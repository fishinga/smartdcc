package com.attiot.railAnaly.workflow.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSUser;
import com.attiot.railAnaly.base.service.TSUserService;
import com.attiot.railAnaly.common.CommonUtils;
import com.attiot.railAnaly.common.util.StringUtil;
import com.attiot.railAnaly.fault.entity.AFaultInfo;
import com.attiot.railAnaly.fault.entity.AFaultList;
import com.attiot.railAnaly.fault.service.FaultService;
import com.attiot.railAnaly.foundation.ParamVerifyUtil;
import com.attiot.railAnaly.point.entity.APointPlease;
import com.attiot.railAnaly.point.entity.APointPleaseForgein;
import com.attiot.railAnaly.point.service.PointPleaseService;
import com.attiot.railAnaly.task.entity.ATaskList;
import com.attiot.railAnaly.task.service.TaskListService;
import com.attiot.railAnaly.workflow.entity.AWorkflowData;
import com.attiot.railAnaly.workflow.service.WorkflowDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "workflow/WorkflowController")
public class WorkflowController {

    @Autowired
    private WorkflowDataService workflowDataService;
    @Autowired
    private TSUserService userService;
    @Autowired
    private PointPleaseService pointService;
    @Autowired
    private TaskListService taskListService;
    @Autowired
    private FaultService faultService;

