package com.attiot.railAnaly.fault.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.entity.TSDepart;
import com.attiot.railAnaly.base.entity.TSRole;
import com.attiot.railAnaly.base.service.TSBaseUserService;
import com.attiot.railAnaly.base.service.TSDepartService;
import com.attiot.railAnaly.base.service.TSRoleService;
import com.attiot.railAnaly.base.service.TSTypeService;
import com.attiot.railAnaly.borrow.service.BorrowListDetailService;
import com.attiot.railAnaly.borrow.service.BorrowListService;
import com.attiot.railAnaly.common.CommonUtils;
import com.attiot.railAnaly.common.Convert;
import com.attiot.railAnaly.common.ImageUtils;
import com.attiot.railAnaly.common.util.StringUtil;
import com.attiot.railAnaly.fault.entity.AFaultInfo;
import com.attiot.railAnaly.fault.entity.AFaultList;
import com.attiot.railAnaly.fault.service.FaultService;
import com.attiot.railAnaly.foundation.ParamVerifyUtil;
import com.attiot.railAnaly.jpush.service.JPushService;
import com.attiot.railAnaly.task.entity.ATaskList;
import com.attiot.railAnaly.workflow.entity.AWorkflowData;
import com.attiot.railAnaly.workflow.service.WorkflowDataService;
import com.google.gson.Gson;
import com.sun.imageio.plugins.common.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;

/***
 * 故障提报
 */
@RestController
@Slf4j
@RequestMapping(value = "fault/faultInfoController")
public class FaultInfoController {
    @Autowired
    private FaultService faultService;
    @Autowired
    private TSDepartService tSDepartService;
    @Autowired
    private WorkflowDataService workflowDataService;
    @Autowired
    private TSRoleService tsRoleService;
    @Autowired
    private TSBaseUserService userService;
    @Autowired
    private TSDepartService departService;
    @Autowired
    private TSTypeService tsTypeService;

    @Autowired
    private JPushService jpushService;
    @Value("${upload.main.dir}")
    private String fileContextPath;//文件上下文路径
    private static final Logger logger = Logger.getLogger(FaultInfoController.class);

