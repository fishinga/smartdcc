package com.attiot.railAnaly.task.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.base.dao.TSBaseUserDao;
import com.attiot.railAnaly.base.dao.TSDepartDao;
import com.attiot.railAnaly.base.dao.TSUserDao;
import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.entity.TSDepart;
import com.attiot.railAnaly.base.entity.TSRoleUser;
import com.attiot.railAnaly.base.entity.TSUser;
import com.attiot.railAnaly.common.ConstantValue;
import com.attiot.railAnaly.common.Convert;
import com.attiot.railAnaly.common.HttpClientUtilsToServer;
import com.attiot.railAnaly.common.util.DateUtils;
import com.attiot.railAnaly.fault.dao.AFaultInfoDao;
import com.attiot.railAnaly.fault.dao.AFaultListDao;
import com.attiot.railAnaly.fault.entity.AFaultList;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.foundation.Session;
import com.attiot.railAnaly.foundation.SessionManager;
import com.attiot.railAnaly.foundation.exception.AppException;
import com.attiot.railAnaly.foundation.exception.ErrorInfo;
import com.attiot.railAnaly.jpush.service.JPushService;
import com.attiot.railAnaly.task.dao.*;
import com.attiot.railAnaly.task.entity.*;
import com.attiot.railAnaly.task.param.ATaskListQueryParam;
import com.attiot.railAnaly.workflow.service.WorkflowDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
@Transactional
public class TaskListService {
    @Autowired
    private ATaskListDao aTaskListDao;
    @Autowired
    private TaskTempDao taskTempDao;
    @Autowired
    private TSUserDao userDao;
    @Autowired
    private TaskSchedulerDao taskSchedulerDao;
    @Autowired
    private TSBaseUserDao tSBaseUserDao;
    @Autowired
    private TaskJobDao taskJobDao;
    @Autowired
    private TSDepartDao tsDepartDao;
    @Autowired
    private TaskShiftSchedulerHolidayDao taskShiftSchedulerHolidayDao;
    @Autowired
    private JPushService jpushService;
    @Autowired
    private TaskReformDataService taskReformDataService;
    @Autowired
    private TaskListService taskListService;
    @Autowired
    private WorkflowDataService workflowDataService;

    @Autowired
    private AFaultListDao aFaultListDao;
    @Autowired
    private AFaultInfoDao aFaultInfoDao;