    private static final Logger logger = Logger.getLogger(WorkflowController.class);
    private static final SimpleDateFormat fat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /***
     * 获取待办信息（流程信息）
     * @param request
     * @param response
     */
    @RequestMapping(value = "getBacklog")
    public void getBacklog(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        try {
            String teamDepartId = request.getParameter("departId");
            String accessToken = request.getParameter("accessToken");
            String auditState = request.getParameter("auditState");
            if (StringUtil.isNotEmpty(accessToken)) {
                Map<String, Object> _map = new HashMap();
                _map.put("userid", accessToken);
                _map.put("auditState", auditState);
//                System.out.println("1--------->查询sql开始"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//                List workFlow = workflowDataService.getBacklog(accessToken);
                List workFlow = workflowDataService.getBacklogNew(accessToken);
//                System.out.println("2--------->查询sql结束"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                List runState_0 = new ArrayList();//发起
                List runState_1 = new ArrayList();//待办
                List runState_3 = new ArrayList();//结束
                for (int k = 0; k < workFlow.size(); k++) {
                    Map _temp = (Map) workFlow.get(k);
                    if ("4011".equals(_temp.get("process_code"))
                            || "4012".equals(_temp.get("process_code"))
                            || "4013".equals(_temp.get("process_code"))
                            || "4014".equals(_temp.get("process_code"))
                            || "4015".equals(_temp.get("process_code"))
                            || "4016".equals(_temp.get("process_code"))
                            || "4017".equals(_temp.get("process_code"))
                            || "4018".equals(_temp.get("process_code"))) {
                        _temp.put("creatorName", _temp.get("creator"));
                        APointPleaseForgein pleaseForgein = pointService.getForgeinById(_temp.get("source_id").toString());
                        if(null ==pleaseForgein.getTrainNo() || pleaseForgein.getTrainNo().length()<1){
                            if(pleaseForgein.getJobContent().lastIndexOf("[")>=0){
                                String trainNo = pleaseForgein.getJobContent().substring(pleaseForgein.getJobContent().lastIndexOf("[")+1,pleaseForgein.getJobContent().lastIndexOf("]"));
                                _temp.put("trainNo", trainNo);
                            }else {
                                _temp.put("trainNo", "");
                            }
                        }else {
                            _temp.put("trainNo", pleaseForgein.getTrainNo());
                        }
                    }
                    if ("1".equals(String.valueOf(_temp.get("run_state"))) && "2".equals(String.valueOf(_temp.get("node_type")))) {//待办
                        runState_1.add(_temp);
                    } else if ("0".equals(String.valueOf(_temp.get("run_state"))) && "1".equals(String.valueOf(_temp.get("node_type")))) {//发起
                        runState_0.add(_temp);
                    }
                    if ("0".equals(String.valueOf(_temp.get("run_state"))) && "3".equals(String.valueOf(_temp.get("node_type")))) {//结束
                        runState_3.add(_temp);
                    }
                }
                for (int k = 0; k < runState_1.size(); k++) {
                    Map _tempDeal = (Map) runState_1.get(k);
                    String wb_no1 = String.valueOf(_tempDeal.get("wb_no"));
                    for (int z = 0; z < runState_0.size(); z++) {
                        Map _tempLaunch = (Map) runState_0.get(z);
                        String wb_no0 = String.valueOf(_tempLaunch.get("wb_no"));
                        if (wb_no1.equals(wb_no0)) {
                            _tempLaunch.put("id", _tempDeal.get("id"));
                            _tempLaunch.put("node_code", _tempDeal.get("node_code"));
                            _tempLaunch.put("node_name", _tempDeal.get("node_name"));
                            _tempLaunch.put("processContent", _tempDeal.get("processContent"));
                            _tempLaunch.put("trainNo", _tempDeal.get("trainNo"));
                        }
                    }
                }
                List newWorkFlow = new ArrayList();
                for (int k = 0; k < runState_1.size(); k++) {
                    Map _tempDeal = (Map) runState_1.get(k);
//                    String source_id = String.valueOf(_tempDeal.get("source_id"));
//                    String sourceIds[] = source_id.split(",");
//                    String process_code = String.valueOf(_tempDeal.get("process_code"));
//                    process_code = process_code.substring(0, 1);

//                    Map<String,String> map = getBacklogDetail(process_code,_tempDeal.get("process_code")+"",sourceIds);
//                    if(null == map.get("trainNo") || "".equals(map.get("trainNo"))){
//                        if(null==_tempDeal.get("trainNo") || "".equals(_tempDeal.get("trainNo"))){
//                            String processContent = String.valueOf(_tempDeal.get("processContent"));
//                            if(processContent.lastIndexOf("[")>=0){
//                                _tempDeal.put("trainNo", processContent.substring(processContent.lastIndexOf("[")+1,processContent.lastIndexOf("]")));
//                            }else {
//                                _tempDeal.put("trainNo","");
//                            }
//                        }else {
//                            _tempDeal.put("trainNo", _tempDeal.get("trainNo"));
//                        }
//                    }else {
//                        _tempDeal.put("trainNo", map.get("trainNo"));
//                    }
//                    if(process_code.equals("5")){
//                        _tempDeal.put("processContent", map.get("processContent"));
//                    }
                    newWorkFlow.add(_tempDeal);
                }
                for (int k = 0; k < runState_0.size(); k++) {
                    Map _tempLaunch = (Map) runState_0.get(k);
                    String wb_no1 = String.valueOf(_tempLaunch.get("wb_no"));
                    for (int z = 0; z < runState_3.size(); z++) {
                        Map _tempEnd = (Map) runState_3.get(z);
                        String wb_no2 = String.valueOf(_tempEnd.get("wb_no"));
                        if (wb_no1.equals(wb_no2)) {
                            _tempLaunch.put("node_code", _tempEnd.get("node_code"));
                            _tempLaunch.put("node_name", _tempEnd.get("node_name"));
                        }
//                        String source_id = String.valueOf(_tempLaunch.get("source_id"));
//                        String sourceIds[] = source_id.split(",");
//                        String process_code = String.valueOf(_tempLaunch.get("process_code"));
//                        process_code = process_code.substring(0, 1);
//                        Map<String,String> map = getBacklogDetail(process_code,_tempLaunch.get("process_code")+"",sourceIds);
//                        if(null == map.get("trainNo") || "".equals(map.get("trainNo"))){
//                            if(null==_tempLaunch.get("trainNo") || "".equals(_tempLaunch.get("trainNo"))){
//                                String processContent = String.valueOf(_tempLaunch.get("processContent"));
//                                if(processContent.lastIndexOf("[")>0){
//                                    _tempLaunch.put("trainNo", processContent.substring(processContent.lastIndexOf("[")+1,processContent.lastIndexOf("]")));
//                                }else {
//                                    _tempLaunch.put("trainNo","");
//                                }
//                            }else {
//                                _tempLaunch.put("trainNo", _tempLaunch.get("trainNo"));
//                            }
//                        }else {
//                            _tempLaunch.put("trainNo", map.get("trainNo"));
//                        }
//                        if(process_code.equals("5")){
//                            _tempLaunch.put("processContent", map.get("processContent"));
//                        }
                    }

                    newWorkFlow.add(_tempLaunch);
                }
                result.setDataList(newWorkFlow);
            } else {
                result.setSuccess(false);
                result.setMsg("参数有错！");
            }
            result.writer(request, response);
        } catch (IllegalArgumentException e) {
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            log.error("系统内部异常", e);
        }
    }

