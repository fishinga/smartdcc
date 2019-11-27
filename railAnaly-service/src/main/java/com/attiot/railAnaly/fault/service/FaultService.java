package com.attiot.railAnaly.fault.service;

import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.base.dao.TSDepartDao;
import com.attiot.railAnaly.base.entity.TSDepart;
import com.attiot.railAnaly.fault.dao.AFaultInfoDao;
import com.attiot.railAnaly.fault.dao.AFaultListDao;
import com.attiot.railAnaly.fault.entity.AFaultInfo;
import com.attiot.railAnaly.fault.entity.AFaultList;
import com.attiot.railAnaly.workflow.service.WorkflowDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/15.
 */
@Service
public class FaultService {

    @Autowired
    private AFaultInfoDao aFaultInfoDao;

    @Autowired
    private WorkflowDataService workflowDataService;
    @Autowired
    private TSDepartDao tsDepartDao;
    @Autowired
    private AFaultListDao aFaultListDao;
    @Autowired
    private AFaultInfoDao aFaultInfotDao;
    /***
     * 保存故障提报信息并提交流程
     * @param aFaultInfo
     */
    @Transactional
    public void  saveFaultInfoWithTransaction(AFaultInfo aFaultInfo) {
        //1.保存
        aFaultInfoDao.insert(aFaultInfo);
        //2.提交流程
        workflowDataService.createWorkflow("5010",aFaultInfo.getId(),"","",aFaultInfo.getCreator());
    }

    public AFaultInfo getAFaultInfoById(String id) {
        return aFaultInfoDao.getById(id);
    }

    public AFaultList getAFaultListById(String id) {
        return aFaultListDao.getById(id);
    }

    public List<AFaultInfo> getUnfinishFaultInfoList() {
        return aFaultInfoDao.getUnfinishFaultInfoList();
    }

    public List<AFaultList> getAFaultListByJobId(String jobId) {
        return aFaultListDao.getByJobId(jobId);
    }

    /***
     * 故障工单派工
     * @param ids
     * @param  factoryJob 1员工，2厂家
     * @param auditType 1自检，2互检，3他检
     * @param operators
     */
    @Transactional
    public void assignFaultInfo(String userId,String ids,Integer factoryJob,Integer auditType,String  operators) {
        String[] array = ids.split(",");
        Calendar current = Calendar.getInstance();
        for(int i=0;i<array.length;i++) {
            String id = array[i];
            AFaultInfo aFaultInfo = aFaultInfoDao.getById(id);
            if(aFaultInfo != null){
            AFaultList aFaultList = new AFaultList();
            aFaultList.setTrainNo(aFaultInfo.getTrainNo());
            aFaultList.setTrainUnit(aFaultInfo.getTrainUnit());
            aFaultList.setJobId(id);
            aFaultList.setFactoryJob(factoryJob);
            aFaultList.setAuditType(auditType);
            List<TSDepart> departs = tsDepartDao.getDepartListByUserId(userId);
            aFaultList.setTeamDepartId(null != departs&&departs.size()>0?departs.get(0).getId():"");
            aFaultList.setFaultDepart(aFaultInfo.getFaultDepart());
            aFaultList.setFaultLevel(aFaultInfo.getFaultLevel());
            aFaultList.setPpointId("");
            aFaultList.setTaskName(aFaultInfo.getFaultContents());
            aFaultList.setWorkState(1);//已派工未开始
//            aFaultList.setWorkDate(null);//作业日期为空
            aFaultList.setScheOperators(operators);//计划作业人员
            aFaultList.setFlowOperators("");
            aFaultList.setActualOperators("");//实际作业人员
//            aFaultList.setStartJob(null);//开始作业日期
//            aFaultList.setFinishJob(null);
            aFaultList.setTaskHours(Float.valueOf("1.0"));
            aFaultList.setCoeDiff(Float.valueOf("1.0"));
            aFaultList.setCoeOvertime(Float.valueOf("1.0"));
            aFaultList.setCoeCooperation(Float.valueOf("1.0"));
            aFaultList.setRemarks("");//完成情况
            aFaultList.setAssignTime(current.getTime());
            aFaultList.setCreator(userId);
            aFaultList.setCreatetime(current.getTime());
            aFaultList.setModifor(userId);
            aFaultList.setModifytime(current.getTime());
//            aFaultList.setSurplusValue(Float.valueOf("100"));
            List<AFaultList> list = aFaultListDao.queryByJobId(id);
            //交接来的工单重新派工，工作量继承
            float surplusValue = 100L;
            if(list.size()>0) {
                for(AFaultList afList:list) {
//                    if((afList.getWorkState() == 4 || afList.getWorkState() == 6) && surplusValue > afList.getSurplusValue()) {
                    if(surplusValue > afList.getSurplusValue()) {
                        surplusValue = afList.getSurplusValue();
                    }
                }
                aFaultList.setSurplusValue(surplusValue);
            }else {
                aFaultList.setSurplusValue(Float.valueOf("100"));
            }
            //查找是否存在未开始的工单，覆盖未开始工单
            boolean isInsert = true;
            if(list.size()>0) {
                for(AFaultList record:list) {
                    if(record.getWorkState() == 1){
                        aFaultList.setId(record.getId());
                        aFaultListDao.update(aFaultList);
                        isInsert = false;
                    }
                }
            }
            if(isInsert) {
                aFaultListDao.insert(aFaultList);
            }
            Map params = new HashMap();
            params.put("modifor",userId);
            params.put("faultState",4);//状态修改为处理中
            params.put("id",id);
            aFaultInfoDao.updateFaultState(params);
            }
        }
    }