    /***
     * 故障提报
     * @param request
     * @param response
     */
    @RequestMapping(value = "/saveFaultInfo", method = RequestMethod.POST)
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.POST}, origins="*")
    public void saveFaultInfo(HttpServletRequest request , HttpServletResponse response){
        AppResult result = new AppResult();
        try{

            String json = request.getParameter("data");
            if(json == null){
                result.setSuccess(false);
                result.setMsg("参数错误");
                result.writer(request,response);
                return;
            }
            ParamVerifyUtil.verifyNotBlank(json);
            Map jsonMap = JSON.parseObject(json,Map.class);
            String userId = Convert.getStringValue(jsonMap.get("accessToken"));

            AFaultInfo aFaultInfo = new AFaultInfo();
            aFaultInfo.setId(Convert.getUUId());
            aFaultInfo.setReporter(userId);
            List<TSDepart> departList = tSDepartService.getDepartListByUserId(userId);
            aFaultInfo.setReporterDeptId(departList!=null && departList.size()>0?departList.get(0).getId():"");
            aFaultInfo.setCreator(userId);
            aFaultInfo.setFaultDepart(Convert.getStringValue(jsonMap.get("faultDepart")));
            aFaultInfo.setFaultTime(Convert.getStringValue(jsonMap.get("faultTime")));
            aFaultInfo.setTrainNo(Convert.getStringValue(jsonMap.get("trainNo")));
            aFaultInfo.setTrainMiles((jsonMap.get("trainMiles")+"").length()==0?0:Convert.getFloatValue(jsonMap.get("trainMiles")));
            aFaultInfo.setTrainUnit(Convert.getStringValue(jsonMap.get("trainUnit")));
            aFaultInfo.setCarriageNo(Convert.getStringValue(jsonMap.get("carriageNo")));
            aFaultInfo.setFaultJob(Convert.getIntegerValue(jsonMap.get("faultJob")));
            aFaultInfo.setFaultJobDetail(Convert.getIntegerValue(jsonMap.get("faultJobDetail")));
            aFaultInfo.setFaultContents(Convert.getStringValue(jsonMap.get("faultContents")));
            String attachContents = Convert.getStringValue(jsonMap.get("attachPath"));
            if(attachContents!=null && attachContents.length()>0) {
                String[] attachArray = attachContents.split(",");
                StringBuilder filePath = new StringBuilder("");
                long filenum = Calendar.getInstance().getTimeInMillis();
                String currentday = Convert.changeDateToString(Calendar.getInstance().getTime(),"yyyyMMdd");
                for(int i=0;i<attachArray.length;i++) {
                    String fileName = (filenum+i)+".jpg";
                    boolean flag = ImageUtils.generateImageFromStr(fileContextPath+ File.separator+currentday+File.separator,fileName,attachArray[i]);//文件传输成功
                    if(flag) {
                        if(filePath.length()>0) {
                            filePath.append(",");
                        }
                        filePath.append(File.separator).append(currentday).append(File.separator).append(fileName);
                    }
                }
                aFaultInfo.setAttachPath(filePath.toString().replace("\\","/"));
            }

            aFaultInfo.setFaultState(1);//提报中
            aFaultInfo.setModifor(userId);
            faultService.saveFaultInfoWithTransaction(aFaultInfo);

        }catch (IllegalArgumentException e){
            log.error("系统内部异常", e);
            result.setSuccess(false);
            result.setMsg("故障提报提交失败");
        }
        result.writer(request,response);
    }

    /**
     * 待审批中查看故障信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "/viewFaultInfo")
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.POST}, origins="*")
    public void viewFaultInfo(HttpServletRequest request , HttpServletResponse response){
        AppResult result = new AppResult();
        try{
            String userId = request.getParameter("accessToken");
            String workflowId = request.getParameter("id");
            List lst = new ArrayList();
            Map jsonMap = new HashMap();
            //根据ID取流程记录
            AWorkflowData aWorkflowData = workflowDataService.getWorkflowDataById(workflowId);            //
            AFaultInfo aFaultInfo = faultService.getAFaultInfoById(aWorkflowData.getSourceId());

            String reporter = aFaultInfo.getReporter();
            TSBaseUser baseUser = userService.getById(reporter);
            aFaultInfo.setReporterName(baseUser.getRealname());
            TSDepart depart = departService.getById(aFaultInfo.getReporterDeptId());
            aFaultInfo.setReporterDeptName(depart.getDepartname());
            aFaultInfo.setFaultDepartName(tsTypeService.getTypeNameByTypeeGroupCodeAndCode("systemType",aFaultInfo.getFaultDepart()));
            aFaultInfo.setFaultJobText(tsTypeService.getTypeNameByTypeeGroupCodeAndCode("faultType",aFaultInfo.getFaultJob()+""));
            aFaultInfo.setFaultJobDetailText(tsTypeService.getTypeNameByTypeeGroupCodeAndCode("faultTimer",aFaultInfo.getFaultJobDetail()+""));
            jsonMap.put("faultInfo",aFaultInfo);

            Map params = new HashMap();
            params.put("wbNo",aWorkflowData.getWbNo());
            params.put("sourceId",aWorkflowData.getSourceId());
            List<AWorkflowData> nodes = workflowDataService.getWorkflowListBySourceIdAndWbNo(params);
            if(null != nodes && nodes.size()>0) {
                for(AWorkflowData record:nodes) {
                    String names = userService.getRealnamesByIds(record.getAuditors());
                    record.setAuditorsName(names);
                    record.setCreatetimeStr(Convert.changeDateToString(record.getCreatetime(),"yyyy-MM-dd HH:mm:ss"));
                    record.setModifytimeStr(Convert.changeDateToString(record.getModifytime(),"yyyy-MM-dd HH:mm:ss"));
                }
            }
            jsonMap.put("nodes",nodes);

            lst.add(jsonMap);
            result.setDataList(lst);
        }catch (IllegalArgumentException e){
            log.error("系统内部异常", e);
            result.setSuccess(false);
            result.setMsg("故障提报提交失败");
        }
        result.writer(request,response);
    }



    //查看所有待派工故障
    @RequestMapping(value = "loadUnfinishFaultInfo")
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.POST}, origins="*")
    public void loadUnfinishFaultInfo(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();

        String userId = request.getParameter("accessToken");
        try {
            List<TSRole> roleList = tsRoleService.getByUserId(userId);
            List lst = new ArrayList();
            boolean flag = false;//是否是工班长
            if(null != roleList && roleList.size()>0) {
                for(TSRole role:roleList) {
                    if("1040".equals(role.getRolecode())) {
                        flag = true;
                        break;
                    }
                }
            }
            if(flag) {
                //取未开始，或部分未开始
                List<AFaultInfo> faultInfoList = faultService.getUnfinishFaultInfoList();
                Map jsonMap = new HashMap();
                jsonMap.put("faults",faultInfoList);
                lst.add(jsonMap);
            }
            result.setDataList(lst);
            result.writer(request, response);
        } catch (NumberFormatException e) {
            result.setSuccess(false);
            result.setMsg("加载数据失败");
            log.error("系统内部异常", e);
        }
    }


    /***
     * 派工
     * @param request
     * @param response
     */
    @RequestMapping(value = "assign")
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.POST}, origins="*")
    public void assign(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        String id = request.getParameter("id");
        Integer factoryJob = Integer.valueOf(request.getParameter("factoryJob"));
        Integer auditType = Integer.valueOf(request.getParameter("auditType"));
        String userId = request.getParameter("accessToken");
        String operators = request.getParameter("operators");

        if(id == null){
            result.setSuccess(false);
            result.setMsg("参数错误");
            result.writer(request,response);
            return;
        }
        //判断进行中工单是否有未开始的
        boolean listStart = faultService.getListStart(id);
        //没有未开始的
        if(listStart){
            result.setSuccess(false);
            result.setMsg("故障已有人员开始作业");
            result.writer(request, response);
            return;
        }

        try {
            faultService.assignFaultInfo(userId,id,factoryJob,auditType,operators);
            result.setSuccess(true);
        } catch (NumberFormatException e) {
            result.setSuccess(false);
            result.setMsg("提交审批失败");
            log.error("系统内部异常", e);
        }
        result.writer(request, response);
    }


}