    @Value("${server_path}")
    private String serverPath;

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-10 18:11:37
     */
    public Page<ATaskList> query(ATaskListQueryParam param){
        Page<ATaskList> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aTaskListDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aTaskListDao.query(param));
        }
        return page;
    }

    /**
     * 获取任务工单
     * @param param
     * @return
     */
    public List<Map<String,Object>> queryTaskList(ATaskListQueryParam param){
        List<Map<String,Object>> list = aTaskListDao.queryTaskList(param);
        return list;
    }

    /***
     *  查询需要完成的任务
     * @param departId
     * @return
     */
    public List getNeedTask(String departId,String userId){
        return  aTaskListDao.getNeedTask(departId,userId);
    }

    /***
     * 根据多个id 查询任务信息
     * @param ids
     * @return
     */
    public List getTaskByIds(String[] ids){
        return aTaskListDao.getTaskByIds(ids);
    }

    /***
     * 修改任务的请点id
     * @param ids
     * @param pointId
     */
    public void editTaskPoint(String[] ids,String pointId){
        aTaskListDao.editTaskPoint(ids,pointId);
    }


    /***
     * 根据请点ID和当前状态更新任务状态
     * @param curWorkState
     * @param newWorkState
     * @param pointId
     */
    public void editTaskWorkState(int curWorkState,int newWorkState,String pointId){
        aTaskListDao.editTaskWorkState(curWorkState,newWorkState,pointId);
    }


    /***
     * 根据请点ID 查询任务
     * @param pointId
     * @return
     */
    public List<ATaskList> getTaskByPointId(String pointId){
        return aTaskListDao.getTaskByPointId(pointId);
    }

    public ATaskList getById(String id){
        return aTaskListDao.getById(id);
    }


    /**
     * 作业包基本信息(系统修)
     * @param jobId
     * @return
     */
    public List<Map<String,Object>> getTaskJob(String jobId){
        return aTaskListDao.getTaskJob(jobId);
    }

    /**
     * 普查整改作业信息
     * @param jobId
     * @return
     */
    public List<Map<String,Object>> getTaskRefrom(String jobId){
        return aTaskListDao.getTaskRefrom(jobId);
    }


    /**
     * 获取临时作业信息
     * @param id
     * @return
     */
    public List<Map<String,Object>> getTaskTemp(String id){
        return aTaskListDao.getTaskTemp(id);
    }


    /**
     * 确认任务完成
     * @param ids
     * @param userId
     */
    public void finishJob(String ids, Integer workState, Date finishJob,String mutualers,String spetials, String remarks,String userId,Float surplusValue){
        Map params = new HashMap();
        params.put("ids", ids);
        params.put("workState",workState);
        params.put("finishJob",finishJob);
        params.put("remarks", remarks);
        params.put("userId", userId);
        params.put("mutualers", mutualers);
        params.put("spetials", spetials);
        params.put("surplusValue",surplusValue);
//    	aTaskListHisDao.insertHisList(params);
        //更新当次完成百分比
        if(surplusValue!=null) {
            List<ATaskList> aTlist = aTaskListDao.getByIds(ids);
            for (ATaskList at : aTlist) {
                BigDecimal su = new BigDecimal(surplusValue.toString());
                if (at.getSurplusValue() == null || at.getSurplusValue().toString().length()<1) {
                    BigDecimal yibai = new BigDecimal(100);
                    BigDecimal sub = yibai.subtract(su);
                    at.setFinishValue(sub.floatValue());
                } else {
                    BigDecimal yibai = new BigDecimal(at.getSurplusValue().toString());
                    BigDecimal sub = yibai.subtract(su);
                    at.setFinishValue(sub.floatValue());
                }
                aTaskListDao.update(at);
            }
        }
        aTaskListDao.finishJob(params);
    }

    public void updateParentTaskId(List<ATaskList> list) {
        aTaskListDao.updateParentTaskId(list);
    }

    /***
     * 部分完成，任务到日计划
     * @param list
     */
    public void addSchedulerFromTaskLst(String userId,List<ATaskList> list) {
        List<TaskScheduler> schedulerList = new ArrayList();

        Calendar current = Calendar.getInstance();

        for(ATaskList lst:list) {
            TaskScheduler scheduler = new TaskScheduler();
            scheduler.setId(lst.getTaskLibId());
            scheduler.setSourceJobId(lst.getJobId());
            scheduler.setJobName(lst.getTaskName());
            scheduler.setTrainNo(lst.getTrainNo());
            scheduler.setTrainUnit(lst.getTrainUnit());
            if(lst.getTaskSource()==6) {
                scheduler.setPreTransferSource(lst.getPreTransferSource());
            }else {
                scheduler.setPreTransferSource(lst.getTaskSource());
            }
//            scheduler.setPpointId(lst.getPpointId());
            scheduler.setJobSource(""+ConstantValue.TASK_SOURCE_9);//部分完成
            if(lst.getWorkDate() != null){
                scheduler.setWorkmonth(Integer.valueOf(Convert.changeDateToString(lst.getWorkDate(), "yyyyMM")));
            }
//            scheduler.setWorkDate(null);//作业日期为空
//            scheduler.setTeamDepartId(null);//作业工班为空
            scheduler.setWorkState(0);
            scheduler.setRemarks(lst.getRemarks());
            scheduler.setCreatetime(current.getTime());
            scheduler.setCreator(userId);
            scheduler.setModifor(userId);
            scheduler.setModifytime(current.getTime());
            if(null != lst.getPreTransferSource() && lst.getPreTransferSource().toString().length()>0) {
                scheduler.setPreTransferSource(lst.getPreTransferSource());
            }else {
                scheduler.setPreTransferSource(lst.getTaskSource());
            }
            JSONObject obj = new JSONObject();
            obj.put("coeCooperation",lst.getCoeCooperation());
            obj.put("taskHours",lst.getTaskHours());
            obj.put("coeDiff",lst.getCoeDiff());
            obj.put("coeOvertime",lst.getCoeOvertime());
            scheduler.setConfigExpress(obj.toString());
            scheduler.setSourceNum(lst.getSourceNum());
            scheduler.setParentTaskId(lst.getParentTaskId());
            scheduler.setSurplusValue(lst.getSurplusValue());
            schedulerList.add(scheduler);
        }
        if(schedulerList.size()>0){
            taskSchedulerDao.batchInsert(schedulerList);
        }
    }

    /**
     * 交接
     * @param id
     */
    public void returnToScheduler(String id,String userId){
        ATaskList lst = aTaskListDao.getById(id);
        boolean return_to_scheduler = true;
        if(4==lst.getTaskSource()) {//临时
            //判断是工班长创建还是调度创建
            TaskTemp temp = taskTempDao.getById(lst.getJobId());
            if(null == temp){
                throw new AppException(ErrorInfo.ERROR_RTNTOSCH);
            }
            if(temp.getTempType()==2) {//工班长创建
                return_to_scheduler = false;
            }
        }
        if(return_to_scheduler) {
            TSUser user = userDao.getById(userId);


            Calendar current = Calendar.getInstance();
            TaskScheduler scheduler = new TaskScheduler();
            scheduler.setId(lst.getTaskLibId());
            scheduler.setSourceJobId(lst.getJobId());
            scheduler.setJobName(lst.getTaskName());

            scheduler.setTrainNo(lst.getTrainNo());
            scheduler.setTrainUnit(lst.getTrainUnit());

            scheduler.setJobSource("6");
            if(lst.getTaskSource()!=6) {
                scheduler.setPreTransferSource(lst.getTaskSource());
            }else {
                scheduler.setPreTransferSource(lst.getPreTransferSource());
            }
            scheduler.setWorkmonth(Integer.valueOf(DateUtils.date2Str(lst.getWorkDate(),DateUtils.yyyyMM)));
//            scheduler.setWorkDate(null);
//            scheduler.setTeamDepartId(null);
            scheduler.setWorkState(0);
            scheduler.setCreatetime(current.getTime());
            scheduler.setCreator(user.getId());
            scheduler.setModifor(user.getId());
            scheduler.setModifytime(current.getTime());
            JSONObject obj = new JSONObject();
            obj.put("coeCooperation",lst.getCoeCooperation());
            obj.put("taskHours",lst.getTaskHours());
            obj.put("coeDiff",lst.getCoeDiff());
            obj.put("coeOvertime",lst.getCoeOvertime());
            scheduler.setConfigExpress(obj.toString());
            taskSchedulerDao.insert(scheduler);
            aTaskListDao.delete(id);
        }else {
            lst.setIsAssign(0);//未分配
            lst.setScheOperators("");
            aTaskListDao.update(lst);
        }
    }

    public void saveTaskTransfer(String id,String transferType,String transfers,String remarks,String userId){
        ATaskList lst = aTaskListDao.getById(id);
        if(lst!=null){
            lst.setRemarks(remarks);
            Calendar current = Calendar.getInstance();
            lst.setRemarks(remarks);
            lst.setWorkState(4);//任务交接
            lst.setModifor(userId);
            lst.setModifytime(current.getTime());
            lst.setFinishJob(current.getTime());
            aTaskListDao.update(lst);

            if("1".equals(transferType)) {//交接给调度
                TaskScheduler scheduler = new TaskScheduler();
                scheduler.setId(lst.getTaskLibId());
                scheduler.setSourceJobId(lst.getJobId());
                scheduler.setJobName(lst.getTaskName());
                scheduler.setTrainNo(lst.getTrainNo());
                scheduler.setTrainUnit(lst.getTrainUnit());
                if(lst.getTaskSource()==6) {
                    scheduler.setPreTransferSource(lst.getPreTransferSource());
                }else {
                    scheduler.setPreTransferSource(lst.getTaskSource());
                }
                scheduler.setPpointId(lst.getPpointId());
                scheduler.setJobSource("6");
                if(lst.getWorkDate() != null){
                    scheduler.setWorkmonth(Integer.valueOf(DateUtils.date2Str(lst.getWorkDate(),DateUtils.yyyyMM)));
                }
//                scheduler.setWorkDate(null);
                scheduler.setTeamDepartId("");
                scheduler.setWorkState(0);
                scheduler.setRemarks(remarks);
                scheduler.setCreatetime(current.getTime());
                scheduler.setCreator(userId);
                scheduler.setModifor(userId);
                scheduler.setModifytime(current.getTime());
                scheduler.setSourceNum(lst.getSourceNum());
                JSONObject obj = new JSONObject();
                obj.put("coeCooperation",lst.getCoeCooperation());
                obj.put("taskHours",lst.getTaskHours());
                obj.put("coeDiff",lst.getCoeDiff());
                obj.put("coeOvertime",lst.getCoeOvertime());
                scheduler.setConfigExpress(obj.toString());
                scheduler.setParentTaskId(lst.getParentTaskId());
                scheduler.setSurplusValue(lst.getSurplusValue());
                taskSchedulerDao.insert(scheduler);

                //消息提醒
                List<TSBaseUser> users = tSBaseUserDao.getTSBaseUserListByRoleCode("1030");
                TSBaseUser creator = tSBaseUserDao.getById(userId);
                String info = creator.getRealname()+"的交接单"+lst.getTaskName()+"["+lst.getTrainNo()+"]";
                if(null != users && users.size()>0) {
                    for(TSBaseUser bu:users) {
                        jpushService.sendMessage(ConstantValue.PUSH_MSG_GONGDAN,bu.getId(),info);
                        HttpClientUtilsToServer.getInstance().doGet(serverPath,bu.getId(),ConstantValue.PUSH_MSG_GONGDAN,info);
                    }
                }
            }else if("2".equals(transferType)) {//交接给工班长
                ATaskList newlst = new ATaskList();
                newlst.setRemarks(remarks);
                newlst.setTrainNo(lst.getTrainNo());
                newlst.setTrainUnit(lst.getTrainUnit());
                newlst.setTaskLibId(lst.getTaskLibId());
                newlst.setJobId(lst.getJobId());
                newlst.setParentJobId(lst.getParentJobId());
                newlst.setParentJobName(lst.getParentJobName());
                newlst.setAuditType(lst.getAuditType());
                newlst.setTaskSource(6);//任务交接
                newlst.setTeamDepartId(lst.getTeamDepartId());
                newlst.setPpointId(lst.getPpointId());
                newlst.setTaskName(lst.getTaskName());
                newlst.setWorkState(0);//未派工
                newlst.setIsFlow(1);
                if(lst.getTaskSource()==6) {
                    newlst.setPreTransferSource(lst.getPreTransferSource());
                }else {
                    newlst.setPreTransferSource(lst.getTaskSource());
                }
//            newlst.setScheOperators(null);
                newlst.setFlowOperators(lst.getFlowOperators());
                newlst.setTaskHours(lst.getTaskHours());
                newlst.setCoeDiff(lst.getCoeDiff());
                newlst.setCoeOvertime(lst.getCoeOvertime());
                newlst.setCoeCooperation(lst.getCoeCooperation());
                newlst.setRemarks(remarks);
                newlst.setCreator(userId);
                newlst.setModifor(userId);
                newlst.setCreatetime(current.getTime());
                newlst.setModifytime(current.getTime());
                newlst.setSourceNum(lst.getSourceNum());
                newlst.setWorkDate(lst.getWorkDate());
                newlst.setParentTaskId(lst.getParentTaskId());
                newlst.setSurplusValue(lst.getSurplusValue());
                aTaskListDao.insert(newlst);

                TSBaseUser creator = tSBaseUserDao.getById(userId);
                String info = creator.getRealname()+"的交接单"+lst.getTaskName()+"["+lst.getTrainNo()+"]";
                Map params = new HashMap();
                params.put("roleCode","1040");
                params.put("departId",lst.getTeamDepartId());
                List<TSBaseUser> users = tSBaseUserDao.getByRoleCodeAndDepartId(params);
                if(null != users && users.size()>0) {
                    for(TSBaseUser bu:users) {
                        jpushService.sendMessage(ConstantValue.PUSH_MSG_GONGDAN, bu.getId(), info);
                        HttpClientUtilsToServer.getInstance().doGet(serverPath,bu.getId(), ConstantValue.PUSH_MSG_GONGDAN, info);
                    }
                }
            }else if("3".equals(transferType)) {//交接给同班组作业人员
                ATaskList newlst = new ATaskList();
                newlst.setRemarks(remarks);
                newlst.setTrainNo(lst.getTrainNo());
                newlst.setTrainUnit(lst.getTrainUnit());
                newlst.setTaskLibId(lst.getTaskLibId());
                newlst.setJobId(lst.getJobId());
                newlst.setParentJobId(lst.getParentJobId());
                newlst.setParentJobName(lst.getParentJobName());
                newlst.setAuditType(lst.getAuditType());
                newlst.setTaskSource(6);//任务交接
                newlst.setTeamDepartId(lst.getTeamDepartId());
                newlst.setPpointId(lst.getPpointId());
                newlst.setTaskName(lst.getTaskName());
                if(null != lst.getPpointId() && lst.getPpointId().length()>0) {
                    newlst.setWorkState(2);//开始
                }else {
                    newlst.setWorkState(1);//已派工未开始
                }

                newlst.setIsFlow(1);
                if(lst.getTaskSource()==6) {
                    newlst.setPreTransferSource(lst.getPreTransferSource());
                }else {
                    newlst.setPreTransferSource(lst.getTaskSource());
                }
                List<TSDepart> departs = tsDepartDao.getByIds(transfers);
                String departIds = "";
                if(null != departs && departs.size()>0) {
                    newlst.setTeamDepartId(departs.get(0).getId());
                }
                newlst.setScheOperators(transfers);
                newlst.setFlowOperators(lst.getFlowOperators());
                newlst.setTaskHours(lst.getTaskHours());
                newlst.setCoeDiff(lst.getCoeDiff());
                newlst.setCoeOvertime(lst.getCoeOvertime());
                newlst.setCoeCooperation(lst.getCoeCooperation());
                newlst.setRemarks(remarks);
                newlst.setCreator(userId);
                newlst.setModifor(userId);
                newlst.setCreatetime(current.getTime());
                newlst.setModifytime(current.getTime());
                newlst.setSourceNum(lst.getSourceNum());
                newlst.setWorkDate(lst.getWorkDate());
                newlst.setAssignTime(current.getTime());
                newlst.setParentTaskId(lst.getParentTaskId());
                newlst.setSurplusValue(lst.getSurplusValue());
                aTaskListDao.insert(newlst);
                TSBaseUser creator = tSBaseUserDao.getById(userId);
                String info = creator.getRealname()+"的交接单"+lst.getTaskName()+"["+lst.getTrainNo()+"]";

                String[] userArray = transfers.split(",");
                for(int i=0;i<userArray.length;i++) {
                    jpushService.sendMessage(ConstantValue.PUSH_MSG_GONGDAN, userArray[i], info);
                    HttpClientUtilsToServer.getInstance().doGet(serverPath,userArray[i], ConstantValue.PUSH_MSG_GONGDAN, info);
                }
            }
        }else{

            saveFaultTransfer(id,transferType,transfers,remarks,userId);

        }


    }

    /**
     * 保存故障工单交接
     * @param id
     * @param transferType
     * @param transfers
     * @param remarks
     * @param userId
     */
    public void saveFaultTransfer(String id,String transferType,String transfers,String remarks,String userId){
        AFaultList faultList = aFaultListDao.getById(id);
        if(faultList != null) {
            faultList.setRemarks(remarks);
            Calendar current = Calendar.getInstance();
            faultList.setRemarks(remarks);
            faultList.setWorkState(4);//工单交接
            faultList.setModifor(userId);
            faultList.setFinishJob(current.getTime());
            faultList.setModifytime(current.getTime());
            aFaultListDao.update(faultList);

            if ("1".equals(transferType)) {//交接给调度
                TaskScheduler scheduler = new TaskScheduler();
                scheduler.setId(Convert.getUUId());
                scheduler.setSourceJobId(faultList.getJobId());
                scheduler.setJobName(faultList.getTaskName());
                scheduler.setTrainNo(faultList.getTrainNo());
                scheduler.setTrainUnit(faultList.getTrainUnit());
                scheduler.setPreTransferSource(3);
                scheduler.setPpointId(faultList.getPpointId());
                scheduler.setJobSource("3");
                if (faultList.getWorkDate() != null) {
                    scheduler.setWorkmonth(Integer.valueOf(Convert.changeDateToString(faultList.getWorkDate(), "yyyyMM")));
                }
//                scheduler.setWorkDate(null);
//                scheduler.setTeamDepartId(null);
                scheduler.setWorkState(0);
                scheduler.setRemarks(remarks);
                scheduler.setCreatetime(current.getTime());
                scheduler.setCreator(userId);
                scheduler.setModifor(userId);
                scheduler.setModifytime(current.getTime());
                JSONObject obj = new JSONObject();
                obj.put("coeCooperation", faultList.getCoeCooperation());
                obj.put("taskHours", faultList.getTaskHours());
                obj.put("coeDiff", faultList.getCoeDiff());
                obj.put("coeOvertime", faultList.getCoeOvertime());
                scheduler.setConfigExpress(obj.toString());
//                scheduler.setSourceNum(null);
                scheduler.setParentTaskId(faultList.getJobId());
                scheduler.setSurplusValue(faultList.getSurplusValue());
                taskSchedulerDao.insert(scheduler);

                //消息提醒
                List<TSBaseUser> users = tSBaseUserDao.getTSBaseUserListByRoleCode("1030");
                TSBaseUser creator = tSBaseUserDao.getById(userId);
                String info = creator.getRealname() + "的故障交接单" + faultList.getTaskName() + "[" + faultList.getTrainNo() + "]";
                if (null != users && users.size() > 0) {
                    for (TSBaseUser bu : users) {
                        jpushService.sendMessage(ConstantValue.PUSH_MSG_GONGDAN, bu.getId(), info);
                        HttpClientUtilsToServer.getInstance().doGet(serverPath, bu.getId(), ConstantValue.PUSH_MSG_GONGDAN, info);
                    }
                }
            } else if ("2".equals(transferType)) {//交接给工班长
                Map params = new HashMap();
                params.put("modifor", userId);
                params.put("faultState", 6);
                params.put("id", faultList.getJobId());
                aFaultInfoDao.updateFaultState(params);
            } else if ("3".equals(transferType)) {//交接给同班组作业人员
                AFaultList aFaultList = new AFaultList();
                aFaultList.setTrainNo(faultList.getTrainNo());
                aFaultList.setTrainUnit(faultList.getTrainUnit());
                aFaultList.setJobId(faultList.getJobId());
                aFaultList.setAuditType(faultList.getAuditType());
                aFaultList.setTeamDepartId(faultList.getTeamDepartId());
                aFaultList.setFaultDepart(faultList.getFaultDepart());
                aFaultList.setFaultLevel(faultList.getFaultLevel());
                aFaultList.setPpointId(faultList.getPpointId());
                aFaultList.setTaskName(faultList.getTaskName());
                aFaultList.setWorkState(1);//已派工未开始
//                aFaultList.setWorkDate(null);//作业日期为空
                aFaultList.setScheOperators(transfers);//计划作业人员
//                aFaultList.setFlowOperators(null);
//                aFaultList.setActualOperators(null);//实际作业人员
//                aFaultList.setStartJob(null);//开始作业日期
//                aFaultList.setFinishJob(null);
                aFaultList.setTaskHours(faultList.getTaskHours());
                aFaultList.setCoeDiff(faultList.getCoeDiff());
                aFaultList.setCoeOvertime(faultList.getCoeOvertime());
                aFaultList.setCoeCooperation(faultList.getCoeCooperation());
                aFaultList.setRemarks(faultList.getRemarks());//完成情况
                aFaultList.setAssignTime(current.getTime());
                aFaultList.setCreator(userId);
                aFaultList.setCreatetime(current.getTime());
                aFaultList.setModifor(userId);
                aFaultList.setModifytime(current.getTime());
                aFaultList.setSurplusValue(faultList.getSurplusValue());
                aFaultList.setFactoryJob(faultList.getFactoryJob());
                aFaultListDao.insert(aFaultList);
            }
            TSBaseUser creator = tSBaseUserDao.getById(userId);
            String info = creator.getRealname() + "的故障交接单" + faultList.getTaskName() + "[" + faultList.getTrainNo() + "]";

            String[] userArray = transfers.split(",");
            for (int i = 0; i < userArray.length; i++) {
                jpushService.sendMessage(ConstantValue.PUSH_MSG_GONGDAN, userArray[i], info);
                HttpClientUtilsToServer.getInstance().doGet(serverPath, userArray[i], ConstantValue.PUSH_MSG_GONGDAN, info);
            }
        }
    }

    /**
     * 获取当前登录用户的清点车
     * @param userId
     * @return
     */
    public List<Map<String,Object>> pointCar(String userId){
        return aTaskListDao.pointCar(userId);
    }

    public void update(ATaskList aTaskList){
        aTaskListDao.update(aTaskList);
    }

    public void updateWorkStateAndRemarks(String ids,String remarks,Integer workState,String userId){
        Map params = new HashMap();
        params.put("ids", ids);
        params.put("remarks", remarks);
        params.put("userId",userId);
        params.put("workState",workState);
        aTaskListDao.updateWorkStateAndRemarks(params);
    }

    public List<ATaskList> getByIds(String ids){
        return  aTaskListDao.getByIds(ids);
    }

    public List<TSBaseUser> getTSBaseUserListByRoleCode(String roleCode) {
        return tSBaseUserDao.getTSBaseUserListByRoleCode(roleCode);
//        String sql = "select t.id,t.realname from t_s_base_user t " +
//                "  where t.id IN(select distinct userid from t_s_role_user t1,t_s_role t2 where t1.roleid = t2.id and find_in_set(t2.rolecode,'"+roleCode+"')>0)";
//        return commonDao.findListbySql(sql);
    }

    public List<TSBaseUser> getGroupManagerByUserId(String userId) {
        return tSBaseUserDao.getGroupManagerByUserId(userId);

    }


    public List<ATaskList> getATaskListByWorkDateAndTaskSource(String workdate,Integer taskSource,String workState) {
        Map params = new HashMap();
        params.put("workdate",workdate);
        params.put("taskSource",taskSource);
        params.put("workState",workState);
        return aTaskListDao.getATaskListByWorkDateAndTaskSource(params);

    }

    public List<ATaskList> getMyUnfinishTaskList(String userId,String taskSources,String workState) {
        Map params = new HashMap();
        params.put("userId",userId);
        params.put("taskSources",taskSources);
        params.put("workState",workState);
        return aTaskListDao.getMyUnfinishTaskList(params);
    }

    public TaskJob getTaskJobById(String id) {
        return  taskJobDao.getById(id);
    }

    public TaskTemp getTaskTempById(String id) {
        TaskTemp temp = taskTempDao.getById(id);
        return temp;
    }

    public TaskShiftSchedulerHoliday getByWorkdate(String workdate) {
        return taskShiftSchedulerHolidayDao.getByWorkdate(workdate);
    }


    /***
     * 根据任务ID和请点类型判断是否有请点过的任务
     * @param taskIds
     * @param pointType
     * @return
     */
    public List<ATaskList> checkTaskRequested(String taskIds,String pointType){
        return aTaskListDao.checkTaskRequested(taskIds,pointType);
    }

    /***
     * 任务完成
     * @param jsonMap
     */
    @Transactional
    public void saveJobToFinish(Map jsonMap) {
        String userId = (String )jsonMap.get("accessToken");
        String ids = (String )jsonMap.get("ids");
        String taskRemark = (String )jsonMap.get("taskRemark");
        Integer auditType = (Integer )jsonMap.get("auditType");
        Float surplusValue = null != jsonMap.get("surplusValue")?Float.valueOf(jsonMap.get("surplusValue")+""):Float.valueOf("0");
        //互检人
        String mutualers = (String )jsonMap.get("mutualers");
        //他检人
        String spetials = (String )jsonMap.get("spetials");

        String id[] = ids.split(",");
        for(int i=0;i<id.length;i++){
            taskReformDataService.saveReformData(jsonMap,id[i]);
        }
        List<ATaskList> list = aTaskListDao.getByIds(ids);
        if(null == list || list.size()<=0) list = new ArrayList();
        JSONObject data = new JSONObject();
        JSONArray array = new JSONArray();
        for(ATaskList lst :list) {
            JSONObject obj = new JSONObject();
            obj.put("id",lst.getId());
            obj.put("trainNo",lst.getTrainNo());
            obj.put("taskSource",lst.getTaskSource());
            obj.put("taskName",lst.getTaskName());
            obj.put("startJobStr",lst.getStartJobStr());
            array.add(obj);
        }
        data.put("rows",array);
        if(auditType == 1) {//自检
            Integer workState = 3;//完成
            if(surplusValue>0) {
                workState = 6;//部分完成
            }
            taskListService.finishJob(ids,workState, Calendar.getInstance().getTime(),mutualers,spetials,taskRemark,userId,surplusValue);
            if(6==workState) {//部分完成，需要更新串联的parent_task_id
                List<ATaskList> updateList = new ArrayList();
                for(ATaskList lst:list) {
                    if(null == lst.getParentTaskId() || lst.getParentTaskId().length()<1) {
                        lst.setModifor(userId);
                        lst.setParentTaskId(lst.getId());
                        updateList.add(lst);
                    }
                    lst.setSurplusValue(surplusValue);
                    lst.setRemarks(taskRemark);
                }
                //部分完成，更新parent task id
                if(null != updateList && updateList.size()>0) {
                    taskListService.updateParentTaskId(updateList);
                }
                //记录需要回滚到计划库中
                taskListService.addSchedulerFromTaskLst(userId,list);
            }
        }else if(auditType == 2){//互检
            data.put("assignIds",mutualers);
            taskListService.finishJob(ids,5,null,mutualers,spetials,taskRemark,userId,surplusValue);
            workflowDataService.createWorkflow("3011", ids,taskRemark, data.toString(), userId);
        }else if(auditType == 3){//他检
            data.put("assignIds",mutualers);
            taskListService.finishJob(ids,5,null,mutualers,spetials,taskRemark,userId,surplusValue);
            workflowDataService.createWorkflow("3012", ids,taskRemark, data.toString(), userId);
        }
    }
    
    public List<ATaskList> getByParentTaskId(String parentTaskId){
        return aTaskListDao.getByParentTaskId(parentTaskId);
    }
}