    /***
     * 根据任务ID和请点类型判断是否有请点过的任务
     * @param id
     * @return
     */
    public boolean getListStart(String id){
        boolean listStart = false;
        String[] array = id.split(",");
        for(int i=0;i<array.length;i++) {
            String infoId = array[i];
            AFaultInfo aFaultInfo = aFaultInfoDao.getById(infoId);
            //进行中的故障进行判断
            if(aFaultInfo.getFaultState() == 4){
                List<AFaultList> list = aFaultListDao.queryByJobId(infoId);
                //全部都是已经开始的工单
                boolean allStart = true;
                for(AFaultList af:list){
                    if(af.getWorkState() == 1){
                        //有未开始工单
                        allStart=false;
                    }
                }
                if(allStart){
                    listStart = true;
                }
            }
        }
        return listStart;
    }


    /***
     * 根据任务ID和请点类型判断是否有请点过的任务
     * @param taskIds
     * @param pointType
     * @return
     */
    public List<AFaultList> checkTaskRequested(String taskIds,String pointType){
        return aFaultListDao.checkTaskRequested(taskIds,pointType);
    }

    /****
     * 加载故障工单
     * @param userId
     * @return
     */
    public List<AFaultList> getMyFaultList(String userId) {
        return aFaultListDao.getByUserId(userId);
    }

    public List<AFaultList> getMyUnfinishFaultList(String userId,String workState) {
        Map params = new HashMap();
        params.put("userId",userId);
        params.put("workState",workState);
        return aFaultListDao.getMyUnfinishFaultList(params);
    }


    public AFaultList getById(String id){
        return aFaultListDao.getById(id);
    }
    public AFaultInfo getInfoById(String id){
        return aFaultInfotDao.getById(id);
    }

    public List<AFaultInfo> getByIds(String ids) {
        return aFaultInfoDao.getByIds(ids);
    }

    @Transactional
    public void submitFaultListToWorkflow(String id, Float surplusValue,String remarks,String doFaultRemarks,String mutualOperations,String speOperations,String userId) {

        AFaultList aFaultList = aFaultListDao.getById(id);
        JSONObject dataObj = new JSONObject();
        if(2==aFaultList.getAuditType() || 3==aFaultList.getAuditType()) {
            dataObj.put("assignIds",mutualOperations);//互检人
        }
        String processCode="";
        if(1==aFaultList.getAuditType()) {
            processCode="5011";
        }else if(2==aFaultList.getAuditType()) {
            processCode="5012";
        }else if(3==aFaultList.getAuditType()) {
            processCode="5013";
        }
        workflowDataService.createWorkflow(processCode, id,"", dataObj.toString(), userId);
        Map params = new HashMap();
        params.put("modifor",userId);
        params.put("workState",5);//审批中
        params.put("id",id);
        params.put("surplusValue",surplusValue);
        params.put("remarks",remarks);
        params.put("doFaultRemarks",doFaultRemarks);
        params.put("mutualOperations",mutualOperations);
        params.put("speOperations",speOperations);
        aFaultListDao.finishFaultList(params);
    }

    public List<AFaultList> getFaultByPointId(String pointId){
        return aFaultListDao.getFaultByPointId(pointId);
    }
}
