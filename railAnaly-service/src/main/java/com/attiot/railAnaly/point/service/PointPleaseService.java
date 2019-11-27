package com.attiot.railAnaly.point.service;


import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.base.dao.TSBaseUserDao;
import com.attiot.railAnaly.base.dao.TSDepartDao;
import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.entity.TSDepart;
import com.attiot.railAnaly.base.service.TSBaseUserService;
import com.attiot.railAnaly.base.service.TSDepartService;
import com.attiot.railAnaly.borrow.dao.ABorrowListDetailDao;
import com.attiot.railAnaly.borrow.entity.ABorrowListDetail;
import com.attiot.railAnaly.borrow.service.BorrowListDetailService;
import com.attiot.railAnaly.borrow.service.BorrowListService;
import com.attiot.railAnaly.common.util.DateUtils;
import com.attiot.railAnaly.common.util.JacksonUtil;
import com.attiot.railAnaly.common.util.StringUtil;
import com.attiot.railAnaly.fault.dao.AFaultListDao;
import com.attiot.railAnaly.fault.entity.AFaultList;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.goods.entity.PointList;
import com.attiot.railAnaly.goods.service.PointListService;
import com.attiot.railAnaly.point.dao.*;
import com.attiot.railAnaly.point.entity.*;
import com.attiot.railAnaly.point.param.APointPleaseForgeinQueryParam;
import com.attiot.railAnaly.point.param.APointPleaseQueryParam;
import com.attiot.railAnaly.task.dao.ATaskListDao;
import com.attiot.railAnaly.task.entity.ATaskList;
import com.attiot.railAnaly.task.service.TaskListService;
import com.attiot.railAnaly.task.service.TaskTableDataService;
import com.attiot.railAnaly.workflow.dao.AWorkflowDataDao;
import com.attiot.railAnaly.workflow.entity.AWorkflowData;
import com.attiot.railAnaly.workflow.service.WorkflowDataService;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PointPleaseService {
    @Autowired
    private APointPleaseDao aPointPleaseDao;
    @Autowired
    private TaskListService taskService;
    @Autowired
    private ATaskListDao aTaskListDao;
    @Autowired
    private TSBaseUserDao tSBaseUserDao;
    @Autowired
    private TSDepartDao tSDepartDao;
    @Autowired
    private ABorrowListDetailDao aBorrowListDetailDao;
    @Autowired
    private BorrowListService borrowService;
    @Autowired
    private TSBaseUserService userService;
    @Autowired
    private TSDepartService departService;
    @Autowired
    private PointPleaseBoardingService boardingService;
    @Autowired
    private APointPleaseBoardingDao aPointPleaseBoardingDao;
    @Autowired
    private APointPleaseStingerDao aPointPleaseStingerDao;
    @Autowired
    private PointPleasePowerDao pointPleasePowerDao;
    @Autowired
    private APointPleaseCartDao aPointPleaseCartDao;
    @Autowired
    private PointPleaseStingerService stingerService;
    @Autowired
    private PointPleaseCartService cartService;
    @Autowired
    private WorkflowDataService workflowDataService;
    @Autowired
    private AWorkflowDataDao aWorkflowDataDao;
    @Autowired
    private BorrowListDetailService borrowDetailService;
    @Autowired
    private TaskTableDataService taskTableDataService;
    @Autowired
    private PointListService pointListService;
    @Autowired
    private PointPleaseTransferService transferService;
    @Autowired
    private PointPleasePowerService powerService;
    @Autowired
    private APointPleaseForgeinDao forgeinDao;
    @Autowired
    private AFaultListDao aFaultListDao;


    private static final Logger logger = Logger.getLogger(PointPleaseService.class);
    /**
     * 新增
     *
     * @param aPointPlease 参数
     *
     * @author attiot
     * 2018-04-17 09:39:30
     */
    public String create(APointPlease aPointPlease){
        aPointPleaseDao.insert(aPointPlease);
        return  aPointPlease.getId();
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-17 09:39:30
     */
    public void delete(String id){
        aPointPleaseDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
    public APointPlease getById(String id){
        return  aPointPleaseDao.getById(id);
    }


    /**
     * 修改
     *
     * @param aPointPlease 参数
     *
     * @author attiot
     * 2018-04-17 09:39:30
     */
    public void update(APointPlease aPointPlease){
        aPointPleaseDao.update(aPointPlease);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-17 09:39:30
     */
    public Page<APointPlease> query(APointPleaseQueryParam param){
        Page<APointPlease> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPointPleaseDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPointPleaseDao.query(param));
        }
        return page;
    }

    /***
     * 销点-提交
     * @param jsonMap
     */
    @Transactional
    public void saveFinishPoint(Map jsonMap){

        String pointId = String.valueOf(jsonMap.get("pointId"));
        String accessToken = String.valueOf(jsonMap.get("accessToken"));
        if(StringUtil.isNotEmpty(pointId)&&StringUtil.isNotEmpty(accessToken)){
            APointPlease pointPlease = aPointPleaseDao.getById(pointId);
            pointPlease.setSaleTime(new Date());
            //获取需要保持的节点信息,并保存相关的流程
            Map<String,Object> _pointMap = bulidAuditData(pointPlease);
            String processCode = String.valueOf(jsonMap.get("processCode"));
            if("1011".equals(processCode)){//登车许可证
                String assignIds = String.valueOf(jsonMap.get("assignIds"));
                if(StringUtil.isNotEmpty(assignIds) && !"null".equals(assignIds) ){
                    _pointMap.put("assignIds",assignIds);
                }
                pointPlease.setBpmStatus("5");
                aPointPleaseDao.update(pointPlease);
                //**** 去掉三检时添加的作业反馈 by 2018-05-24   begin *****
                APointPleaseBoarding _tempBoarding =  JacksonUtil.toBean(jsonMap.get("boarding").toString(),APointPleaseBoarding.class);
                APointPleaseBoarding boarding = aPointPleaseBoardingDao.getByPointId(pointPlease.getId());
                boarding.setPantograph2(_tempBoarding.getPantograph2());
                boarding.setBattery2(_tempBoarding.getBattery2());
                boarding.setBreaker2(_tempBoarding.getBreaker2());
                boarding.setBraking2(_tempBoarding.getBraking2());
                boarding.setHandleInfo(_tempBoarding.getHandleInfo());
                aPointPleaseBoardingDao.update(boarding);
                //**** 去掉三检时添加的作业反馈 by 2018-05-24   end *****
                _pointMap.put("boarding",boarding);
            }else if("1021".equals(processCode)){//Stinger
                APointPleaseStinger _tempStinger =  JacksonUtil.toBean(jsonMap.get("stinger").toString(),APointPleaseStinger.class);
                APointPleaseStinger stinger = aPointPleaseStingerDao.getByPointId(pointPlease.getId());
                stinger.setPowerOffTime(_tempStinger.getPowerOffTime());
                stinger.setOperatorId2(_tempStinger.getOperatorId2());
                stinger.setOperatorName2(_tempStinger.getOperatorName2());
                stinger.setMonitorId2(_tempStinger.getMonitorId2());
                stinger.setMonitorName2(_tempStinger.getMonitorName2());
                aPointPleaseStingerDao.update(stinger);
                _pointMap.put("stinger",stinger);
                pointPlease.setBpmStatus("5");
                aPointPleaseDao.update(pointPlease);
                //监控人
                if(StringUtils.isNotEmpty(stinger.getMonitorId2()) && StringUtils.isNotBlank(stinger.getMonitorId2())){
                    _pointMap.put("assignIds",stinger.getMonitorId2());
                }
            }else if("1031".equals(processCode)){//人工推车
                APointPleaseCart _tempCart = JacksonUtil.toBean(jsonMap.get("cart").toString(),APointPleaseCart.class);
                APointPleaseCart cart = aPointPleaseCartDao.getByPointId(pointPlease.getId());
                cart.setB3state(_tempCart.getB3state());
                cart.setB3tool(_tempCart.getB3tool());
                cart.setBraking3(_tempCart.getBraking3());
                cart.setGroundWire3(_tempCart.getGroundWire3());
                cart.setIronShoes3(_tempCart.getIronShoes3());
                cart.setIsolatingSwitch3(_tempCart.getIsolatingSwitch3());
                cart.setPutLocation(_tempCart.getPutLocation());
                cart.setIronShoesCode3(_tempCart.getIronShoesCode3());
                aPointPleaseCartDao.update(cart);
                _pointMap.put("cart",cart);
                pointPlease.setBpmStatus("5");
                aPointPleaseDao.update(pointPlease);
            }else if("1041".equals(processCode)){
                PointPleasePower power = pointPleasePowerDao.getByPointId(pointPlease.getId());
                _pointMap.put("power",power);
                pointPlease.setBpmStatus("5");
                aPointPleaseDao.update(pointPlease);
            }

            List<AWorkflowData> list = workflowDataService.needSaveNode(processCode,pointId,accessToken,new Gson().toJson(_pointMap));

            aWorkflowDataDao.batchInsert(list);
            taskTableDataService.saveTableData(jsonMap,pointId);
            //推送消息
            for(AWorkflowData runWorkflow:list) {
                if(runWorkflow.getRunState()==1) {
                    workflowDataService.pushMsgToAppAndWeb(runWorkflow);
                }
            }
        }
    }

    /**
     * 请点保存，加事务控制
     * @param jsonMap
     */
    @Transactional
    public String  savePointWithTransaction(Map jsonMap){
        String resultInfo = "";
        logger.info("savePointWithTransaction(jsonMap):"+jsonMap);
        Map pointMap = (Map) jsonMap.get("point");
        String processCode = String.valueOf(pointMap.get("processCode"));
        APointPlease point = JacksonUtil.toBean(pointMap.toString(),APointPlease.class);
        point.setCreateDate(new Date());
        String taskIds = String.valueOf(pointMap.get("taskId"));//选中的任务ID
        String carNum = "";
        String taskName = "";

        //--1.保存请点主内容
        List taskLst = aTaskListDao.getTaskByIds(taskIds.split(","));
        logger.info("savePointWithTransaction(taskLst):"+taskLst);
        //有任务，从任务中取carnum,没任务直接取carnum中车
        StringBuilder carNum2 = new StringBuilder("");
        StringBuilder taskName2 = new StringBuilder("");
        for(int k=0;k<taskLst.size();k++){
            ATaskList taskList = (ATaskList) taskLst.get(k);
            if(k==0){
                taskName2.append(taskList.getTaskName()).append("[").append(taskList.getTrainNo()).append("]");
            }else{
                taskName2.append(",").append(taskList.getTaskName()).append("[").append(taskList.getTrainNo()).append("]");
            }
            if(carNum2.indexOf(taskList.getTrainNo())<0) {
                if(carNum2.length()>0) {
                    carNum2.append(",");
                }
                carNum2.append(taskList.getTrainNo());
            }
        }
        carNum = carNum2.toString();
        taskName = taskName2.toString();

        List faultLst = aFaultListDao.getFaultByIds(taskIds.split(","));
        logger.info("savePointWithTransaction(faultLst):"+faultLst);
        //故障工单中的车
        StringBuilder carNum3 = new StringBuilder(carNum);
        StringBuilder taskName3 = new StringBuilder(taskName);
        for(int j=0;j<faultLst.size();j++){
            AFaultList faultList = (AFaultList) faultLst.get(j);
            if(j==0){
                taskName3.append(faultList.getTaskName()).append("[").append(faultList.getTrainNo()).append("]");
            }else{
                taskName3.append(",").append(faultList.getTaskName()).append("[").append(faultList.getTrainNo()).append("]");
            }
            if(carNum3.indexOf(faultList.getTrainNo())<0) {
                if(carNum3.length()>0) {
                    carNum3.append(",");
                }
                carNum3.append(faultList.getTrainNo());
            }
        }
        carNum = carNum3.toString();
        taskName = taskName3.toString();

        if(carNum.length()<1) {
            carNum = pointMap.get("carNum")+"";
        }
        String accessToken = String.valueOf(jsonMap.get("accessToken"));
        TSBaseUser user = tSBaseUserDao.getById(accessToken);
        String departId = String.valueOf(jsonMap.get("departId"));
        TSDepart dep = tSDepartDao.getById(departId);
        logger.info("dep:"+dep+",user:"+user+",accessToken:"+accessToken+",departId:"+departId+",carNum:"+carNum);
        point.setBpmStatus("2");//直接进入审批中
        //point.setJobContent(taskName);
        point.setCreateBy(user.getId());
        point.setCarNum(carNum);
        point.setCreateName(user.getRealname());
        point.setCreateDate(new Date());
        point.setProposer(user.getUsername());
        point.setTeams(dep.getDepartname());
        point.setSysOrgCode(dep.getOrgCode());
        point.setSysCompanyCode(dep.getOrgCode());
        String pointId = this.create(point);
        logger.info("====pointId:"+pointId);

        //--2.保存借用(请点时借用)，并提交流程
        String borrowId = String.valueOf(pointMap.get("borrowId"));//借用物品ID
        logger.info("====borrowId:"+borrowId);
        if(StringUtil.isNotEmpty(borrowId)){
            borrowService.save(borrowId.split(","),user.getId(),dep.getId(),point.getId(),point.getJobContent(),"请点时选择的借用工具");//保存借用信息
        }

        //--3.保存存请点内容明细
        Map<String,Object> _pointMap = bulidAuditData(point);//封装与请点相关联的任务与借用
        logger.info("====_pointMap:"+_pointMap);
        if(null != _pointMap.get("taskId")){
            _pointMap.put("taskId",taskIds);
            _pointMap.put("taskName",taskName);
        }
        if("1010".equals(processCode)){//登车许可证
            APointPleaseBoarding boarding = JacksonUtil.toBean(jsonMap.get("boarding").toString(),APointPleaseBoarding.class);
            boarding.setCarNum(carNum);
            boarding.setPointPleaseId(pointId);
            List<PointList> list =  pointListService.getByTrainNos(carNum);
            JSONObject json  = new JSONObject();
            if(null != list && list.size()>0) {
                for(PointList pointList:list) {
                    json.put(pointList.getTrainNo(),pointList.getMetroStatus());
                }
            }
            boarding.setMetroStatus(json.toJSONString());
            aPointPleaseBoardingDao.insert(boarding);
            APointPleaseBoarding _boarding = aPointPleaseBoardingDao.getByPointId(pointId);
            _pointMap.put("boarding",_boarding);

            resultInfo = pointListService.getTrainAlarm(Integer.valueOf(_boarding.getJobType()),_boarding.getCarNum());
        }else if("1020".equals(processCode)){//Stinger
            APointPleaseStinger stinger =  JacksonUtil.toBean(jsonMap.get("stinger").toString(),APointPleaseStinger.class);
            stinger.setPleasePointId(pointId);
            aPointPleaseStingerDao.insert(stinger);
            APointPleaseStinger _stinger = aPointPleaseStingerDao.getByPointId(pointId);
            _pointMap.put("stinger",_stinger);
            //监控人
            if(StringUtils.isNotEmpty(stinger.getMonitorId()) && StringUtils.isNotBlank(stinger.getMonitorId())){
                _pointMap.put("assignIds",stinger.getMonitorId());
            }
        }else if("1030".equals(processCode)){//人工推车
            APointPleaseCart cart = JacksonUtil.toBean(jsonMap.get("cart").toString(),APointPleaseCart.class);
            cart.setPointPleaseId(pointId);
            cart.setTrainNum(carNum);
            PointList pointList =  pointListService.getByTrainNo(carNum);
            JSONObject json  = new JSONObject();
            if(pointList != null){
                json.put(carNum,pointList.getMetroStatus());
            }
            cart.setMetroStatus(json.toJSONString());
            aPointPleaseCartDao.insert(cart);
            APointPleaseCart _cart = aPointPleaseCartDao.getByPointId(pointId);
            _pointMap.put("cart",_cart);
        }else if("1040".equals(processCode)){//断送电
            PointPleasePower power = JacksonUtil.toBean(jsonMap.get("power").toString(),PointPleasePower.class);
            power = power == null?new PointPleasePower():power;
            power.setPleasePointId(pointId);
            String type = power.getType();
            if("1".equals(type)){
                power.setOperatorId("");
                power.setOperatorName("");
            }else if(type.equals(2)){
                power.setMonitorId("");
                power.setMonitorId("");
            }
            pointPleasePowerDao.insert(power);
            PointPleasePower _power = pointPleasePowerDao.getByPointId(pointId);
            _pointMap.put("power",_power);
        }

        logger.info("saveTableData,jsonMap:"+jsonMap+",pointId:"+pointId);
        //--4 保存风险预控表记录
        taskTableDataService.saveTableData(jsonMap,pointId);

        //--5.修改与请点相关联的任务/故障工单
        logger.info("editTaskPoint,taskIds:"+jsonMap);
        aTaskListDao.editTaskPoint(taskIds.split(","),pointId);
        aFaultListDao.editFaultPoint(taskIds.split(","),pointId);

        //--6.提交审批
        logger.info("needSaveNode");
        List<AWorkflowData> list = workflowDataService.needSaveNode(processCode,pointId,accessToken,new Gson().toJson(_pointMap));
        aWorkflowDataDao.batchInsert(list);
        //--7 推送消息
        for(AWorkflowData runworkflow:list) {
            if(runworkflow.getRunState()==1) {
                workflowDataService.pushMsgToAppAndWeb(runworkflow);
            }
        }
        logger.info("success.");
        return resultInfo;
    }

    /**
     * 保存请点相关信息
     * @param jsonMap
     */
    @Transactional
    public void savePoint(Map jsonMap){
        Map pointMap = (Map) jsonMap.get("point");
        String processCode = String.valueOf(pointMap.get("processCode"));
        APointPlease point = JacksonUtil.toBean(pointMap.toString(),APointPlease.class);
        point.setCreateDate(new Date());
        String taskIds = String.valueOf(pointMap.get("taskId"));//选中的任务ID
        String carNum = "";
        String taskName = "";
        List taskLst = aTaskListDao.getTaskByIds(taskIds.split(","));
        //有任务，从任务中取carnum,没任务直接取carnum中车
        StringBuilder carnum2 = new StringBuilder(carNum);
        StringBuilder taskName2 = new StringBuilder(taskName);
        for(int k=0;k<taskLst.size();k++){
            ATaskList taskList = (ATaskList) taskLst.get(k);
            if(k==0){
                taskName2.append(taskList.getTaskName()).append("[").append(taskList.getTrainNo()).append("]");

            }else{
                taskName2.append(",").append(taskList.getTaskName()).append("[").append(taskList.getTrainNo()).append("]");
            }
            if(carnum2.indexOf(taskList.getTrainNo())<0) {
                if(carnum2.length()>0) {
                    carnum2.append(",");
                }
                carnum2.append(taskList.getTrainNo());
            }
        }
        carNum = carnum2.toString();
        taskName = taskName2.toString();
        if(carNum.length()<1) {
            carNum = pointMap.get("carNum")+"";
        }
        String accessToken = String.valueOf(jsonMap.get("accessToken"));
        TSBaseUser user = tSBaseUserDao.getById(accessToken);
        String departId = String.valueOf(jsonMap.get("departId"));
        TSDepart dep = tSDepartDao.getById(departId);
        point.setBpmStatus("2");//直接进入审批中
        //point.setJobContent(taskName);
        point.setCarNum(carNum);
        point.setCreateBy(user.getId());
        point.setCreateName(user.getRealname());
        point.setCreateDate(new Date());
        point.setProposer(user.getUsername());
        point.setTeams(dep.getDepartname());
        point.setSysOrgCode(dep.getOrgCode());
        point.setSysCompanyCode(dep.getOrgCode());
        String pointId = this.create(point);
        String borrowId = String.valueOf(pointMap.get("borrowId"));//借用物品ID
        if(StringUtil.isNotEmpty(borrowId)){
            borrowService.save(borrowId.split(","),user.getId(),dep.getId(),point.getId(),point.getJobContent(),"请点时选择的借用工具");//保存借用信息
        }

        //获取需要保持的节点信息,并保存相关的流程
        Map<String,Object> _pointMap = bulidAuditData(point);
        if(null != _pointMap.get("taskId")){
            _pointMap.put("taskId",taskIds);
            _pointMap.put("taskName",taskName);
        }
        if("1010".equals(processCode)){//登车许可证
            APointPleaseBoarding boarding = JacksonUtil.toBean(jsonMap.get("boarding").toString(),APointPleaseBoarding.class);
            boarding.setCarNum(carNum);
            boarding.setPointPleaseId(pointId);
            aPointPleaseBoardingDao.insert(boarding);
            APointPleaseBoarding _boarding = aPointPleaseBoardingDao.getByPointId(pointId);
            _pointMap.put("boarding",_boarding);
        }else if("1020".equals(processCode)){//Stinger
            APointPleaseStinger stinger =  JacksonUtil.toBean(jsonMap.get("stinger").toString(),APointPleaseStinger.class);
            stinger.setPleasePointId(pointId);
            aPointPleaseStingerDao.insert(stinger);
            APointPleaseStinger _stinger = aPointPleaseStingerDao.getByPointId(pointId);
            _pointMap.put("stinger",_stinger);
            //监控人
            if(StringUtils.isNotEmpty(stinger.getMonitorId()) && StringUtils.isNotBlank(stinger.getMonitorId())){
                _pointMap.put("assignIds",stinger.getMonitorId());
            }
        }else if("1030".equals(processCode)){//人工推车
            APointPleaseCart cart = JacksonUtil.toBean(jsonMap.get("cart").toString(),APointPleaseCart.class);
            cart.setPointPleaseId(pointId);
            cart.setTrainNum(carNum);
            aPointPleaseCartDao.insert(cart);
            APointPleaseCart _cart = aPointPleaseCartDao.getByPointId(pointId);
            _pointMap.put("cart",_cart);
        }else if("1040".equals(processCode)){//断送电
            PointPleasePower power = JacksonUtil.toBean(jsonMap.get("power").toString(),PointPleasePower.class);
            power.setPleasePointId(pointId);
            pointPleasePowerDao.insert(power);
            PointPleasePower _power = pointPleasePowerDao.getByPointId(pointId);
            _pointMap.put("power",_power);
        }
        //修改任务的请点ID
        aTaskListDao.editTaskPoint(taskIds.split(","),pointId);

        List<AWorkflowData> list = workflowDataService.needSaveNode(processCode,pointId,accessToken,new Gson().toJson(_pointMap));
        aWorkflowDataDao.batchInsert(list);
        taskTableDataService.saveTableData(jsonMap,pointId);
        for(AWorkflowData runworkflow:list) {
            if(runworkflow.getRunState()==1) {
                workflowDataService.pushMsgToAppAndWeb(runworkflow);
            }
        }
    }


    /***
     * 请点json存储格式
     * @param aPointPlease
     * @return
     */
    public HashMap<String,Object> bulidAuditData(APointPlease aPointPlease){
        HashMap<String,Object> pointMap = new HashMap<>();
        pointMap.put("workingTime", DateUtils.formatDate(aPointPlease.getWorkingTime(),"yyyy-MM-dd HH:mm:ss"));
        pointMap.put("aPointPleasePage", aPointPlease);
        //查找任务信息
        List<ATaskList> taskLists = aTaskListDao.getTaskByPointId(aPointPlease.getId());
        StringBuilder taskName = new StringBuilder("");
        StringBuilder taskId = new StringBuilder("");
        for(int k=0;k<taskLists.size();k++){
            ATaskList task = taskLists.get(k);
            if(task != null) {
                if (k == 0) {
                    taskName.append(task.getTaskName()).append("[").append(task.getTrainNo()).append("]");
                    taskId.append(task.getId());
                } else {
                    taskName.append(",").append(task.getTaskName()).append("[").append(task.getTrainNo()).append("]");
                    taskId.append(",").append(task.getId());
                }
            }
        }
        pointMap.put("taskId",taskId.toString());
        pointMap.put("taskName",taskName.toString());
        //查找借用信息
        List<ABorrowListDetail> detailLst = aBorrowListDetailDao.getBorrowByPointId(aPointPlease.getId());
        StringBuilder borrowDetail = new StringBuilder("");
        StringBuilder borrowDetail_name = new StringBuilder("");
        for(int k=0;k<detailLst.size();k++){
            ABorrowListDetail detailEntity = detailLst.get(k);
            if(detailEntity != null) {
                if (k == 0) {
                    borrowDetail.append(detailEntity.getBorrowGoodsId());
                    borrowDetail_name.append(detailEntity.getBorrowGoodsName()).append("[").append(detailEntity.getCode()).append("]");
                } else {
                    borrowDetail.append(",").append(detailEntity.getBorrowGoodsId());
                    borrowDetail_name.append(",").append(detailEntity.getBorrowGoodsName()).append("[").append(detailEntity.getCode()).append("]");
                }
            }
        }
        pointMap.put("borrowDetail", borrowDetail.toString());
        pointMap.put("borrowDetail_name", borrowDetail_name.toString());
        return  pointMap;
    }


    /***
     * 交接
     * @param jsonMap
     */
    @Transactional
    public void transfer(Map jsonMap){
        String pointId = String.valueOf(jsonMap.get("pointId"));
        String accessToken = String.valueOf(jsonMap.get("accessToken"));
        String transferId = String.valueOf(jsonMap.get("transferId"));
        String transferName = String.valueOf(jsonMap.get("transferName"));
        String toolFlag = String.valueOf(jsonMap.get("toolFlag"));
        String pointType = String.valueOf(jsonMap.get("pointType"));
        if(StringUtil.isNotEmpty(pointId)&&StringUtil.isNotEmpty(transferId)){
            if("5".equals(pointType)){
                APointPleaseTransfer transfer = new APointPleaseTransfer();
                transfer.setPointPleaseId(pointId);
                transfer.setOldHolderId(accessToken);
                TSBaseUser user = userService.getById(accessToken);
                if(user != null){
                    transfer.setOldHolderName(user.getRealname());
                }
                transfer.setNewHolderId(transferId);
                transfer.setNewHolderName(transferName);
                transfer.setCreateTime(new Date());
                transferService.create(transfer);
                APointPleaseForgein forgein = forgeinDao.getById(pointId);
                forgein.setWorker(transferId);
                forgeinDao.update(forgein);
            }else{
                List<APointPlease> pointLst = aPointPleaseDao.getByIds(pointId);
                if(null!=pointLst && pointLst.size()>0){
                    List<APointPleaseTransfer> transferList = new ArrayList<>();
                    for(int k=0;k<pointLst.size();k++){
                        APointPlease pointPlease =  pointLst.get(k);
                        if("3".equals(pointPlease.getBpmStatus())){//状态为待销点
                            //交接记录
                            APointPleaseTransfer pointPleaseTransfer = new APointPleaseTransfer();
                            pointPleaseTransfer.setNewHolderId(transferId);
                            pointPleaseTransfer.setNewHolderName(transferName);
                            pointPleaseTransfer.setCreateTime(new Date());
                            pointPleaseTransfer.setPointPleaseId(pointPlease.getId());
                            if(StringUtils.isNotBlank(pointPlease.getTransferId())&&StringUtils.isNotEmpty(pointPlease.getTransferId())){
                                pointPleaseTransfer.setOldHolderId(pointPlease.getTransferId());
                                pointPleaseTransfer.setOldHolderName(pointPlease.getTransferName());
                                pointPlease.setCreateBy(pointPlease.getTransferId());
                                pointPlease.setCreateName(pointPlease.getTransferName());
                                pointPlease.setCreateDate(new Date());
                            }else{
                                pointPleaseTransfer.setOldHolderId(pointPlease.getCreateBy());
                                pointPleaseTransfer.setOldHolderName(pointPlease.getCreateName());
                            }
                            transferList.add(pointPleaseTransfer);

                            pointPlease.setTransferId(transferId);
                            pointPlease.setTransferName(transferName);
                            pointPlease.setTransferTime(new Date());
                            pointPlease.setUpdateBy(accessToken);
                            pointPlease.setUpdateDate(new Date());
                            aPointPleaseDao.update(pointPlease);

                            //请点工单交接
                            Map params = new HashMap();
                            params.put("ppointId",pointPlease.getId());
                            params.put("transferId",transferId);
                            params.put("modifor",accessToken);
                            pointListService.updateTransfer(params);

                            //工具交接
                            if("1".equals(toolFlag)){//1-工具交接，2-不交接工具
                                List<ABorrowListDetail>  detailList = borrowDetailService.getNeedGivebackBorrowDetailByPointId(pointPlease.getId());
                                if(null != detailList && detailList.size()>0) {
                                    String[]  goodIds = new String[detailList.size()];
                                    for(int l=0;l<detailList.size();l++){
                                        ABorrowListDetail detail = detailList.get(l);
                                        goodIds[l] = detail.getBorrowGoodsId();
                                    }
                                    borrowService.saveGiveBack(goodIds,accessToken,"2", transferId,pointPlease.getId());
                                }
                            }
                        }
                    }
                    if(null != transferList && transferList.size()>0){
                        transferService.insertBatch(transferList);
                    }
                }
            }
        }
    }

    public List<APointPleaseForgein> getForgeinList(APointPleaseForgeinQueryParam param){
        return forgeinDao.getForgeinList(param);
    }

    public APointPleaseForgein getForgeinById(String forgeinId){
        return  forgeinDao.getById(forgeinId);
    }

    public List<Map> getBoardingTrainContent(String sourceId) {
        return aPointPleaseBoardingDao.getBoardingTrainContent(sourceId);
    }

    public List<Map> getCartTrainContent(String sourceId) {
        return aPointPleaseCartDao.getCartTrainContent(sourceId);
    }

}