    /**
     *
     * @param process_code
     * @param processCodes
     * @param sourceIds
     * @return
     */
    public Map<String,String> getBacklogDetail(String process_code,String processCodes,String sourceIds[]){
        Map<String,String> map = new HashMap<String,String>();
        StringBuilder trainNo = new StringBuilder("");
        StringBuilder processContent = new StringBuilder("");
        if ("3".equals(process_code)) {
            for (int i = 0; i < sourceIds.length; i++) {
                ATaskList taskList = taskListService.getById(sourceIds[i]);
                if (taskList != null) {
                    trainNo.append(taskList.getTrainNo()).append(",");
                }
            }
            if (trainNo.length() > 0) {
                trainNo = new StringBuilder(trainNo.substring(0, trainNo.length() - 1));
            }
        }

        if("5".equals(process_code)){//故障工单
            for (int i = 0; i < sourceIds.length; i++) {
                if("5010".equals(processCodes)){//故障提报
                    AFaultInfo faultInfo=  faultService.getInfoById(sourceIds[i]);
                    if(faultInfo!=null){
                        trainNo.append(faultInfo.getTrainNo()).append(",");
                        processContent.append(faultInfo.getFaultContents()).append(",");
                    }
                    if (trainNo.length() > 0) {
                        trainNo = new StringBuilder(trainNo.substring(0, trainNo.length() - 1));
                        processContent = new StringBuilder(processContent.substring(0, processContent.length() - 1));
                    }
                }else{
                    AFaultList faultList = faultService.getById(sourceIds[i]);
                    if(faultList!=null){
                        trainNo.append(faultList.getTrainNo()).append(",");
                        processContent.append(faultList.getTaskName()).append(",");
                    }
                    if (trainNo.length() > 0) {
                        trainNo = new StringBuilder(trainNo.substring(0, trainNo.length() - 1));
                        processContent = new StringBuilder(processContent.substring(0, processContent.length() - 1));
                    }
                }

            }
        }
        map.put("trainNo",trainNo.toString());
        map.put("processContent",processContent.toString());
        return map;
    }


