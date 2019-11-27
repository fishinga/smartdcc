package com.attiot.railAnaly.task.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.*;
import com.attiot.railAnaly.base.param.TSBaseUserQueryParam;
import com.attiot.railAnaly.base.service.*;
import com.attiot.railAnaly.common.CommonUtils;
import com.attiot.railAnaly.common.ConstantValue;
import com.attiot.railAnaly.common.HttpClientUtilsToServer;
import com.attiot.railAnaly.common.util.DateUtils;
import com.attiot.railAnaly.common.util.StringUtil;
import com.attiot.railAnaly.fault.entity.AFaultList;
import com.attiot.railAnaly.fault.service.FaultService;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.goods.entity.PointList;
import com.attiot.railAnaly.goods.entity.PointListGoods;
import com.attiot.railAnaly.goods.service.PointListService;
import com.attiot.railAnaly.jpush.service.JPushService;
import com.attiot.railAnaly.point.service.ForgeinPointPleaseService;
import com.attiot.railAnaly.task.entity.*;
import com.attiot.railAnaly.task.param.ATaskListQueryParam;
import com.attiot.railAnaly.task.service.ATaskListHisService;
import com.attiot.railAnaly.task.service.TaskListService;
import com.attiot.railAnaly.task.service.TaskReformDataService;
import com.attiot.railAnaly.workflow.entity.AWorkflowData;
import com.attiot.railAnaly.workflow.service.WorkflowDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Slf4j
@RequestMapping(value = "/task/TaskController")
public class TaskController {

    @Autowired
    private TaskListService taskListService;
    @Autowired
    private TSDepartService departService;
    @Autowired
    private TSRoleService roleService;
    @Autowired
    private WorkflowDataService workflowDataService;
    @Autowired
    private TSUserService userService;
    @Autowired
    private TaskReformDataService taskReformDataService;
    @Autowired
    private PointListService pointListService;
    @Autowired
    private ForgeinPointPleaseService forgeinPointPleaseService;
    @Autowired
    private TSBaseUserService baseUserService;
    @Autowired
    private JPushService jpushService;
    @Value("${server_path}")
    private String serverPath;
    @Autowired
    private TSTypeService tsTypeService;
    @Autowired
    private FaultService faultService;
    @Autowired
    private ATaskListHisService taskListHisService;

