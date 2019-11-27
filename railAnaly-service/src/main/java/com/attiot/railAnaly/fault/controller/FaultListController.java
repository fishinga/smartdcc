package com.attiot.railAnaly.fault.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.entity.TSDepart;
import com.attiot.railAnaly.base.entity.TSRole;
import com.attiot.railAnaly.base.entity.TSType;
import com.attiot.railAnaly.base.service.TSBaseUserService;
import com.attiot.railAnaly.base.service.TSDepartService;
import com.attiot.railAnaly.base.service.TSRoleService;
import com.attiot.railAnaly.base.service.TSTypeService;
import com.attiot.railAnaly.common.Convert;
import com.attiot.railAnaly.fault.entity.AFaultInfo;
import com.attiot.railAnaly.fault.entity.AFaultList;
import com.attiot.railAnaly.fault.service.FaultService;
import com.attiot.railAnaly.foundation.ParamVerifyUtil;
import com.attiot.railAnaly.jpush.service.JPushService;
import com.attiot.railAnaly.workflow.entity.AWorkflowData;
import com.attiot.railAnaly.workflow.service.WorkflowDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 故障工单处理
 */
@RestController
@Slf4j
@RequestMapping(value = "fault/faultListController")
public class FaultListController {
    @Autowired
    private FaultService faultService;
    @Autowired
    private TSBaseUserService tSBaseUserService;
    @Autowired
    private TSTypeService tsTypeService;
    @Autowired
    private TSDepartService departService;
    @Autowired
    private JPushService jpushService;
    @Autowired
    private TSRoleService tsRoleService;
    @Autowired
    private WorkflowDataService workflowDataService;
    @Autowired
    private TSBaseUserService userService;
    private static final Logger logger = Logger.getLogger(FaultListController.class);