    /***
     * 根据ID 查询
     * @param request
     * @param response
     */
    @RequestMapping(value = "getBacklogById")
    public void getBacklogById(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        try {
            String accessToken = request.getParameter("accessToken");
            String id = request.getParameter("id");
            if (StringUtil.isNotEmpty(accessToken)) {
                //查询流程信息
                AWorkflowData workFlow = workflowDataService.getById(id);
                int nodeCode = 0;
                if (null != workFlow) {
                    nodeCode = Integer.valueOf(workFlow.getNodeCode());
                }
                List nodeLst = null;
                String dataVal = "";
                Integer runState = 0;
                Integer auditState = 0;
                String realname = "";
                String audit_results = "";
                boolean auditFlag = false;
                if (StringUtil.isNotEmpty(workFlow.getSourceId())) {
                    //查询审批流程记录
                    nodeLst = workflowDataService.getBlockLogNode(workFlow.getSourceId(), workFlow.getWbNo());
                    dataVal = workFlow.getDataValue();
                    runState = workFlow.getRunState();
                    audit_results = workFlow.getAuditResults();
                    TSUser user = userService.getById(workFlow.getAuditors());
                    if (null != user) {
                        realname = user.getRealname();
                    }
                    if (0 == workFlow.getRunState()) {//已运行的，查最新记录
                        int countAudit = 0;
                        for (int k = 0; k < nodeLst.size(); k++) {
                            Map _tempNode = (Map) nodeLst.get(k);
                            String _node_code = String.valueOf(_tempNode.get("node_code"));
                            String process_code = String.valueOf(_tempNode.get("process_code"));
                            if (nodeCode < Integer.valueOf(_node_code) && process_code.equals(workFlow.getProcessCode())) {
                                nodeCode = Integer.valueOf(_node_code);
                                dataVal = String.valueOf(_tempNode.get("data_value"));
                                //runState = (Integer) _tempNode.get("run_state");
                                realname = String.valueOf(_tempNode.get("realname"));
                                audit_results = String.valueOf(_tempNode.get("audit_results"));
                                auditState = (Integer) _tempNode.get("audit_state");
                            }
                            String run_state = String.valueOf(_tempNode.get("run_state"));
                            if ("1".equals(run_state)) {
                                String auditors = String.valueOf(_tempNode.get("auditors"));
                                if (auditors.contains(accessToken)) {
                                    countAudit++;
                                }
                            }
                        }
                        if (countAudit > 0) {
                            auditFlag = true;
                        }
                    } else if (1 == workFlow.getRunState()) {//当前可运行的
                        if (StringUtils.isNotBlank(workFlow.getAuditors()) && StringUtils.isNotEmpty(workFlow.getAuditors())) {
                            if (workFlow.getAuditors().contains(accessToken)) {//是否可以审批
                                auditFlag = true;
                            }
                        }
                    }
                }
                Map jsonMap = JSON.parseObject(dataVal, Map.class);
                DateFormat formatFrom = new SimpleDateFormat("MMM dd,yyyy KK:mm:ss aa", Locale.ENGLISH);
                DateFormat formatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if ("2010".equals(workFlow.getProcessCode())) {//借用审批
                    JSONObject _temp = (JSONObject) jsonMap.get("borrow");
                    String borrowTime = String.valueOf(_temp.get("borrowTime"));
                    if (StringUtils.isNotEmpty(borrowTime) && StringUtils.isNotBlank(borrowTime)) {
                        Date date = formatFrom.parse(borrowTime);
                        _temp.put("borrowTime", formatTo.format(date));
                    }
                    APointPlease pointPlease = pointService.getById(String.valueOf(_temp.get("ppointId")));
                    if (null != pointPlease) {
                        _temp.put("pointType", pointPlease.getPointType());
                    }
                }
                //接触网断送电
                if ("1040".equals(workFlow.getProcessCode()) || "1041".equals(workFlow.getProcessCode())) {
                    JSONObject aPointPleasePageTemp = (JSONObject) jsonMap.get("aPointPleasePage");
                    //申请时间格式化
                    String workingTime = String.valueOf(aPointPleasePageTemp.get("workingTime"));
                    if (StringUtils.isNotEmpty(workingTime) && StringUtils.isNotBlank(workingTime)) {
                        Date date = formatFrom.parse(workingTime);
                        aPointPleasePageTemp.put("workingTime", formatTo.format(date));
                    }
                    JSONObject powerTemp = (JSONObject) jsonMap.get("power");
                    //创建人
                    String creator = String.valueOf(powerTemp.get("creator"));
                    aPointPleasePageTemp.put("createName", creator);
                }
                JSONObject _tempStinger = (JSONObject) jsonMap.get("stinger");
                if (_tempStinger != null) {
                    String sendKeyTime = null != _tempStinger.get("sendKeyTime") ? String.valueOf(_tempStinger.get("sendKeyTime")) : null;
                    String givePowerTime = null != _tempStinger.get("givePowerTime") ? String.valueOf(_tempStinger.get("givePowerTime")) : null;
                    if (sendKeyTime != null && StringUtils.isNotEmpty(sendKeyTime) && StringUtils.isNotBlank(sendKeyTime)) {
                        Date date = formatFrom.parse(sendKeyTime);
                        _tempStinger.put("sendKeyTime", formatTo.format(date));
                    }
                    if (givePowerTime!=null && StringUtils.isNotEmpty(givePowerTime) && StringUtils.isNotBlank(givePowerTime)) {
                        Date date = formatFrom.parse(givePowerTime);
                        _tempStinger.put("givePowerTime", formatTo.format(date));
                    }
                }

                jsonMap.put("node", nodeLst);
                jsonMap.put("nodeCode", nodeCode);
                jsonMap.put("runState", runState);
                jsonMap.put("auditFlag", auditFlag);
                jsonMap.put("auditors", realname);
                jsonMap.put("auditState", auditState);
                jsonMap.put("auditResults", audit_results.toString().length()>0 ? audit_results : ' ');
                List lst = new ArrayList();
                lst.add(jsonMap);
                result.setDataList(lst);
            } else {
                result.setSuccess(false);
                result.setMsg("参数有错！");
            }
            result.writer(request, response);
        } catch (ParseException e) {
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            log.error("系统内部异常", e);
        }
    }