    /***
     * 获取需要完成的任务
     * @param request
     * @param response
     */
    @RequestMapping(value = "getNeedTask")
    public void getNeedTask(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        try {
            String departId = request.getParameter("departId");
            String userId = request.getParameter("accessToken");
            String pointType = request.getParameter("pointType");
            if (StringUtil.isNotEmpty(departId)) {
//                List lst = taskListService.getNeedTask(departId,userId);
                Calendar current = Calendar.getInstance();
                SimpleDateFormat mmformat = new SimpleDateFormat("MM");
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                Integer workmonth = Integer.valueOf(mmformat.format(current.getTime()));
                List<ATaskList> systemList = taskListService.getATaskListByWorkDateAndTaskSource(dateformat.format(current.getTime()), 1, "1,2");
                if (null == systemList || systemList.size() <= 0) systemList = new ArrayList();
                List<ATaskList> taskLists = taskListService.getMyUnfinishTaskList(userId, "2,3,4,5,6,7,8,9", "1,2");
                if (null == taskLists || taskLists.size() <= 0) taskLists = new ArrayList();
                List<Map> lst = new ArrayList();
                List taskIds = new ArrayList();
                for (ATaskList record : systemList) {
                    Map map = new HashMap();
                    map.put("id", record.getId());
                    map.put("task_name", record.getTaskName());
                    map.put("train_no", record.getTrainNo());
                    map.put("work_state", record.getWorkState());
                    map.put("work_date", record.getWorkDate());
                    map.put("task_source", record.getTaskSource());
                    map.put("need_risk_table", 0);
                    if ("2".equals(record.getTaskSource()) || (null != record.getPreTransferSource() && record.getPreTransferSource().toString().length()>0 && "2".equals(record.getPreTransferSource()))) {
                        map.put("need_risk_table", 1);
                    }
                    map.put("audit_type", record.getAuditType());
                    map.put("job_id", record.getJobId());
                    map.put("taskLabel", record.getJobId());
                    lst.add(map);
                    taskIds.add(record.getId());
                }
                Map<String, TaskTemp> tempMap = new HashMap();
                for (ATaskList record : taskLists) {
                    Map map = new HashMap();
                    map.put("id", record.getId());
                    taskIds.add(record.getId());
                    map.put("task_name", record.getTaskName());
                    map.put("train_no", record.getTrainNo());
                    map.put("work_state", record.getWorkState());
                    map.put("work_date", record.getWorkDate());
                    map.put("task_source", record.getTaskSource());
                    map.put("audit_type", record.getAuditType());
                    map.put("job_id", record.getJobId());
                    map.put("taskLabel", record.getJobId());
                    if (record.getTaskSource() == 5) {//日检，四日检，四日检巡查，设置标识为5
                        map.put("taskLabel", 5);
                    } else if (record.getTaskSource() == 4) {//临时
                        TaskTemp temp = tempMap.get(record.getJobId());
                        if (null == temp) {
                            temp = taskListService.getTaskTempById(record.getJobId());
                            if (null != temp) {
                                if (null != temp.getTempSource() && temp.getTempSource() == 5) {
                                    map.put("taskLabel", 5);
                                }
                                tempMap.put(record.getJobId(), temp);
                            }
                        } else {
                            if (null != temp.getTempSource() && temp.getTempSource() == 5) {
                                map.put("taskLabel", 5);
                            }
                        }
                    } else if (record.getTaskSource() == 6) {//任务交接
                        if (record.getPreTransferSource() != null) {
                            if (record.getPreTransferSource() == 5) {//交接前是日检
                                map.put("taskLabel", 5);
                            } else if (record.getPreTransferSource() == 4) {//交接前是临时
                                TaskTemp temp = tempMap.get(record.getJobId());
                                if (null == temp) {
                                    temp = taskListService.getTaskTempById(record.getJobId());
                                    if (null != temp) {
                                        if (null != temp.getTempSource() && temp.getTempSource() == 5) {
                                            map.put("taskLabel", 5);
                                        }
                                        tempMap.put(record.getJobId(), temp);
                                    }
                                }
                            } else if (record.getPreTransferSource() == 7) {
                                map.put("taskLabel", 7);
                            }
                        }
                    } else if (record.getTaskSource() == 7) {
                        map.put("taskLabel", 7);
                    }

                    map.put("need_risk_table", 0);//是否需要填风险预控表,0不需要，1需要
                    if ("2".equals(record.getTaskSource() + "") || (null != record.getPreTransferSource() && "2".equals(record.getPreTransferSource() + ""))) {
                        map.put("need_risk_table", 1);
                    }
                    lst.add(map);
                }

                //3故障提报
                List<AFaultList> faultList = faultService.getMyUnfinishFaultList(userId,"1,2");
                for(AFaultList fList:faultList){
                    if(fList.getPpointId() == null || fList.getPpointId().length()<1){
                        Map map = new HashMap();
                        map.put("id", fList.getId());
                        taskIds.add(fList.getId());
                        map.put("task_name", fList.getTaskName());
                        map.put("train_no", fList.getTrainNo());
                        map.put("work_state", fList.getWorkState());
                        map.put("work_date", fList.getWorkDate());
                        map.put("task_source", 3);
                        map.put("audit_type", fList.getAuditType());
                        map.put("job_id", fList.getJobId());
                        map.put("taskLabel", fList.getJobId());
                        lst.add(map);
                    }else {
                        List<AFaultList> af = faultService.checkTaskRequested(fList.getId(),pointType);
                        if(af.size()==0){
                            Map map = new HashMap();
                            map.put("id", fList.getId());
                            taskIds.add(fList.getId());
                            map.put("task_name", fList.getTaskName());
                            map.put("train_no", fList.getTrainNo());
                            map.put("work_state", fList.getWorkState());
                            map.put("work_date", fList.getWorkDate());
                            map.put("task_source", 3);
                            map.put("audit_type", fList.getAuditType());
                            map.put("job_id", fList.getJobId());
                            map.put("taskLabel", fList.getJobId());
                            lst.add(map);
                        }
                    }
                }

                //当前请点类型是否在相关任务中有请点过
                List<ATaskList> existTasks = taskListService.checkTaskRequested(taskLists.toString(), pointType);

                for (int k = 0; k < lst.size(); k++) {
                    Map _temp = lst.get(k);
                    String taskId = String.valueOf(_temp.get("id"));
                    for (int z = 0; z < existTasks.size(); z++) {
                        ATaskList taskList = existTasks.get(z);
                        if (taskId.equals(taskList.getId())) {
                            lst.remove(k);
                        }
                    }
                }
                result.setDataList(lst);
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
     * 获取任务工单
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getTaskList")
    public void getTaskList(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        String userId = request.getParameter("accessToken");
        String departId = request.getParameter("departId");
        List<TSRole> rolelist = roleService.getByUserId(userId);
        //查询所有用户
        TSBaseUserQueryParam baseUserParam = new TSBaseUserQueryParam();
        baseUserParam.setPageNo(1);
        baseUserParam.setPageSize(10000);
        baseUserParam.setDeleteFlag(0);
        Page<TSBaseUser> userPage = baseUserService.query(baseUserParam);
        List<TSBaseUser> userList = userPage.getResults();

        if (null == rolelist || rolelist.size() <= 0) rolelist = new ArrayList();
        boolean ismanager = false;
        for (TSRole role : rolelist) {
            if ("1040".equals(role.getRolecode())) {
                ismanager = true;
                break;
            }
        }

        List<Map<String, Object>> aTaskLists = new ArrayList();
        ATaskListQueryParam param = new ATaskListQueryParam();
        try {
            if (ismanager) {
                param.setTeamDepartId(departId);
                aTaskLists = taskListService.queryTaskList(param);
                if (null == aTaskLists || aTaskLists.size() <= 0) aTaskLists = new ArrayList();
                ATaskListQueryParam myparam = new ATaskListQueryParam();
                myparam.setScheOperators(userId);
                List<Map<String, Object>> mytaskList = taskListService.queryTaskList(myparam);
                if (null == mytaskList || mytaskList.size() <= 0) mytaskList = new ArrayList();
                for (Map<String, Object> mytask : mytaskList) {
                    boolean exist = false;
                    for (Map<String, Object> task : aTaskLists) {
                        if (task.get("id").toString().equals(mytask.get("id").toString())) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {//我的任务工单不存在于所有任务列表中
                        aTaskLists.add(mytask);
                    }
                }
                for (Map<String, Object> task : aTaskLists) {
                    task.put("flag", "0");//1为我的工单,0为非我的工单
                    for (Map<String, Object> mytask : mytaskList) {
                        if (task.get("id").toString().equals(mytask.get("id").toString())) {
                            task.put("flag", "1");
                            break;
                        }
                    }
                    String work_state = task.get("work_state") + "";
                    if ("0".equals(work_state)) {//未派工
                        task.put("assign_time", task.get("createtime"));//调度派发时间
                    }
                }
                //工班长任务工单未开始的，工班长还可以派工（显示在任务列表中）
                for (Map<String, Object> mytask : mytaskList) {
                    String work_state = mytask.get("work_state") + "";
                    //未开始
                    if ("1".equals(work_state)) {
                        mytask.put("flag", "0");
                        aTaskLists.add(mytask);
                    }
                }


            } else {
                param.setScheOperators(userId);
                aTaskLists = taskListService.queryTaskList(param);
                SimpleDateFormat minFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                for (Map<String, Object> task : aTaskLists) {
                    task.put("flag", "1");//1为我的工单,0为非我的工单
                }
            }
            //循环替换计划作业人员ID为名称
            for (Map<String, Object> task : aTaskLists) {
                String scheOperators = task.get("scheOperators") + "";
                if("null".equals(scheOperators)){
                    scheOperators = "";
                }
                StringBuilder scheOperatornames = new StringBuilder("");
                for(TSBaseUser u : userList){
                    if(null !=scheOperators && scheOperators.length()>0 && scheOperators.contains(u.getId())){
                        if(scheOperatornames.length()<1){
                            scheOperatornames = new StringBuilder(u.getRealname());
                        }else {
                            scheOperatornames.append(",").append(u.getRealname());
                        }
                    }
                }
                task.put("scheOperatornames",scheOperatornames.toString());
            }
            result.setDataList(aTaskLists);
            result.writer(request, response);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMsg("参数有错！");
            log.error("系统内部异常", e);
        }
    }


    /**
     * 开始任务
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "working")
    public void working(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        String userId = request.getParameter("accessToken");
        String ids = request.getParameter("ids");
        if(ids == null){
            result.setSuccess(false);
            result.setMsg("参数错误");
            result.writer(request,response);
            return;
        }
        String id[] = ids.split(",");
        try {
            for (int i = 0; i < id.length; i++) {
                ATaskList taskList = taskListService.getById(id[i]);
                taskList.setModifor(userId);
                taskList.setModifytime(new Date());
                taskList.setStartJob(new Date());
                taskList.setWorkState(2);
                taskListService.update(taskList);
            }
            result.writer(request, response);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMsg("参数有错！");
            log.error("系统内部异常", e);
        }
    }


    /**
     * 获取作业本信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getTaskDetail")
    public void getTaskPackage(HttpServletRequest request, HttpServletResponse response) {
        //查询所有用户
        TSBaseUserQueryParam baseUserParam = new TSBaseUserQueryParam();
        baseUserParam.setPageNo(1);
        baseUserParam.setPageSize(10000);
        baseUserParam.setDeleteFlag(0);
        Page<TSBaseUser> userPage = baseUserService.query(baseUserParam);
        List<TSBaseUser> uList = userPage.getResults();
        AppResult result = new AppResult();
        String taskId = request.getParameter("taskId");
        ATaskList taskList = taskListService.getById(taskId);
        int taskSource = taskList.getTaskSource();
        if (taskSource == 6 || taskSource == 9) {//任务交接或部分完成
            taskSource = taskList.getPreTransferSource();
        }
        Map listMap = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            //取作业信息
            if (taskSource == 1 || taskSource == 5 || taskSource == 7 || taskSource == 8) {  //1：系统修 5：日检/四日检,7：定额; 8常规任务
                list = taskListService.getTaskJob(taskId);
            } else if (taskSource == 2) {  //2:普查整改
                list = taskListService.getTaskRefrom(taskId);
            } else if (taskSource == 4) {  //4：临时
                list = taskListService.getTaskTemp(taskId);
            } else if (taskSource == 6) {//任务交接
                String userId = taskList.getCreator();
                String scheOperators = taskList.getScheOperators();
                String date = DateUtils.date2Str(taskList.getCreatetime(), DateUtils.datetimeFormat);
                if (StringUtils.isNotBlank(userId)) {
                    TSBaseUser user = baseUserService.getById(userId);
//                    list.get(0).put("createUser", user.getRealname());
                    listMap.put("createUser", user.getRealname());
                }
                if (StringUtils.isNotBlank(scheOperators)) {
                    List<Map<String, Object>> userList = userService.getUserListByIds(scheOperators);
                    StringBuilder connect = new StringBuilder("");
                    for (int i = 0; i < userList.size(); i++) {
                        connect.append(userList.get(i).get("realname")).append(",");
                    }
//                    connect = StringUtils.substring(connect, 0, connect.length() - 1);
                    listMap.put("connect", connect.substring(0,connect.length()-1));
                    listMap.put("connectDate", date);
//                    list.get(0).put("connect", connect);
//                    list.get(0).put("connectDate", date);
                    list.add(listMap);
                }
            }
            List<Map<String, String>> parentTaskList = new ArrayList();
            String parentTaskId=taskId;
            if(taskList.getTaskSource()==9) {
            	parentTaskId = taskList.getParentTaskId();
            }
            List<ATaskList> pTaskList = taskListService.getByParentTaskId(parentTaskId);
            for (ATaskList t : pTaskList) {
                Map<String, String> map = new HashMap();
                map.put("id", t.getId());
                StringBuilder scheOperatornames = new StringBuilder("");
                for(TSBaseUser u : uList){
                    if(null !=t.getScheOperators() && t.getScheOperators().length()>0 && t.getScheOperators().contains(u.getId())){
                        if(scheOperatornames.length()<1){
                            scheOperatornames.append(u.getRealname());
                        }else {
                            scheOperatornames.append(",").append(u.getRealname());
                        }
                    }
                }
//                TSBaseUser user = baseUserService.getById(t.getScheOperators());
                map.put("scheOperators", scheOperatornames.toString());//作业人员
                map.put("workState", t.getWorkState() + "");//作业状态
                map.put("surplusValue", t.getSurplusValue() + "");//剩余百分比
                map.put("finishJobStr", DateUtils.date2Str(t.getFinishJob(), DateUtils.datetimeFormat));
                map.put("remarks", t.getRemarks());//完成情况
                parentTaskList.add(map);
            }

            if(list.size() == 0){
                ATaskListHis his = taskListHisService.getById(taskList.getParentTaskId());
                listMap.put("id", taskList.getId());
                listMap.put("taskName", taskList.getTaskName()== null ? "" : taskList.getTaskName());
                listMap.put("taskHours", taskList.getTaskHours()== null ? "" : taskList.getTaskHours());
                listMap.put("coeDiff", taskList.getCoeDiff()== null ? "" : taskList.getCoeDiff());
                listMap.put("coeOvertime", taskList.getCoeOvertime()== null ? "" : taskList.getCoeOvertime());
                listMap.put("coeCooperation", taskList.getCoeCooperation()== null ? "" : taskList.getCoeCooperation());
                listMap.put("taskRemark", taskList.getRemarks()== null ? "" : taskList.getRemarks());
                listMap.put("workState", taskList.getWorkState()== null ? "" : taskList.getWorkState());//作业状态
                listMap.put("surplusValue", taskList.getSurplusValue()== null ? "" : taskList.getSurplusValue());//剩余百分比
                StringBuilder scheNames = new StringBuilder("");
                for(TSBaseUser u : uList){
                    if(null !=taskList.getScheOperators() && taskList.getScheOperators().length()>0 && taskList.getScheOperators().contains(u.getId())){
                        if(scheNames.length()<1){
                            scheNames.append(u.getRealname());
                        }else {
                            scheNames.append(",").append(u.getRealname());
                        }
                    }
                }
//                TSBaseUser user = baseUserService.getById(taskList.getScheOperators());
//                listMap.put("scheOperators", user == null ? "" : user.getRealname());//作业人员
                listMap.put("scheOperators", scheNames.toString());//作业人员
                listMap.put("connect", scheNames.toString());
                listMap.put("connectDate", DateUtils.date2Str(taskList.getCreatetime(), DateUtils.datetimeFormat));
                listMap.put("attachePath", "");
                listMap.put("contents", "");
                Map<String, String> hisMap = new HashMap();
                if( null!= his ) {
                    hisMap.put("id", his.getId());
//                TSBaseUser hisUser = baseUserService.getById(his.getScheOperators());
                    StringBuilder scheOperatornames = new StringBuilder("");
                    for (TSBaseUser u : uList) {
                        if (null != his.getScheOperators() && his.getScheOperators().length()>0 && his.getScheOperators().contains(u.getId())) {
                            if (scheOperatornames.length()<1) {
                                scheOperatornames = new StringBuilder(u.getRealname());
                            } else {
                                scheOperatornames.append(",").append(u.getRealname());
                            }
                        }
                    }
//                hisMap.put("scheOperators", hisUser == null ? "" : hisUser.getRealname());//作业人员
                    hisMap.put("scheOperators", scheOperatornames.toString());//作业人员
                    hisMap.put("workState", his.getWorkState() + "");//作业状态
                    hisMap.put("surplusValue", taskList.getSurplusValue() + "");//剩余百分比
                    hisMap.put("finishJobStr", DateUtils.date2Str(his.getFinishJob(), DateUtils.datetimeFormat));
                    hisMap.put("remarks", his.getRemarks());//完成情况
                    parentTaskList.add(hisMap);
                }
                listMap.put("parentTaskList", parentTaskList);
                list.add(listMap);
            }else {
                list.get(0).put("parentTaskList", parentTaskList);
            }
            if(parentTaskList.size()==0){
                Map<String, String> hisMap = new HashMap();
                hisMap.put("id", taskList.getId());
//                TSBaseUser hisUser = baseUserService.getById(taskList.getScheOperators());
//                hisMap.put("scheOperators", hisUser == null ? "" : hisUser.getRealname());//作业人员
                StringBuilder scheOperatornames = new StringBuilder("");
                for(TSBaseUser u : uList){
                    if(null !=taskList.getScheOperators() && taskList.getScheOperators().length()>0 && taskList.getScheOperators().contains(u.getId())){
                        if(scheOperatornames.length()<1){
                            scheOperatornames.append(u.getRealname());
                        }else {
                            scheOperatornames.append(",").append(u.getRealname());
                        }
                    }
                }
                hisMap.put("scheOperators", scheOperatornames.toString());//作业人员
                hisMap.put("workState", taskList.getWorkState() + "");//作业状态
                hisMap.put("surplusValue", taskList.getSurplusValue()==null?"":taskList.getSurplusValue() + "");//剩余百分比
                hisMap.put("finishJobStr", DateUtils.date2Str(taskList.getFinishJob(), DateUtils.datetimeFormat));
                hisMap.put("remarks", taskList.getRemarks());//完成情况
                parentTaskList.add(hisMap);
                list.get(0).put("parentTaskList", parentTaskList);
            }
            result.setDataList(list);
            result.writer(request, response);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMsg("参数有错！");
            log.error("系统内部异常", e);
        }
    }


    /**
     * 确认任务完成
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "finishJob")
    public void finishJob(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        try {
            String json = request.getParameter("data");
            Map jsonMap = JSON.parseObject(json, Map.class);
            taskListService.saveJobToFinish(jsonMap);
            result.writer(request, response);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMsg("提交失败！");
            log.error("系统内部异常", e);
        }
    }


    /**
     * 工班长指派任务
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "assign")
    public void assign(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        String accessToken = request.getParameter("accessToken");
        String userIds = request.getParameter("userIds");
        String ids = request.getParameter("ids");
        if(ids == null || userIds == null){
            result.setSuccess(false);
            result.setMsg("参数错误");
            result.writer(request,response);
            return;
        }
        String id[] = ids.split(",");
        try {
            Calendar current = Calendar.getInstance();
            StringBuilder tasks = new StringBuilder("");
            for (int i = 0; i < id.length; i++) {
                ATaskList taskList = taskListService.getById(id[i]);
                String ppointId = taskList.getPpointId();
                if (StringUtils.isBlank(ppointId)) {
                    taskList.setWorkState(1);
                } else {
                    taskList.setWorkState(2);
                }
                taskList.setScheOperators(userIds);
                taskList.setModifor(accessToken);
                taskList.setModifytime(current.getTime());
                taskList.setAssignTime(current.getTime());
                taskListService.update(taskList);
                if (tasks.length()>0) {
                    tasks.append(",");
                }
                tasks.append(taskList.getTaskName()).append("[").append(taskList.getTrainNo()).append("]");
            }
            String[] userArray = userIds.split(",");
            for (int i = 0; i < userArray.length; i++) {
                jpushService.sendMessage(ConstantValue.PUSH_MSG_GONGDAN, userArray[i], "新的工单" + tasks.toString());
                HttpClientUtilsToServer.getInstance().doGet(serverPath, userArray[i], ConstantValue.PUSH_MSG_GONGDAN, "新的工单" + tasks);
            }
            result.writer(request, response);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMsg("参数有错！");
            log.error("系统内部异常", e);
        }
    }


    /**
     * 交接
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "returnToScheduler")
    public void returnToScheduler(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        String ids = request.getParameter("ids");
        String userId = request.getParameter("accessToken");
        String transferType = request.getParameter("transferType");
        String transferUserIds = request.getParameter("transferUserIds");
        String remarks = request.getParameter("remarks");
        if(ids == null || transferUserIds == null){
            result.setSuccess(false);
            result.setMsg("参数不能为空！");
            result.writer(request, response);
            return;
        }
        String id[] = ids.split(",");
        if ("3".equals(transferType)) {
            if (StringUtils.isBlank(transferUserIds)) {
                result.setSuccess(false);
                result.setMsg("交接人不能为空！");
                result.writer(request, response);
                return;
            }
        }
        try {
            for (int i = 0; i < id.length; i++) {
                taskListService.saveTaskTransfer(id[i], transferType, transferUserIds, remarks, userId);
            }
            result.writer(request, response);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMsg("参数有错！");
            log.error("系统内部异常", e);
        }
    }


    /**
     * 获取当前登录用户的清点车
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "pointCar")
    public void pointCar(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        String userId = request.getParameter("accessToken");
        try {
            List<PointList> pointLists = pointListService.getAllParentPointList();
            List<Map<String, String>> list = new ArrayList();
            if (null != pointLists && pointLists.size() > 0) {
                for (PointList pointList : pointLists) {
                    Map<String, String> map = new HashMap();
                    map.put("id", pointList.getId());
                    map.put("trainNo", pointList.getTrainNo());
                    list.add(map);
                }
            }
//            List<Map<String,Object>> list = taskListService.pointCar(userId);
            result.setDataList(list);
            result.writer(request, response);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMsg("参数有错！");
            log.error("系统内部异常", e);
        }
    }


    /**
     * 获取作业人员列表
     * type定义：
     * 所有作业人员：1
     * 同班组作业人员，21，单选；22：多选
     * 工班长 3
     * 他检人 4
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "workUserList")
    public void workUserList(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        String departId = request.getParameter("departId");
        String type = request.getParameter("type");
        String userId = request.getParameter("accessToken");
        try {
            List<Map<String, Object>> list = new ArrayList();
            List<TSBaseUser> userList = null;
            if ("1".equals(type)) {//所有作业人员
                userList = userService.getTSBaseUserListByRoleCode("1050");
            } else if ("2".equals(type)) {//同班组作业人员，2
                userList = userService.getTSBaseUserListByDepartId(departId);
            } else if ("3".equals(type)) {//工班长 3
                userList = userService.getTSBaseUserListByRoleCode("1040");
            } else if ("4".equals(type)) {//他检人 4
                userList = taskListService.getTSBaseUserListByRoleCode("1040,1042,1043,1045");//所有工班长1040、班组质检员1043、班组安全员1045、分部质检员1042
                List<TSBaseUser> orgUserList = taskListService.getGroupManagerByUserId(userId);
                userList.addAll(orgUserList);
            } else if ("5".equals(type)) {//当日上班所有作业人员，2
                //取今天的排班，排班时间在08:00-
                String currendate = CommonUtils.getCurrentShiftdate();
                TaskShiftSchedulerHoliday shift = taskListService.getByWorkdate(currendate);
                String departIds = shift.getDayShift() + "," + shift.getNightShift();
                if (null != shift.getRepairOne() && shift.getRepairOne().length()>0) {
                    departIds += "," + shift.getRepairOne();
                }
                if (null != shift.getRepairTwo() && shift.getRepairTwo().length()>0) {
                    departIds += "," + shift.getRepairTwo();
                }
                userList = userService.getTSBaseUserListByDepartId(departIds);
            }

            if (null != userList && userList.size() > 0) {
                Map existMap = new HashMap();
                for (TSBaseUser record : userList) {
                    Map usermap = new HashMap();
                    if (null == existMap.get(record.getId())) {
                        usermap.put("id", record.getId());
                        usermap.put("userName", record.getUsername());
                        usermap.put("realName", record.getRealname());
                        list.add(usermap);
                        existMap.put(record.getId(), "1");
                    }
                }
            }
//          List<Map<String ,Object>> list = departService.getWorkUserList(departId);
            result.setDataList(list);
            result.writer(request, response);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMsg("参数有错！");
            log.error("系统内部异常", e);
        }
    }


    @RequestMapping(value = "workUserList2")
    public void workUserList2(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        List<Map<String, Object>> list2 = new ArrayList();
        try {
            List<TSBaseUser> userList = null;
            //取今天的排班
            String departIds = "4028801062473980016247c01cce00eb";
            userList = userService.getTSBaseUserListByDepartId(departIds);
            TSDepart depart = departService.getById(departIds);
            List<TSType> resultList = tsTypeService.queryListByCode("postType");
            Map postMap = new HashMap();
            for(TSType type:resultList) {
                postMap.put(type.getTypecode(),type.getTypename());
            }
            if (null != userList && userList.size() > 0) {
                List<Map<String, Object>> list = new ArrayList();
                Map existMap = new HashMap();
                addUserList(userList, postMap, list, depart);
                existMap.put("departId",depart.getId());
                existMap.put("departName", depart.getDepartname());
                existMap.put("list", list);
                list2.add(0, existMap);
            }

            departIds = "4028801062473980016247c0474000ed";
            userList = userService.getTSBaseUserListByDepartId(departIds);
            depart = departService.getById(departIds);
            if (null != userList && userList.size() > 0) {
                List<Map<String, Object>> list = new ArrayList();
                Map existMap = new HashMap();
                addUserList(userList, postMap, list, depart);
                existMap.put("departId",depart.getId());
                existMap.put("departName", depart.getDepartname());
                existMap.put("list", list);
                list2.add(1, existMap);
            }
            departIds = "4028801062473980016247c087aa00ef";
            userList = userService.getTSBaseUserListByDepartId(departIds);
            depart = departService.getById(departIds);
            if (null != userList && userList.size() > 0) {
                List<Map<String, Object>> list = new ArrayList();
                Map existMap = new HashMap();
                addUserList(userList, postMap, list, depart);
                existMap.put("departId",depart.getId());
                existMap.put("departName", depart.getDepartname());
                existMap.put("list", list);
                list2.add(2, existMap);
            }
            departIds = "4028801062473980016247c0adcc00f1";
            userList = userService.getTSBaseUserListByDepartId(departIds);
            depart = departService.getById(departIds);
            if (null != userList && userList.size() > 0) {
                List<Map<String, Object>> list = new ArrayList();
                Map existMap = new HashMap();
                addUserList(userList, postMap, list, depart);
                existMap.put("departId",depart.getId());
                existMap.put("departName", depart.getDepartname());
                existMap.put("list", list);
                list2.add(3, existMap);
            }
            departIds = "4028801062473980016247c0d67000f3";
            userList = userService.getTSBaseUserListByDepartId(departIds);
            depart = departService.getById(departIds);
            if (null != userList && userList.size() > 0) {
                List<Map<String, Object>> list = new ArrayList();
                Map existMap = new HashMap();
                addUserList(userList, postMap, list, depart);
                existMap.put("departId",depart.getId());
                existMap.put("departName", depart.getDepartname());
                existMap.put("list", list);
                list2.add(4, existMap);
            }
            departIds = "4028801062473980016247c0f9be00f5";
            userList = userService.getTSBaseUserListByDepartId(departIds);
            depart = departService.getById(departIds);
            if (null != userList && userList.size() > 0) {
                List<Map<String, Object>> list = new ArrayList();
                Map existMap = new HashMap();
                addUserList(userList, postMap, list,depart);
                existMap.put("departId",depart.getId());
                existMap.put("departName", depart.getDepartname());
                existMap.put("list", list);
                list2.add(5, existMap);
            }
            result.setDataList(list2);
            result.writer(request, response);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMsg("参数有错！");
            log.error("系统内部异常", e);
        }
    }

    private void addUserList(List<TSBaseUser> userList, Map postMap, List<Map<String, Object>> list, TSDepart depart) {
        for (TSBaseUser record : userList) {
            Map usermap = new HashMap();
            usermap.put("id", record.getId());
            usermap.put("userName", record.getUsername());
            usermap.put("realName", record.getRealname());
            usermap.put("positionCode", (null != record.getPositionCode())?record.getPositionCode():"");
            usermap.put("positionName", (null != record.getPositionCode())?postMap.get(record.getPositionCode()):"");
            usermap.put("departId", depart.getId());
            usermap.put("departName", depart.getDepartname());
            list.add(usermap);
        }
    }

    /**
     * 获取数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "viewTaskAudit")
    public void auditTask(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        String workflowId = request.getParameter("id");
        String accessToken = request.getParameter("accessToken");
        String mutualContents = "";
        String specialContents = "";
        String contents = "";
        String surplusValue = "";
        List<Map<String, String>> mutualContentsList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> specialContentsList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> contentsList = new ArrayList<Map<String, String>>();
        try {
            AWorkflowData lastedworkflowData = null;
            List<AWorkflowData> list = workflowDataService.getAllWorkflowListByWorkflowId(workflowId);
            List nodeList = new ArrayList();
            Map<String, Object> userMap = new HashMap();
            ATaskList taskLst = null;
            if (null != list && list.size() > 0) {//封装每个节点的审批人，审批者，审批时间
                StringBuilder userIds = new StringBuilder("");
                String sourceId = "";
                for (AWorkflowData record : list) {
                    if (null != record.getCreator() && record.getCreator().length()>0 && userIds.indexOf(record.getCreator()) < 0) {
                        userIds.append(",").append(record.getCreator());
                    }
                    if (null != record.getModifor() && record.getModifor().length()>0 && userIds.indexOf(record.getModifor()) < 0) {
                        userIds.append(",").append(record.getModifor());
                    }
                    if (null != record.getAuditors() && record.getAuditors().length()>0) {
                        String[] auditarray = record.getAuditors().split(",");
                        for (int i = 0; i < auditarray.length; i++) {
                            userIds.append(",").append(auditarray[i]);
                        }
                    }
                    sourceId = record.getSourceId();
                }

                String[] array = sourceId.split(",");
                taskLst = taskListService.getById(array[0]);

                if (taskLst.getTaskSource() == 1 || (null != taskLst.getPreTransferSource() && taskLst.getPreTransferSource() == 1)) {
                    ATaskList lsts = new ATaskList();
                    for (int i = 0; i < array.length; i++) {//遍历所有的任务
                        Map<String, String> map1 = new HashMap<String, String>();
                        Map<String, String> map2 = new HashMap<String, String>();
                        Map<String, String> map3 = new HashMap<String, String>();
                        lsts = taskListService.getById(array[i]);
                        TaskJob job = taskListService.getTaskJobById(lsts.getJobId());
                        String taskName = lsts.getTaskName() == null ? "" : lsts.getTaskName();
                        /**注释部分为清除html标签**/
//        				contents = job!=null && job.getContents()!=null?delHTMLTag(job.getContents()):"";
//        				mutualContents = null != job.getMutualContents()?delHTMLTag(job.getMutualContents()):"";
//        				specialContents = null !=  job.getSpecialContents()?delHTMLTag(job.getSpecialContents()):"";
                        contents = job != null && job.getContents() != null ? job.getContents() : "";
                        mutualContents = null != job.getMutualContents() ? job.getMutualContents() : "";
                        specialContents = null != job.getSpecialContents() ? job.getSpecialContents() : "";
                        map1.put("taskName", taskName);
                        map1.put("contents", contents);
                        contentsList.add(map1);
                        map2.put("taskName", taskName);
                        map2.put("mutualContents", mutualContents);
                        mutualContentsList.add(map2);
                        map3.put("taskName", taskName);
                        map3.put("specialContents", specialContents);
                        specialContentsList.add(map3);

                    }
                }
                if(taskLst!=null){
                    surplusValue = taskLst.getSurplusValue() + "";
                }

                if (taskLst.getMutualOperations() != null && taskLst.getMutualOperations().length()>0) {
                    String[] mutualarray = taskLst.getMutualOperations().split(",");
                    for (int i = 0; i < mutualarray.length; i++) {
                        userIds.append(",").append(mutualarray[i]);
                    }
                }
                if (taskLst.getSpeOperations() != null && taskLst.getSpeOperations().length()>0) {
                    String[] spearray = taskLst.getSpeOperations().split(",");
                    for (int i = 0; i < spearray.length; i++) {
                        userIds.append(",").append(spearray[i]);
                    }
                }

                List<Map<String, Object>> userList = null;
                if (StringUtils.isNotBlank(userIds.toString()) && StringUtils.isNotEmpty(userIds.toString())) {
                    userList = userService.getTSBaseUserListByUserIds(userIds.substring(1));
                }
                if (null == userList || userList.size() <= 0) userList = new ArrayList();

                for (Map<String, Object> record : userList) {
                    userMap.put(String.valueOf(record.get("id")), String.valueOf(record.get("realname")));
                }

                for (AWorkflowData record : list) {
                    String nodeCode = record.getNodeCode();
                    JSONObject node = new JSONObject();
                    node.put("id", record.getId());
                    node.put("nodeCode", record.getNodeCode());
                    node.put("nodeName", record.getNodeName());
                    String creator = String.valueOf(userMap.get(record.getCreator()));
                    node.put("creatorname", creator);
                    node.put("creator", record.getCreator());
                    node.put("createtime", DateUtils.date2Str(record.getCreatetime(), DateUtils.datetimeFormat));
                    String modifor = String.valueOf(userMap.get(record.getModifor()));
                    node.put("modiforname", modifor);
                    node.put("modifor", record.getModifor());
                    node.put("modifytime", DateUtils.date2Str(record.getModifytime(), DateUtils.datetimeFormat));
                    node.put("runState", record.getRunState());
                    node.put("auditState", record.getAuditState());
                    node.put("auditResults", record.getAuditResults());
                    node.put("auditors", record.getAuditors());
                    StringBuilder auditornames = new StringBuilder();
                    String[] auditarray = record.getAuditors().split(",");
                    for (int i = 0; i < auditarray.length; i++) {
                        String auditor = String.valueOf(userMap.get(auditarray[i]));
                        if (auditornames.length()>0) {
                            auditornames.append(",");
                        }
                        auditornames.append(auditor);
                    }
                    node.put("auditornames", auditornames.toString());
                    if (1 == record.getNodeType()) {
                        StringBuilder mutualOperators = new StringBuilder("");
                        String[] mutuarray = null != taskLst && null != taskLst.getMutualOperations() ? taskLst.getMutualOperations().split(",") : new String[]{};
                        for (int i = 0; i < mutuarray.length; i++) {
                            String auditor = String.valueOf(userMap.get(mutuarray[i]));
                            if (mutualOperators.length()>0) {
                                mutualOperators.append(",");
                            }
                            mutualOperators.append(auditor);
                        }

                        StringBuilder speOperators = new StringBuilder("");
                        StringBuilder speOperatorIds = new StringBuilder("");
                        String[] spearray = null != taskLst && null != taskLst.getSpeOperations() ? taskLst.getSpeOperations().split(",") : new String[]{};
                        for (int i = 0; i < spearray.length; i++) {
                            String auditor = String.valueOf(userMap.get(spearray[i]));
                            if (speOperators.length()>0) {
                                speOperators.append(",");
                                speOperatorIds.append(",");
                            }
                            speOperators.append(auditor);
                            speOperatorIds.append(spearray[i]);
                        }

                        node.put("mutualOperators", mutualOperators.toString());
                        node.put("speOperators", speOperators.toString());
                        node.put("speOperatorIds", speOperatorIds.toString());
                    }

                    JSONObject data = new JSONObject();
                    if (record.getDataValue() != null && record.getDataValue().length()>0) {
                        data = JSONObject.parseObject(record.getDataValue());
                    }
                    node.put("data", data.toString());
                    //他检人
                    if (1 == record.getRunState() || record.getNodeType() == 3) {
                        lastedworkflowData = record;
                    }

                    nodeList.add(node);
                }
            }
            Map jsonMap = new HashMap();
            jsonMap.put("mutualContents", mutualContentsList);
            jsonMap.put("specialContents", specialContentsList);
            jsonMap.put("contents", contentsList);

            jsonMap.put("workflowId", workflowId);
            jsonMap.put("node", nodeList);
            jsonMap.put("processCode", lastedworkflowData.getProcessCode());
            jsonMap.put("nodeCode", lastedworkflowData.getNodeCode());
            jsonMap.put("runState", lastedworkflowData.getRunState());
            if (lastedworkflowData.getAuditors().indexOf(accessToken) >= 0) {
                jsonMap.put("auditFlag", true);
            } else {
                jsonMap.put("auditFlag", false);
            }

            StringBuilder auditornames = new StringBuilder("");
            String[] auditarray = lastedworkflowData.getAuditors().split(",");
            for (int i = 0; i < auditarray.length; i++) {
                String auditor = String.valueOf(userMap.get(auditarray[i]));
                if (auditornames.length()>0) {
                    auditornames.append(",");
                }
                auditornames.append(auditor);
            }

            jsonMap.put("surplusValue", surplusValue);
            jsonMap.put("auditors", auditornames.toString());
            jsonMap.put("auditState", lastedworkflowData.getAuditState());
            jsonMap.put("auditResults", lastedworkflowData.getAuditResults().length()<1 ? lastedworkflowData.getAuditResults() : ' ');
            List lst = new ArrayList();
            lst.add(jsonMap);
            result.setDataList(lst);
            result.writer(request, response);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMsg("加载数据失败");
            log.error("系统内部异常", e);
        }
    }

    //使用正则表达式删除HTML标签
    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串 
    }


    private List getSpectialUserList(String workflowId) {

        //他检人包括：班组质检员1043；本班工班长1040；分部质检员1042及班组监督员1044
        List<TSBaseUser> userlist = taskListService.getTSBaseUserListByRoleCode("1043,1042,1044");
        if (null == userlist || userlist.size() <= 0) userlist = new ArrayList();
        //要排除的作业人员
        String excludeUser = "";
        AWorkflowData workflowData = workflowDataService.getWorkflowDataById(workflowId);
        excludeUser += workflowData.getCreator();
        excludeUser += "," + workflowData.getAuditors();

        //工班长,角色为工班长，同时与creator同一个机构
        //取
        List<TSBaseUser> orgUserList = taskListService.getGroupManagerByUserId(workflowData.getCreator());
        userlist.addAll(orgUserList);
        List list = new ArrayList();
        for (TSBaseUser user : userlist) {

            if (excludeUser.indexOf(user.getId()) < 0) {
                Map map = new HashMap();
                map.put("key", user.getId());
                map.put("value", user.getRealname());
                list.add(map);
            }
        }
        return list;
    }

    @RequestMapping(value = "submitWorkflow")
    public void submitWorkflow(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        String workflowId = request.getParameter("workflowId");
        String auditState = request.getParameter("auditState");
        String auditResults = request.getParameter("auditResults");
        String operators = request.getParameter("operators");
        String userId = request.getParameter("accessToken");


        try {
            AWorkflowData workflowData = workflowDataService.getById(workflowId);
            //三检，且到了互检,他检人由发起来指定。
            if ("3012".equals(workflowData.getProcessCode()) && "1002".equals(workflowData.getNodeCode())) {
                String[] array = workflowData.getSourceId().split(",");
                ATaskList lst = taskListService.getById(array[0]);
                operators = "assignIds=" + lst.getSpeOperations();
            }
            workflowDataService.submitWorkflowWorkflow(workflowId, Integer.valueOf(auditState), auditResults, operators, userId);
            result.writer(request, response);
        } catch (NumberFormatException e) {
            result.setSuccess(false);
            result.setMsg("加载数据失败");
            log.error("系统内部异常", e);
        }

    }

    /**
     * 清点时提示选择的车辆是否有挂牌
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "pointCheckTrainGoods")
    public void pointCheckTrainGoods(HttpServletRequest request, HttpServletResponse response) {
        AppResult result = new AppResult();
        String trainNo = request.getParameter("trainNo");
        try {
            List nodeList = new ArrayList();
            JSONObject node = new JSONObject();
            List<PointListGoods> goodsList = forgeinPointPleaseService.getPointListGoodsByTrainNo(trainNo);
            if (null != goodsList && goodsList.size() > 0) {
                JSONArray brandInfo = new JSONArray();
                for (PointListGoods pointListGoods : goodsList) {
                    JSONObject json = new JSONObject();
                    if (null == pointListGoods.getCreator() || pointListGoods.getCreator().length()<1) {
                        json.put("info", "列车[" + pointListGoods.getTrainNo() + "]有挂牌[" + pointListGoods.getGoodsName() + "][" + pointListGoods.getGoodsCode() + "],负责人为调度,请联系调度指派;");
                    } else {
                        json.put("info", "列车[" + pointListGoods.getTrainNo() + "]有挂牌[" + pointListGoods.getGoodsName() + "][" + pointListGoods.getGoodsCode() + "],负责人[" + pointListGoods.getCreator() + "];");
                    }
                    brandInfo.add(json);
                }
                node.put("brandInfo", brandInfo);
                nodeList.add(node);
            }
            result.setDataList(nodeList);
            result.writer(request, response);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMsg("参数有错！");
            log.error("系统内部异常", e);
        }
    }


}