    /***
     * 加载故障工单
     * @param request
     * @param response
     */
    @RequestMapping(value = "/loadFaultList")
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.POST}, origins="*")
    public void loadFaultList(HttpServletRequest request , HttpServletResponse response){
        AppResult result = new AppResult();
        List outList = new ArrayList();
        Map outMap = new HashMap();
        try{
            String userId = request.getParameter("accessToken");
            //1.判断是否工班长，如果是工班长，取所有待派工故障工单
            List<TSRole> roleList = tsRoleService.getByUserId(userId);

            boolean ismanager = false;//是否是工班长
            if(null != roleList && roleList.size()>0) {
                for(TSRole role:roleList) {
                    if("1040".equals(role.getRolecode())) {
                        ismanager = true;
                        break;
                    }
                }
            }
            outMap.put("flag",ismanager?1:0);//工班长为1，非工班长为0
            List<JSONObject> unassignrows = new ArrayList();
//            if(ismanager) {
                //取未开始，或部分未开始
                List<AFaultInfo> faultInfoList = faultService.getUnfinishFaultInfoList();
                if(null != faultInfoList && faultInfoList.size()>0) {
                    StringBuilder userIds = new StringBuilder("");

                    List<TSType> typeList=tsTypeService.getByTypeeGroupCode("systemType");
                    Map<String,String> typeMap=new HashMap();
                    if(null != typeList && typeList.size()>0) {
                        for(TSType record:typeList) {
                            typeMap.put(record.getTypecode(),record.getTypename());
                        }
                    }
                    for(AFaultInfo record:faultInfoList) {
                        JSONObject obj = new JSONObject();
                        obj.put("id",record.getId());
                        obj.put("reporter",record.getReporter());
                        userIds.append(",").append(record.getReporter());
                        obj.put("trainNo",record.getTrainNo());
                        obj.put("faultTime",record.getFaultTime());//故障 日期
                        obj.put("faultDepart",typeMap.get(record.getFaultDepart())!=null ?typeMap.get(record.getFaultDepart()):record.getFaultDepart());//故障部件
                        obj.put("faultState",record.getFaultState());//故障状态
                        obj.put("faultContents",record.getFaultContents());//故障描述
                        obj.put("faultLevel",record.getFaultLevel());//故障等级
                        unassignrows.add(obj);
                    }
                    List<TSBaseUser> userList = tSBaseUserService.getByIds(userIds.toString());
                    Map<String,String> userMap = new HashMap();
                    if(null != userList&& userList.size()>0) {
                        for(TSBaseUser baseUser:userList) {
                            userMap.put(baseUser.getId(),baseUser.getRealname());
                        }
                    }
                    for(JSONObject obj:unassignrows) {
                        String reporter = obj.getString("reporter");
                        obj.put("reporterName",userMap.get(reporter)!=null?userMap.get(reporter):reporter);
                    }

                }
//            }
            outMap.put("unAssignFaults",unassignrows);//未派工工单


            List<AFaultList> list = faultService.getMyFaultList(userId);
            List myfaultrows = new ArrayList();
            if(null != list && list.size()>0) {
                StringBuilder jobIds = new StringBuilder("");

                List<TSType> typeList = tsTypeService.getByTypeeGroupCode("systemType");
                Map<String,String> typeMap = new HashMap();
                for(TSType type:typeList) {
                    typeMap.put(type.getTypecode(),type.getTypename());
                }
                for(AFaultList record:list) {
                    JSONObject obj = new JSONObject();
                    obj.put("id",record.getId());//id
                    obj.put("auditType",record.getAuditType());//三检
                    obj.put("faultDepart",typeMap.get(record.getFaultDepart())!=null?typeMap.get(record.getFaultDepart()):record.getFaultDepart());//故障部位
                    obj.put("faultLevel",record.getFaultLevel());//故障等级
                    obj.put("jobId",record.getJobId());//故障
                    obj.put("faultContents",record.getTaskName());//故障描述
                    obj.put("auditState",record.getWorkState());//审批状态
                    obj.put("trainNo",record.getTrainNo());//故障列车
                    obj.put("factoryJob",record.getFactoryJob());//是否厂家作业1员工2-厂家
                    obj.put("faultTime",record.getAssignTimeStr());//故障时间
                    obj.put("reporter","");//提报人
                    if(jobIds.length()>0) {
                        jobIds.append(",");
                    }
                    jobIds.append(record.getJobId());
                    myfaultrows.add(obj);
                }

                List<AFaultInfo> infoList = faultService.getByIds(jobIds.toString());

                Map<String,AFaultInfo> reporterMap = new HashMap();//jobId:reporter
                StringBuilder reporters = new StringBuilder("");
                for(AFaultInfo info:infoList) {
                    if(reporters.length()>0) {
                        reporters.append(",");
                    }
                    reporters.append(info.getReporter());
                    reporterMap.put(info.getId(),info);
                }
                List<TSBaseUser> userList = tSBaseUserService.getByIds(reporters.toString());
                Map<String,TSBaseUser> userMap = new HashMap();
                for(TSBaseUser user:userList) {
                    userMap.put(user.getId(),user);
                }
                for(int i=0;i<myfaultrows.size();i++) {
                    JSONObject json = (JSONObject)myfaultrows.get(i);
                    AFaultInfo aFaultInfo = reporterMap.get(json.getString("jobId"));
                    String reporter = null != aFaultInfo?aFaultInfo.getReporter():null;
                    String reporterName = null != reporter && null != userMap.get(reporter)?userMap.get(reporter).getRealname():reporter;
                    json.put("reporterName",reporterName);
//                    json.put("faultTime",null != aFaultInfo?aFaultInfo.getFaultTime():"");
                }
            }

            outMap.put("myFaults",myfaultrows);//分配给我的故障工单
            outList.add(outMap);
            result.setDataList(outList);
        }catch (IllegalArgumentException e){
            log.error("系统内部异常", e);
            result.setSuccess(false);
            result.setMsg("故障提报提交失败");
        }
        result.writer(request,response);
    }

    /***
     * 查看工单详情
     * @param request
     * @param response
     */
    @RequestMapping(value = "/viewFaultList")
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.POST}, origins="*")
    public void viewFaultList(HttpServletRequest request , HttpServletResponse response){
        AppResult result = new AppResult();
        try{
            String userId = request.getParameter("accessToken");
            String jobId = request.getParameter("jobId");//afaultinfo主键
            String id = request.getParameter("id");//afaultlist主键
            AFaultList aFaultList = null != id && id.length()>0?faultService.getAFaultListById(id):null;
            AFaultInfo aFaultInfo = faultService.getAFaultInfoById(jobId);
            List lst = new ArrayList();
            Map map = new HashMap();
            String reporter = aFaultInfo.getReporter();
            TSBaseUser user = tSBaseUserService.getById(reporter);
            aFaultInfo.setReporterName(user.getRealname());
            List<TSType> typeList = tsTypeService.getByTypeeGroupCode("systemType");
            Map<String,String> typeMap = new HashMap();
            for(TSType type:typeList) {
                typeMap.put(type.getTypecode(),type.getTypename());
            }
            TSDepart depart = departService.getById(aFaultInfo.getReporterDeptId());
            aFaultInfo.setReporterDeptName(null != depart?depart.getDepartname():aFaultInfo.getReporterDeptId());
//            String faultDepartName = typeMap.get(aFaultInfo.getFaultDepart());
//            aFaultInfo.setFaultDepartName(faultDepartName);
            aFaultInfo.setFaultDepartName(tsTypeService.getTypeNameByTypeeGroupCodeAndCode("systemType",aFaultInfo.getFaultDepart()));
            aFaultInfo.setFaultJobText(null !=aFaultInfo.getFaultJob()?tsTypeService.getTypeNameByTypeeGroupCodeAndCode("faultType",aFaultInfo.getFaultJob()+""):"");
            aFaultInfo.setFaultJobDetailText(null != aFaultInfo.getFaultJobDetail()?tsTypeService.getTypeNameByTypeeGroupCodeAndCode("faultTimer",aFaultInfo.getFaultJobDetail()+""):"");
            map.put("faultInfo",aFaultInfo);
            if(null != aFaultList) {
                aFaultList.setScheOperatorsName(tSBaseUserService.getRealnamesByIds(aFaultList.getScheOperators()));
                map.put("faultList",aFaultList);
            }else {
                map.put("faultList",new AFaultList());
            }

            lst.add(map);
            result.setDataList(lst);

        }catch (IllegalArgumentException e){
            log.error("系统内部异常", e);
            result.setSuccess(false);
            result.setMsg("数据加载失败");
        }
        result.writer(request,response);
    }

    /**
     * 待审批中查看故障工单处理
     * @param request
     * @param response
     */
    @RequestMapping(value = "/viewAuditFaultList")
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.POST}, origins="*")
    public void viewAuditFaultList(HttpServletRequest request , HttpServletResponse response){
        AppResult result = new AppResult();
        try{
            String userId = request.getParameter("accessToken");
            String workflowId = request.getParameter("id");
            String typeGroupCode = request.getParameter("typeGroupCode");
            List lst = new ArrayList();
            Map jsonMap = new HashMap();
            //根据ID取流程记录
            AWorkflowData aWorkflowData = workflowDataService.getWorkflowDataById(workflowId);            //
            AFaultList aFaultList = faultService.getAFaultListById(aWorkflowData.getSourceId());
            AFaultInfo aFaultInfo = faultService.getAFaultInfoById(aFaultList.getJobId());
            String reporter = aFaultInfo.getReporter();
            TSBaseUser baseUser = userService.getById(reporter);
            aFaultInfo.setReporterName(baseUser.getRealname());
            TSDepart depart = departService.getById(aFaultInfo.getReporterDeptId());
            aFaultInfo.setReporterDeptName(depart.getDepartname());
            aFaultInfo.setFaultDepartName(tsTypeService.getTypeNameByTypeeGroupCodeAndCode("systemType",aFaultInfo.getFaultDepart()));
            aFaultInfo.setFaultJobText(tsTypeService.getTypeNameByTypeeGroupCodeAndCode("faultType",aFaultInfo.getFaultJob()+""));
            aFaultInfo.setFaultJobDetailText(tsTypeService.getTypeNameByTypeeGroupCodeAndCode("faultTimer",aFaultInfo.getFaultJobDetail()+""));
            if(aFaultInfo.getCoeDiff() == null || aFaultInfo.getCoeDiff().toString().length()<1){
                aFaultInfo.setCoeDiff(aFaultList.getCoeDiff());
            }
            if(null != typeGroupCode && typeGroupCode.length()>0){
                String[] groupCodeArray = typeGroupCode.split(",");
                List dataList = new ArrayList();
                for(int i=0;i<groupCodeArray.length;i++) {
                    List<TSType> typeList = tsTypeService.getByTypeeGroupCode(groupCodeArray[i]);
                    List  tlst= new ArrayList();
                    if (null != typeList && typeList.size() > 0) {
                        for (TSType record : typeList) {
                            Map map = new HashMap();
                            map.put("text", record.getTypename());
                            map.put("value", record.getTypecode());
                            tlst.add(map);
                        }
                    }
                    Map groupMap = new HashMap();
                    groupMap.put(groupCodeArray[i],tlst);
                    dataList.add(groupMap);
                }
                jsonMap.put("coeDiffList",dataList);
            }
            jsonMap.put("faultInfo",aFaultInfo);
            jsonMap.put("faultList",aFaultList);
            Map params = new HashMap();
            params.put("wbNo",aWorkflowData.getWbNo());
            params.put("sourceId",aWorkflowData.getSourceId());
            List<AWorkflowData> nodes = workflowDataService.getWorkflowListBySourceIdAndWbNo(params);
            if(null != nodes && nodes.size()>0) {
                for(AWorkflowData record:nodes) {
                    String names = null != record.getAuditors()?userService.getRealnamesByIds(record.getAuditors()):"";
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
            result.setMsg("查看故障工单详情失败");
        }
        result.writer(request,response);
    }

    /***
     * 提交审批
     * @param request
     * @param response
     */
    @RequestMapping(value = "/submitToWorkflow")
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.POST}, origins="*")
    public void submitToWorkflow(HttpServletRequest request , HttpServletResponse response){
        AppResult result = new AppResult();
        try{
//            String userId = request.getParameter("accessToken");
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
            String id = Convert.getStringValue(jsonMap.get("id"));
            AFaultList aFaultList = faultService.getAFaultListById(id);
            String processCode = "";
            if(aFaultList.getAuditType()==1) {
                processCode = "5011";
            }else if(aFaultList.getAuditType()==2) {
                processCode = "5012";
            }else if(aFaultList.getAuditType()==3) {
                processCode = "5013";
            }
            Float surplusValue = Convert.getFloatValue(jsonMap.get("surplusValue"));//剩余进度
            String remarks = Convert.getStringValue(jsonMap.get("remarks"));//完成情况
            String doFaultRemarks = Convert.getStringValue(jsonMap.get("doFaultRemarks"));//故障处理情况
            String mutualOperations = Convert.getStringValue(jsonMap.get("mutualOperations"));//互检人
            String speOperations = Convert.getStringValue(jsonMap.get("speOperations"));//他检人
            String data = "assignIds="+mutualOperations;
            faultService.submitFaultListToWorkflow(id,surplusValue,remarks, doFaultRemarks,mutualOperations,speOperations,userId);

        }catch (IllegalArgumentException e){
            log.error("系统内部异常", e);
            result.setSuccess(false);
            result.setMsg("故障提报提交失败");
        }
        result.writer(request,response);
    }



}