    /***
     * 保存流程信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "saveBlock")
    public void saveBlock(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        String refnum = "";
        try {
            String json = request.getParameter("data");
            if(json == null){
                result.setSuccess(false);
                result.setMsg("参数错误");
                result.writer(request,response);
                return;
            }
            ParamVerifyUtil.verifyNotBlank(json);
            Map jsonMap = JSON.parseObject(json, Map.class);
            if (!jsonMap.isEmpty()) {

                //--控制访问开始
                String accessToken = String.valueOf(jsonMap.get("accessToken"));
                boolean isdanger = CommonUtils.isDanger(accessToken + "_WorkflowController_saveBlock");
                if (isdanger) {
                    result.setSuccess(false);
                    result.setMsg("操作太频繁,请稍等...");
                    result.writer(request, response);
                    return;
                }
                refnum = String.valueOf(jsonMap.get("id"));
                logger.info("saveBlock(refnum):" + refnum);
                //判断当前refnum的状态值:
                Integer refstate = CommonUtils.getCurrentRefnumState(refnum);
                if (1 == refstate) {//提交中,请勿重新提交
                    result.setSuccess(false);
                    result.setMsg("提交中,请勿重新提交...");
                    result.writer(request, response);
                    return;
                } else if (2 == refstate) {
                    result.setSuccess(false);
                    result.setMsg("已提交,请勿重新提交...");
                    result.writer(request, response);
                    return;
                }
                //--控制访问结束

                CommonUtils.setRefnum(refnum, 1);//提交中
                workflowDataService.saveBlock(jsonMap);
                CommonUtils.setRefnum(refnum, 2);//提交成功
                result.setSuccess(true);
                result.setMsg("流程提交成功！");
            } else {
                result.setSuccess(false);
                result.setMsg("参数不能为空！");
            }
        } catch (IllegalArgumentException e) {
            CommonUtils.setRefnum(refnum, 0);//提交失败
            result.setMsg("审批提交失败,请联系管理员");
            logger.error("系统内部异常");
            result.setSuccess(false);
        }
        result.writer(request, response);
    }

    @RequestMapping(value = "submitWorkflow")
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.POST}, origins="*")
    public void submitWorkflow(
            HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        String refnum = "";
        try {
//            TSUser user = ResourceUtil.getSessionUserName();
            String workflowId = request.getParameter("workflowId");//工作流id
            Integer auditState = Integer.valueOf(request.getParameter("auditState"));//审批通过与否
            String auditResults = request.getParameter("auditResults");//审批意见
            String data = request.getParameter("data");//json数据
            String accessToken = request.getParameter("accessToken");
            refnum = workflowId;

            if(request.getParameter("auditState")==null || request.getParameter("auditState").length()<1){
                result.setSuccess(false);
                result.setMsg("提交参数无效,请选择审批意见.");
                result.writer(request, response);
                return;
            }
            //--控制访问开始
            boolean isdanger = CommonUtils.isDanger(accessToken + "_WorkflowController_submitWorkflow");
            if (isdanger) {
                result.setSuccess(false);
                result.setMsg("操作太频繁,请稍等...");
                result.writer(request, response);
                return;
            }
//            logger.info("submitWorkflow(refnum):" + refnum);
            if (null == refnum || "null".equals(refnum) || refnum.length()<1) {
                result.setSuccess(false);
                result.setMsg("提交参数无效,请退出重新登陆.");
                result.writer(request, response);
                return;
            }
            //判断当前refnum的状态值:
            Integer refstate = CommonUtils.getCurrentRefnumState(refnum);
            if (1 == refstate) {//提交中,请勿重新提交
                result.setSuccess(false);
                result.setMsg("提交中,请勿重新提交...");
                result.writer(request, response);
                return;
            } else if (2 == refstate) {
                result.setSuccess(false);
                result.setMsg("已提交,请勿重新提交...");
                result.writer(request, response);
                return;
            }
            //--控制访问结束

            CommonUtils.setRefnum(refnum, 1);//提交中
            workflowDataService.submitWorkflowWorkflow(workflowId, auditState, auditResults, data, accessToken);
            CommonUtils.setRefnum(refnum, 2);//提交成功
            result.setSuccess(true);
        } catch (IllegalArgumentException e) {
            result.setMsg("审批提交失败,请联系管理员");
            logger.error("系统内部异常");
            CommonUtils.setRefnum(refnum, 0);//提交失败
            result.setSuccess(false);
        }
        result.writer(request, response);
    }

}
