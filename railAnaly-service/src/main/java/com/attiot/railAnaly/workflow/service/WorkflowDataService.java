package com.attiot.railAnaly.workflow.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.base.dao.TSBaseUserDao;
import com.attiot.railAnaly.base.dao.TSDepartDao;
import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.entity.TSDepart;
import com.attiot.railAnaly.base.service.TSBaseUserService;
import com.attiot.railAnaly.base.service.TSUserOrgService;
import com.attiot.railAnaly.borrow.dao.ABorrowListDao;
import com.attiot.railAnaly.borrow.dao.ABorrowListDetailDao;
import com.attiot.railAnaly.borrow.entity.ABorrowList;
import com.attiot.railAnaly.borrow.entity.ABorrowListDetail;
import com.attiot.railAnaly.borrow.service.BorrowListDetailService;
import com.attiot.railAnaly.borrow.service.BorrowListService;
import com.attiot.railAnaly.common.ConstantValue;
import com.attiot.railAnaly.common.Convert;
import com.attiot.railAnaly.common.HttpClientUtilsToServer;
import com.attiot.railAnaly.common.util.DateUtils;
import com.attiot.railAnaly.common.util.JacksonUtil;
import com.attiot.railAnaly.common.util.StringUtil;
import com.attiot.railAnaly.fault.dao.AFaultInfoDao;
import com.attiot.railAnaly.fault.dao.AFaultListDao;
import com.attiot.railAnaly.fault.entity.AFaultInfo;
import com.attiot.railAnaly.fault.entity.AFaultList;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.goods.entity.ABorrowGoodLoss;
import com.attiot.railAnaly.goods.entity.ABorrowGoods;
import com.attiot.railAnaly.goods.entity.PointList;
import com.attiot.railAnaly.goods.entity.PointListGoods;
import com.attiot.railAnaly.goods.service.BorrowGoodsService;
import com.attiot.railAnaly.goods.service.GoodLossService;
import com.attiot.railAnaly.goods.service.PointListGoodsService;
import com.attiot.railAnaly.goods.service.PointListService;
import com.attiot.railAnaly.jpush.service.JPushService;
import com.attiot.railAnaly.metro.entity.TrackInfo;
import com.attiot.railAnaly.metro.service.TrackInfoService;
import com.attiot.railAnaly.point.dao.*;
import com.attiot.railAnaly.point.entity.*;
import com.attiot.railAnaly.point.service.*;
import com.attiot.railAnaly.task.dao.ATaskListDao;
import com.attiot.railAnaly.task.dao.ATaskListHisDao;
import com.attiot.railAnaly.task.entity.ATaskList;
import com.attiot.railAnaly.task.entity.ATaskListHis;
import com.attiot.railAnaly.task.service.TaskListService;
import com.attiot.railAnaly.util.Constant;
import com.attiot.railAnaly.workflow.dao.AWorkflowDataDao;
import com.attiot.railAnaly.workflow.dao.AWorkflowFormDao;
import com.attiot.railAnaly.workflow.dao.AWorkflowNodeDao;
import com.attiot.railAnaly.workflow.dao.AWorkflowProcessDao;
import com.attiot.railAnaly.workflow.entity.AWorkflowData;
import com.attiot.railAnaly.workflow.entity.AWorkflowForm;
import com.attiot.railAnaly.workflow.entity.AWorkflowNode;
import com.attiot.railAnaly.workflow.entity.AWorkflowProcess;
import com.attiot.railAnaly.workflow.param.AWorkflowDataQueryParam;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 流程记录
 *
 * @author attiot
 * 2018-04-18 18:42:09
 */
@Slf4j
@Service
public class WorkflowDataService {
    @Autowired
    private AWorkflowDataDao aWorkflowDataDao;
    @Autowired
    private  AWorkflowProcessDao aWorkflowProcessDao;
    @Autowired
    private  AWorkflowFormDao aWorkflowFormDao;
    @Autowired
    private  AWorkflowNodeDao aWorkflowNodeDao;

    @Autowired
    private WorkflowProcessService processService;
    @Autowired
    private APointPleaseDao aPointPleaseDao;
    @Autowired
    private APointPleaseBoardingDao aPointPleaseBoardingDao;
    @Autowired
    private APointPleaseCartDao aPointPleaseCartDao;
    @Autowired
    private TSBaseUserDao tSBaseUserDao;
    @Autowired
    private ABorrowListDetailDao aBorrowListDetailDao;
    @Autowired
    private ABorrowListDao aBorrowListDao;
    @Autowired
    private ATaskListDao aTaskListDao;
    @Autowired
    private ATaskListHisDao aTaskListHisDao;
    @Autowired
    private AFaultInfoDao aFaultInfoDao;
    @Autowired
    private AFaultListDao aFaultListDao;

    @Autowired
    private WorkflowFormService formService;

    @Autowired
    private WorkflowNodeService nodeService;

    @Autowired
    private TSDepartDao tsDepartDao;

    @Autowired
    private TSBaseUserService userService;
    @Autowired
    private TSUserOrgService userOrgService;
    @Autowired
    private PointPleaseService pointService;
    @Autowired
    private PointPleaseBoardingService boardingService;
    @Autowired
    private PointPleaseCartService cartService;
    @Autowired
    private PointPleaseStingerService stingerService;
    @Autowired
    private GoodLossService goodLossService;
    @Autowired
    private BorrowListService borrowListService;
    @Autowired
    private BorrowListDetailService borrowListDetailService;
    @Autowired
    private BorrowGoodsService goodsService;
    @Autowired
    private PointListService  pointListService;

    @Autowired
    private PointListGoodsService pointGoodsService;

    @Autowired
    private AWorkflowProcessDao workflowProcessDao;
    @Autowired
    private AWorkflowFormDao workflowFormDao;
    @Autowired
    private AWorkflowNodeDao workflowNodeDao;
    @Autowired
    private TaskListService taskListService;
    @Autowired
    private APointPleaseForgeinDao aPointPleaseForgeinDao;
    @Autowired
    private ATaskListForgeinDao aTaskListForgeinDao;
    @Autowired
    private PointPleasePowerDao pointPleasePowerDao;
    @Autowired
    private JPushService jpushService;
//    @Autowired
//    private BorrowListForeignService borrowListForeignService;
    @Autowired
    private PointPleasePowerService powerService;
    @Autowired
    private TrackInfoService trackInfoService;
    @Value("${server_path}")
    private String serverPath;

    /***
     * 根据主键查询
     * @param id
     * @return
     */
    public AWorkflowData getById(String id){
        return  aWorkflowDataDao.getById(id);
    }


    /**
     * 修改
     *
     * @param aWorkflowData 参数
     *
     * @author attiot
     * 2018-04-18 18:42:09
     */
    public void update(AWorkflowData aWorkflowData){
        aWorkflowDataDao.update(aWorkflowData);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-18 18:42:09
     */
    public Page<AWorkflowData> query(AWorkflowDataQueryParam param){
        Page<AWorkflowData> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aWorkflowDataDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aWorkflowDataDao.query(param));
        }
        return page;
    }

    /***
     * 根据用户查询相对应的流程
     * @param userid
     * @return
     */
    public List getBacklog(String userid) {
        return aWorkflowDataDao.getBacklog(userid);
    }
    /***
     * 根据用户查询相对应的流程new优化后
     * @param userid
     * @return
     */
    public List getBacklogNew(String userid) {
    	return aWorkflowDataDao.getBacklogNew(userid);
    }

    /***
     * 根据来源ID，查询流程记录
     * @param sourceId
     * @return
     */
    public List getBlockLogNode(String sourceId,String wbNo){
        return  aWorkflowDataDao.getBlockLogNode(sourceId,wbNo);
    }

    public List<TSBaseUser> getTSBaseUserListByUserIds(String userIds) {
        return tSBaseUserDao.getByIds(userIds);
    }

    public List<AWorkflowData> initPropertyText(List<AWorkflowData> nodes) {
        if(null != nodes && nodes.size()>0) {
            String userIds = "";
            StringBuilder userIdsBuilder = new StringBuilder("");
            for(AWorkflowData record:nodes) {
                if(null != record.getCreator() && record.getCreator().length()>0 && userIdsBuilder.indexOf(record.getCreator())<0) {
                    userIdsBuilder.append(",").append(record.getCreator());
                }
                if(null != record.getModifor() && record.getModifor().length()>0 && userIdsBuilder.indexOf(record.getModifor())<0) {
                    userIdsBuilder.append(",").append(record.getModifor());
                }
                if(null != record.getAuditors() && record.getAuditors().length()>0) {
                    String[] auditarray = record.getAuditors().split(",");
                    for(int i=0;i<auditarray.length;i++) {
                        userIdsBuilder.append(",").append(auditarray[i]);
                    }
                }
            }
            userIds = userIdsBuilder.toString();
            List<TSBaseUser> userList = userIds.length()>0? getTSBaseUserListByUserIds(userIds.substring(1)):null;
            if(null == userList || userList.size()<=0) userList = new ArrayList();
            Map<String,TSBaseUser> userMap = new HashMap();
            for(TSBaseUser tsBaseUser:userList) {
                userMap.put(tsBaseUser.getId(),tsBaseUser);
            }
            for(AWorkflowData record:nodes) {
                TSBaseUser tsBaseUser = userMap.get(record.getCreator());
                record.setCreatorName(null!=tsBaseUser?tsBaseUser.getRealname():record.getCreator());
                record.setCreatetimeStr(Convert.changeDateToString(record.getCreatetime(),"yyyy-MM-dd HH:mm:ss"));
                String auditornames = "";
                String[] auditarray = null != record.getAuditors()?record.getAuditors().split(","):null;
                if(null != auditarray && auditarray.length>0) {
                    StringBuilder au = new StringBuilder("");
                    for(int i=0;i<auditarray.length;i++) {
                        TSBaseUser auditor = userMap.get(auditarray[i]);
                        if(au.length()>0) {
                            au.append(",");
                        }
                        au.append(null!=auditor?auditor.getRealname():"");
                    }
                    auditornames = au.toString();
                }
                record.setAuditorsName(auditornames);
            }
        }
        return nodes;
    }

    /***
     * 保存节点信息
     * @param jsonMap
     */
    public void saveBlock(Map jsonMap){
        try{
        String processCode = String.valueOf(jsonMap.get("processCode"));//流程ID
        String nodeCode = String.valueOf(jsonMap.get("nodeCode"));//当前节点ID
        String id = String.valueOf(jsonMap.get("id"));
        String auditState = String.valueOf(jsonMap.get("auditState"));
        String auditResults = String.valueOf(jsonMap.get("auditResults"));
        String accessToken = String.valueOf(jsonMap.get("accessToken"));
        String departId = String.valueOf(jsonMap.get("departId"));
        if(StringUtil.isNotEmpty(processCode)){//获取业务数据
            AWorkflowData workflowData = aWorkflowDataDao.getById(id);
            if(0 == workflowData.getRunState()){
                throw new RuntimeException("流程已提交审批,请勿重复提交");
            }
            Map _map = new Gson().fromJson(workflowData.getDataValue(),Map.class);
            String processType = processCode.substring(0,1);
            if(Constant.PROCESS_TYPE_1.equals(processType)){//请销点
                APointPlease pointPlease = pointService.getById(workflowData.getSourceId());//请点信息
                if(Constant.PROCESS_CODE_1010.equals(processCode)){//登车许可请点
                    APointPleaseBoarding _tempBoarding = JacksonUtil.toBean(jsonMap.get("boarding").toString(),APointPleaseBoarding.class);
                    APointPleaseBoarding boarding = boardingService.getByPointId(pointPlease.getId());
                    if("101002".equals(nodeCode)){
                        boarding.setPantograph(_tempBoarding.getPantograph());
                        boarding.setBattery(_tempBoarding.getBattery());
                        boarding.setBraking(_tempBoarding.getBraking());
                        boarding.setBreaker(_tempBoarding.getBreaker());
                        boardingService.update(boarding);
                        _map.put("boarding",boarding);
                        if(Constant.AUDIT_STATE_1.equals(auditState)){
                            pointPlease.setBpmStatus("3");//状态改为待销点
                        }else{
                            pointPlease.setBpmStatus("4");//状态改为请点不通过
                        }
                        pointService.update(pointPlease);//修改请点信息
                        _map.put("aPointPleasePage",pointPlease);
                    }
                }else if(Constant.PROCESS_CODE_1011.equals(processCode)){//登车许可销点
                    if("101102".equals(nodeCode)) {//互检
                        String assignIds = String.valueOf(jsonMap.get("assignIds"));
                        if(StringUtils.isNotEmpty(assignIds) && StringUtils.isNotBlank(assignIds)){
                            _map.put("assignIds",assignIds);
                        }
                    }else if("101103".equals(nodeCode)){//他检
                        log.info("他检");
                    }else if("101104".equals(nodeCode)){//作业人员反馈
                        APointPleaseBoarding _tempBoarding = JacksonUtil.toBean(jsonMap.get("boarding").toString(),APointPleaseBoarding.class);
                        APointPleaseBoarding boarding = boardingService.getByPointId(pointPlease.getId());
                        boarding.setBreaker2(_tempBoarding.getBreaker2());
                        boarding.setBraking2(_tempBoarding.getBraking2());
                        boarding.setBattery2(_tempBoarding.getBattery2());
                        boarding.setPantograph2(_tempBoarding.getPantograph2());
                        boarding.setHandleInfo(_tempBoarding.getHandleInfo());
                        boardingService.update(boarding);
                        _map.put("boarding",boarding);
                        auditState = Constant.AUDIT_STATE_1;
                    }else if("101105".equals(nodeCode)){//登车许可销点调度确认
                        if(Constant.AUDIT_STATE_1.equals(auditState)){
                            pointPlease.setBpmStatus("6");//状态改为已销点
                        }else{
                            pointPlease.setBpmStatus("7");//状态改为已销点
                        }
                        pointService.update(pointPlease);//修改请点信息
                        _map.put("aPointPleasePage",pointPlease);
                    }
                }else if(Constant.PROCESS_CODE_1020.equals(processCode)){//Stinger请点
                    if(!Constant.AUDIT_STATE_1.equals(auditState)){
                        pointPlease.setBpmStatus("4");//状态改为请点不通过
                    }
                    pointService.update(pointPlease);//修改请点信息
                    if("102002".equals(nodeCode)){//监控人确认
                        APointPleaseStinger stinger = stingerService.getByPointId(pointPlease.getId());
                        stinger.setConfirmTime(new Date());//确认监控人时间
                        stinger.setConfirmerId(accessToken);//确认监控人ID
                        TSBaseUser user = userService.getById(accessToken);
                        stinger.setConfirmer(user.getRealname());
                        stingerService.update(stinger);
                        _map.put("stinger",stinger);
                        _map.put("aPointPleasePage",pointPlease);
                    }else if("102003".equals(nodeCode)){//调度审批
                        APointPleaseStinger _tempStinger = JacksonUtil.toBean(jsonMap.get("stinger").toString(),APointPleaseStinger.class);
                        APointPleaseStinger stinger = stingerService.getByPointId(pointPlease.getId());
                        stinger.setSendKeyTime(_tempStinger.getSendKeyTime());
                        stingerService.update(stinger);
                        _map.put("stinger",stinger);
                        _map.put("aPointPleasePage",pointPlease);
                    }else if("102004".equals(nodeCode)){//作业反馈
                        APointPleaseStinger _tempStinger = JacksonUtil.toBean(jsonMap.get("stinger").toString(),APointPleaseStinger.class);
                        APointPleaseStinger stinger = stingerService.getByPointId(pointPlease.getId());
                        stinger.setGivePowerTime(_tempStinger.getGivePowerTime());
                        stinger.setB1State(_tempStinger.getB1State());
                        stinger.setB2State(_tempStinger.getB2State());
                        stingerService.update(stinger);
                        _map.put("stinger",stinger);
                        _map.put("aPointPleasePage",pointPlease);
                    }else if("102005".equals(nodeCode)){
                        if(Constant.AUDIT_STATE_1.equals(auditState)){
                            pointPlease.setBpmStatus("3");//状态改为待销点
                        }else{
                            pointPlease.setBpmStatus("4");//状态改为请点不通过
                        }
                        pointService.update(pointPlease);//修改请点信息
                        _map.put("aPointPleasePage",pointPlease);
                    }
                }else if(Constant.PROCESS_CODE_1021.equals(processCode)){//Stinger销点
                    if(!Constant.AUDIT_STATE_1.equals(auditState)){
                        pointPlease.setBpmStatus("7");//状态改为销点不通过
                    }
                    pointService.update(pointPlease);//修改请点信息
                    if("102103".equals(nodeCode)){
                        APointPleaseStinger _tempStinger = JacksonUtil.toBean(jsonMap.get("stinger").toString(),APointPleaseStinger.class);
                        APointPleaseStinger stinger = stingerService.getByPointId(pointPlease.getId());
                        stinger.setTakeBackTime(_tempStinger.getTakeBackTime());
                        stingerService.update(stinger);
                        _map.put("stinger",stinger);
                    }
                    if("102104".equals(nodeCode)){//Stinger销点调度审批
                        APointPleaseStinger _tempStinger = JacksonUtil.toBean(jsonMap.get("stinger").toString(),APointPleaseStinger.class);
                        APointPleaseStinger stinger = stingerService.getByPointId(pointPlease.getId());
                        stinger.setOutageTime(_tempStinger.getOutageTime());
                        stinger.setB1State2(_tempStinger.getB1State2());
                        stinger.setB2State2(_tempStinger.getB2State2());
                        stingerService.update(stinger);
                        _map.put("stinger",stinger);
                    }else if("102105".equals(nodeCode)){
                        if(Constant.AUDIT_STATE_1.equals(auditState)){
                            pointPlease.setBpmStatus("6");//状态改为已销点
                        }else{
                            pointPlease.setBpmStatus("7");//状态改为销点不通过
                        }
                        pointService.update(pointPlease);//修改请点信息
                        _map.put("aPointPleasePage",pointPlease);
                    }
                }else if(Constant.PROCESS_CODE_1030.equals(processCode)){//人工推车请点
                    if("103002".equals(nodeCode)){
                        APointPleaseCart _tempCart = JacksonUtil.toBean(jsonMap.get("cart").toString(),APointPleaseCart.class);
                        APointPleaseCart cart = cartService.getByPointId(pointPlease.getId());
                        cart.setPointPleaseId(pointPlease.getId());
                        cart.setBlockTime(_tempCart.getBlockTime());
                        cart.setBlockTrack(_tempCart.getBlockTrack());
                        cart.setBlockArea(_tempCart.getBlockArea());
                        cart.setSignalMachine2(_tempCart.getSignalMachine2());
                        cart.setRemarks(_tempCart.getRemarks());
                        cartService.update(cart);
                        _map.put("cart",cart);
                        if(Constant.AUDIT_STATE_1.equals(auditState)){
                            pointPlease.setBpmStatus("3");//状态改为销点
                        }else{
                            pointPlease.setBpmStatus("4");
                        }
                        pointService.update(pointPlease);//修改请点信息
                        _map.put("aPointPleasePage",pointPlease);
                    }
                }else if(Constant.PROCESS_CODE_1031.equals(processCode)){//人工推车销点
                    if("103102".equals(nodeCode)){
                        if(Constant.AUDIT_STATE_1.equals(auditState)){
                            pointPlease.setBpmStatus("6");//状态改为销点
                        }else{
                            pointPlease.setBpmStatus("7");//状态改为销点审批不通过
                        }
                        pointService.update(pointPlease);//修改请点信息
                        _map.put("aPointPleasePage",pointPlease);
                    }
                }else if("1040".equals(processCode)){//断送电请点
                    if("104002".equals(nodeCode)){
                        PointPleasePower power = powerService.getByPointId(pointPlease.getId());
                        _map.put("power",power);
                        if(Constant.AUDIT_STATE_1.equals(auditState)){
                            pointPlease.setBpmStatus("3");//状态改为销点
                            List<ATaskList> taskListList = taskListService.getTaskByPointId(pointPlease.getId());
                            if(taskListList.size() > 0 ){
                                ATaskList task = taskListList.get(0);
                                task.setWorkState(2);
                                taskListService.update(task);
                            }
                            TrackInfo trackInfo = trackInfoService.getByTrackName(power.getStock());
                            trackInfo.setIsElectric(1);
                            trackInfoService.update(trackInfo);
                        }else{
                            pointPlease.setBpmStatus("4");
                        }
                        pointService.update(pointPlease);//修改请点信息
                        _map.put("aPointPleasePage",pointPlease);
                    }

                }else if("1041".equals(processCode)){//断送电销点
                    if("104102".equals(nodeCode)){
                        if(Constant.AUDIT_STATE_1.equals(auditState)){
                            pointPlease.setBpmStatus("6");//状态改为销点
                            PointPleasePower power = powerService.getByPointId(pointPlease.getId());
                            TrackInfo trackInfo = trackInfoService.getByTrackName(power.getStock());
                            trackInfo.setIsElectric(0);
                            trackInfoService.update(trackInfo);
                        }else{
                            pointPlease.setBpmStatus("7");//状态改为销点审批不通过
                        }
                        pointService.update(pointPlease);//修改请点信息
                        _map.put("aPointPleasePage",pointPlease);
                    }

                }
            }else if(Constant.PROCESS_TYPE_2.equals(processType)) {//借用归还
                if (Constant.PROCESS_CODE_2010.equals(processCode)) {//借用
                    if("201002".equals(nodeCode)){//借用调度审批
                        //修改物品状态
                        List<ABorrowListDetail>  list = borrowListDetailService.getByBorrowId(workflowData.getSourceId().split(","));
                        for(int k=0;k<list.size();k++){
                            ABorrowListDetail bd = list.get(k);
                            ABorrowGoods g = new ABorrowGoods();
                            ABorrowGoods oldG  = goodsService.getById(bd.getBorrowGoodsId());//原物品情况
                            g.setId(bd.getBorrowGoodsId());
                            if(Constant.AUDIT_STATE_1.equals(auditState)){//审批通过
                                g.setState(3);//占用物品
                            }else{//审批不通过
                            	if(oldG.getState()!=3) {
                            		g.setState(1);//库内
                            	}
                            }
                            goodsService.editState(g);

                            if(Constant.AUDIT_STATE_1.equals(auditState)){//审批通过
                                bd.setReturnType("1");
                            }else{
                                bd.setReturnType("8");
                            }
                            borrowListDetailService.update(bd);
                        }
                        if(Constant.AUDIT_STATE_1.equals(auditState)) {
                            ABorrowList borrowList = borrowListService.getById(workflowData.getSourceId());
                            if(borrowList != null){
                                borrowList.setBorrowState(4);//待还
                                /*borrowList.setDispatcher(accessToken);
                                borrowList.setDispatchTime(new Date());*/
                                borrowListService.update(borrowList);
                            }
//                            else{
//                                BorrowListForeign borrowListForeign = borrowListForeignService.getById(workflowData.getSourceId());
//                                borrowListForeign.setBorrowState(4);
//                                /*borrowListForeign.setDispatcher(accessToken);
//                                borrowListForeign.setDispatchTime(new Date());*/
//                                borrowListForeignService.update(borrowListForeign);
//                            }

                        }else{
                            ABorrowList borrowList = borrowListService.getById(workflowData.getSourceId());
                            if(borrowList != null){
                                borrowList.setBorrowState(5);//借用失败
                                /*borrowList.setDispatcher(accessToken);
                                borrowList.setDispatchTime(new Date());*/
                                borrowListService.update(borrowList);
                                APointPleaseForgein forgein = aPointPleaseForgeinDao.getById(borrowList.getPpointId());
                                if(null !=forgein){
                                    forgein.setBrands("");
                                    aPointPleaseForgeinDao.update(forgein);
                                }
                            }
//                            else{
//                                BorrowListForeign borrowListForeign = borrowListForeignService.getById(workflowData.getSourceId());
//                                borrowListForeign.setBorrowState(5);
//                                /*borrowListForeign.setDispatcher(accessToken);
//                                borrowListForeign.setDispatchTime(new Date());*/
//                                borrowListForeignService.update(borrowListForeign);
//                            }

                        }
                    }else if ("201003".equals(nodeCode)) {//物品损坏程度反馈
                        String lossStr = new Gson().toJson(jsonMap.get("loss"));
                        List lossLst = new Gson().fromJson(lossStr,List.class);
                        List _newLst = new ArrayList();
                        TSBaseUser user = userService.getById(accessToken);
                        for(int k=0;k<lossLst.size();k++){
                            ABorrowGoodLoss loss = JacksonUtil.toBean(new Gson().toJson(lossLst.get(k)), ABorrowGoodLoss.class);
                            if(null!=loss){
                                loss.setUserName(user.getRealname());
                                loss.setCreater(user.getId());
                                loss.setCreateTime(new Date());
                                loss.setUserId(user.getId());
                                String wbNo = workflowData.getWbNo();
                                loss.setWorkFlowWbNo(wbNo);
                                goodLossService.create(loss);
                                _newLst.add(loss);
                            }
                        }
                        _map.put("loss", _newLst);
                    }else if ("201004".equals(nodeCode)) {//借用调度确认
                        log.info("借用调度确认");
                        //修改物品状态
                        /*List<ABorrowListDetail>  list = borrowListDetailService.getByBorrowId(workflowData.getSourceId().split(","));
                        for(int k=0;k<list.size();k++){
                            ABorrowListDetail bd = list.get(k);
                            ABorrowGoods g = new ABorrowGoods();
                            g.setId(bd.getBorrowGoodsId());
                            if(Constant.AUDIT_STATE_1.equals(auditState)){//审批通过
                                g.setState(3);//出借物品
                            }else{//审批不通过
                                g.setState(1);//库内
                            }
                            goodsService.editState(g);
                        }
                        if(Constant.AUDIT_STATE_1.equals(auditState)){
                            ABorrowList borrowList = borrowListService.getById(workflowData.getSourceId());
                            borrowList.setBorrowState(4);//待还
                            borrowListService.update(borrowList);
                        }else{
                            ABorrowList borrowList = borrowListService.getById(workflowData.getSourceId());
                            borrowList.setBorrowState(5);//借用失败
                            borrowListService.update(borrowList);
                        }*/
                    }
                }else if (Constant.PROCESS_CODE_2011.equals(processCode)) {//归还
                    List<ABorrowListDetail> detailList = borrowListDetailService.getByReturnNum(workflowData.getSourceId());
                    List<ABorrowListDetail> _newLst = new ArrayList<>();
                    for(int k=0;k<detailList.size();k++){
                        ABorrowListDetail detail = detailList.get(k);
                        if(Constant.AUDIT_STATE_1.equals(auditState)){//审批通过
                            detail.setReturnType("3");//已还
                        }else{//审批不通过
                            detail.setReturnType("1");//待还
                        }
                        borrowListDetailService.update(detail);
                        ABorrowGoods g = new ABorrowGoods();
                        g.setId(detail.getBorrowGoodsId());
                        if(Constant.AUDIT_STATE_1.equals(auditState)){//审批通过
                            g.setState(1);//库内
                        }else{//审批不通过
                            g.setState(3);//出借物品
                        }
                        goodsService.editState(g);
                        _newLst.add(detail);
                        if(Constant.AUDIT_STATE_1.equals(auditState)){
                            ABorrowList borrowList = borrowListService.getById(detail.getBorrowListId());
                            if(borrowList != null){
                                if(null==borrowList.getReturnNum()|| "null".equals(borrowList.getReturnNum())){
                                    borrowList.setReturnNum(1);
                                }else{
                                    if(0 == borrowList.getReturnNum()){
                                        borrowList.setReturnNum(1);
                                    }else{
                                        borrowList.setReturnNum(borrowList.getReturnNum()+1);
                                    }
                                }
                                if(borrowList.getBorrowNum() == borrowList.getReturnNum()){
                                    borrowList.setBorrowState(6);//已还
                                }else if(borrowList.getReturnNum() < borrowList.getBorrowNum()){
                                    borrowList.setBorrowState(4);//部分归还
                                }
                                borrowListService.update(borrowList);
                            }
//                            else{
//                                BorrowListForeign borrowListForeign = borrowListForeignService.getById(detail.getBorrowListId());
//                                if(null==borrowListForeign.getReturnNum()|| "null".equals(borrowListForeign.getReturnNum())){
//                                    borrowListForeign.setReturnNum(1);
//                                }else{
//                                    if(0 == borrowListForeign.getReturnNum()){
//                                        borrowListForeign.setReturnNum(1);
//                                    }else{
//                                        borrowListForeign.setReturnNum(borrowListForeign.getReturnNum()+1);
//                                    }
//                                }
//                                if(borrowListForeign.getBorrowNum() == borrowListForeign.getReturnNum()){
//                                    borrowListForeign.setBorrowState(6);//已还
//                                }else if(borrowListForeign.getReturnNum() < borrowListForeign.getBorrowNum()){
//                                    borrowListForeign.setBorrowState(4);//部分归还
//                                }
//                                borrowListForeignService.update(borrowListForeign);
//                            }

                        }else{
                            ABorrowList borrowList = borrowListService.getById(workflowData.getSourceId());
                            if(borrowList != null){
                                borrowList.setBorrowState(4);//归还失败即待还
                                borrowListService.update(borrowList);
                            }
//                            else{
//                                BorrowListForeign borrowListForeign = borrowListForeignService.getById(workflowData.getSourceId());
//                                borrowListForeign.setBorrowState(4);//归还失败即待还
//                                borrowListForeignService.update(borrowListForeign);
//                            }

                        }
                    }
                    _map.put("borrowDetail",_newLst);
                }
            }
            String nextNode = "";//下一节点
            boolean endFLag = false;
            if(Constant.AUDIT_STATE_1.equals(auditState)){//审批通过
                nextNode = workflowData.getPassNodeId();
                if(agree(workflowData,accessToken)){//是否需要往下走流程
                    AWorkflowData newNode = initNode(workflowData,nextNode,new Gson().toJson(_map),accessToken,1,Integer.valueOf(auditState));
                    aWorkflowDataDao.insert(newNode);//生成下一节点数据
                    if(Constant.PROCESS_TYPE_1.equals(processType)){//请销点
                        if(3 == (newNode.getNodeType())){//请点工单
                            APointPlease pointPlease = pointService.getById(workflowData.getSourceId());//请点信息
                            if(Integer.valueOf(processCode)%2 == 0){//请点
                                if("1".equals(pointPlease.getPointType())){//登车
                                    APointPleaseBoarding boarding = boardingService.getByPointId(pointPlease.getId());
                                    pointListService.addToPointList(accessToken,boarding.getCarNum(),pointPlease.getId(),pointPlease.getJobContent(),
                                            pointPlease.getCreateBy(),pointPlease.getTransferName(),Integer.valueOf(boarding.getJobType()),pointPlease.getCreateDate(),(Float.parseFloat(String.valueOf(pointPlease.getPointHours()))) );
                                }else if("3".equals(pointPlease.getPointType())){//推车
                                    APointPleaseCart cart = cartService.getByPointId(pointPlease.getId());
                                    pointListService.addToPointList(accessToken,cart.getTrainNum(),pointPlease.getId(),pointPlease.getJobContent(),pointPlease.getCreateBy(),pointPlease.getTransferName(),
                                            2, pointPlease.getCreateDate(),(Float.parseFloat(String.valueOf(pointPlease.getPointHours()))) );
                                }
                                //*** 若是关联的请点任务,状态为派工未开始的，改为进行中  begin  ****
                                if (Constant.PROCESS_CODE_1010.equals(processCode) || Constant.PROCESS_CODE_1020.equals(processCode) || Constant.PROCESS_CODE_1030.equals(processCode)) {
                                    taskListService.editTaskWorkState(1,2,pointPlease.getId());
                                    aFaultListDao.editFaultWorkState(1,2,pointPlease.getId());
                                }
                                //*** 若是关联的请点任务,状态为派工未开始的，改为进行中  end   ****
                            }else{//销点

                                updatePointListMetroStatus(accessToken,pointPlease.getId());
                                //根据请点id删除
                                pointListService.deleteByPPointId(pointPlease.getId());
//                                if("1".equals(pointPlease.getPointType())){//登车
//                                    APointPleaseBoarding boarding = boardingService.getByPointId(pointPlease.getId());
//
//
//                                    if(boarding.getCarNum().contains(",")){
//                                        String[] split = boarding.getCarNum().split(",");
//                                        for(int k=0;k<split.length;k++){
//                                            pointListService.removePointList(split[k],pointPlease.getId(),accessToken);
//                                        }
//                                    }else{
//                                        pointListService.removePointList(boarding.getCarNum(),pointPlease.getId(),accessToken);
//                                    }
//                                }else if("3".equals(pointPlease.getPointType())){//推车
//                                    APointPleaseCart cart = cartService.getByPointId(pointPlease.getId());
//                                    if(cart.getTrainNum().contains(",")){
//                                        String[] split = cart.getTrainNum().split(",");
//                                        for(int k=0;k<split.length;k++){
//                                            pointListService.removePointList(split[k],pointPlease.getId(),accessToken);
//                                        }
//                                    }else{
//                                        pointListService.removePointList(cart.getTrainNum(),pointPlease.getId(),accessToken);
//                                    }
//                                }
                            }
                        }
                    }else if(Constant.PROCESS_TYPE_2.equals(processType)){//归还物品时拆牌
                        if(Constant.PROCESS_CODE_2011.equals(processCode)){
                            List<ABorrowListDetail> detailList = borrowListDetailService.getByReturnNum(workflowData.getSourceId());
                            StringBuilder goodIds = new StringBuilder("");
                            for(int k=0;k<detailList.size();k++){
                                ABorrowListDetail bl = detailList.get(k);
                                if(k==0){
                                    goodIds.append(bl.getBorrowGoodsId());
                                }else{
                                    goodIds.append(",").append(bl.getBorrowGoodsId());
                                }
                            }
                            if(goodIds.length()>0){
                                pointGoodsService.removeBrands(goodIds.toString());
                            }
                        }
                    }
                    //修改当前节点的状态  run_state 运行状态：1运行中；0已过
                    workflowData.setRunState(0);
                    workflowData.setDataValue(new Gson().toJson(_map));
                    workflowData.setModifor(workflowData.getActualAuditors());
                    workflowData.setModifytime(new Date());
                    workflowData.setAuditState(Integer.valueOf(auditState));
                    workflowData.setAuditResults(auditResults);
                    aWorkflowDataDao.update(workflowData);
                }
            }else if(Constant.AUDIT_STATE.equals(auditState)){////审批不通过
                nextNode = workflowData.getNotpassNodeId();
                AWorkflowData newNode = initNode(workflowData,nextNode,new Gson().toJson(_map),accessToken,1,Integer.valueOf(auditState));
                aWorkflowDataDao.insert(newNode);//生成下一节点数据
                //修改当前节点的状态  run_state 运行状态：1运行中；0已过
                workflowData.setRunState(0);
                workflowData.setDataValue(new Gson().toJson(_map));
                workflowData.setModifor(accessToken);
                workflowData.setModifytime(new Date());
                workflowData.setAuditState(Integer.valueOf(auditState));
                workflowData.setAuditResults(auditResults);
                workflowData.setActualAuditors(accessToken);
                aWorkflowDataDao.update(workflowData);
            }
        }
        }catch (IllegalArgumentException e){
            log.error("系统内部异常", e);
        }
    }


    /***
     * 判断是否多个人审批，才能继续走下一流程
     * @param currentWorkflow
     * @param accessToken
     * @return
     */
    public boolean agree(AWorkflowData currentWorkflow,String accessToken){
        String actualAuditors = null != currentWorkflow.getActualAuditors()?currentWorkflow.getActualAuditors():"";
        Integer auditMode = currentWorkflow.getAuditMode();//审批方式：1一人同意即放下流转；全部同意才往下流转
        boolean nextFlag = false;//判断流程是否能继续走下去
        if(1==auditMode) {
            nextFlag =true;
            currentWorkflow.setActualAuditors(accessToken);
        }else {
            if(actualAuditors.indexOf(accessToken)<0) {//不存在
                if(actualAuditors.length()>0) {
                    actualAuditors += ",";
                }
                actualAuditors +=  accessToken;
            }
            currentWorkflow.setActualAuditors(actualAuditors);//更新实际审批人
            String auditors = currentWorkflow.getAuditors();
            String[] array = auditors.split(",");
            nextFlag = true;
            for(int i=0;i<array.length;i++) {
                if(actualAuditors.indexOf(array[1])<0) {//存在还有需要审核人
                    nextFlag = false;
                    break;
                }
            }
        }
        return  nextFlag;
    }

    /**
     * 生成节点数据
     * @return
     */
    public AWorkflowData initNode(AWorkflowData startWorkflow,String nextNode,
                                  String dataValue,String accessToken,int runState,int auditState){
        AWorkflowNode runNode = nodeService.getById(nextNode);
        //获取流程表单
        AWorkflowData runWorkflow = new AWorkflowData();
        runWorkflow.setId(UUID.randomUUID().toString().replace("-",""));
        runWorkflow.setWbNo(startWorkflow.getWbNo());//流程工单号
        runWorkflow.setSort(startWorkflow.getSort()+1);
        runWorkflow = copyNodeToWorkflow(runNode,runWorkflow,startWorkflow);
        if(runNode.getAssignType()==Constant.ASSIGN_TYPE_6) {//上节点指派
            Map _temp =   new Gson().fromJson(dataValue,Map.class);
            if(null!=_temp && !_temp.isEmpty()){
                runWorkflow.setAuditors(String.valueOf(_temp.get("assignIds")));
            }
        }
        runWorkflow.setProcessId(startWorkflow.getId());
        runWorkflow.setProcessCode(startWorkflow.getProcessCode());
        runWorkflow.setProcessName(startWorkflow.getProcessName());
        runWorkflow.setFormId(startWorkflow.getFormId());
        runWorkflow.setFormUrl(startWorkflow.getFormUrl());
        runWorkflow.setSourceId(startWorkflow.getSourceId());
        runWorkflow.setRunState(runState);
        runWorkflow.setAuditState(0);
        if(runNode.getNtype()==3) {//结束
            runWorkflow.setRunState(0);//结束流程
            runWorkflow.setAuditState(auditState);
        }
        Date current = Calendar.getInstance().getTime();
        runWorkflow.setDataValue(dataValue);
        runWorkflow.setCreator(startWorkflow.getCreator());
        runWorkflow.setCreatetime(startWorkflow.getCreatetime());
        runWorkflow.setModifor(accessToken);
        runWorkflow.setModifytime(current);
        runWorkflow.setJobContent(startWorkflow.getJobContent());
        runWorkflow.setDepartId(startWorkflow.getDepartId());
        runWorkflow.setTrainNo(startWorkflow.getTrainNo());
        return  runWorkflow;
    }


    /***
     * 获取需要保持的节点信息
     * @param processCode
     * @param sourceId
     * @param accessToken
     * @param dataValue
     * @return
     */
    public List<AWorkflowData> needSaveNode(String processCode,String sourceId,String accessToken,String dataValue){
        //获取流程
        AWorkflowProcess process = aWorkflowProcessDao.getByProcessCode(processCode);
        //获取流程表单
        AWorkflowForm form = aWorkflowFormDao.getById(process.getFormId());
        //获取第一个节点
        AWorkflowNode node = aWorkflowNodeDao.getByProcessIdAndNtype(process.getId(),"1");

        Date current = Calendar.getInstance().getTime();
        //创建开始节点
        AWorkflowData startWorkflow = new AWorkflowData();
        startWorkflow.setId(UUID.randomUUID().toString());
        startWorkflow.setSort(1);
        //流程工单号
        startWorkflow.setWbNo(processCode+DateUtils.formatDate(new Date(),"yyMMddHHmmss"));
        startWorkflow.setProcessId(process.getId());
        startWorkflow.setProcessCode(process.getProcessCode());
        startWorkflow.setProcessName(process.getProcessName());
        startWorkflow.setFormId(process.getFormId());
        if(null != form){
            startWorkflow.setFormUrl(form.getFormUrl());
        }
        startWorkflow.setSourceId(sourceId);
        startWorkflow.setRunState(0);//已过
        startWorkflow.setAuditState(1);//审批通过
        startWorkflow.setActualAuditors(accessToken);//开始节点的审批人为提交流程者
        startWorkflow.setDataValue(dataValue);
        startWorkflow.setCreator(accessToken);
        startWorkflow.setCreatetime(current);
        startWorkflow.setModifor(accessToken);
        startWorkflow.setModifytime(current);
        startWorkflow = copyNodeToWorkflow(node,startWorkflow,startWorkflow);
        startWorkflow.setAuditors(accessToken);
        Map trainNoMap = getWorkflowTrainNo(process.getProcessCode(), sourceId);
        startWorkflow.setTrainNo(null != trainNoMap.get("trainNo")?trainNoMap.get("trainNo")+"":"");
        startWorkflow.setJobContent(null != trainNoMap.get("jobContent")?trainNoMap.get("jobContent")+"":"");
        startWorkflow.setDepartId(null != trainNoMap.get("departId")?trainNoMap.get("departId")+"":"");

        //创建下一节点
        AWorkflowNode runNode = aWorkflowNodeDao.getById(startWorkflow.getPassNodeId());
        AWorkflowData runWorkflow = new AWorkflowData();
        runWorkflow.setId(UUID.randomUUID().toString());
        runWorkflow.setWbNo(startWorkflow.getWbNo());//流程工单号
        runWorkflow.setSort(startWorkflow.getSort()+1);
        runWorkflow = copyNodeToWorkflow(runNode,runWorkflow,startWorkflow);
        if(runNode.getAssignType()==Constant.ASSIGN_TYPE_6) {//上节点指派
            Map _temp =   new Gson().fromJson(dataValue,Map.class);
            if(null!=_temp && !_temp.isEmpty()){
                runWorkflow.setAuditors(String.valueOf(_temp.get("assignIds")));
            }
        }
        runWorkflow.setProcessId(process.getId());
        runWorkflow.setProcessCode(process.getProcessCode());
        runWorkflow.setProcessName(process.getProcessName());
        runWorkflow.setFormId(process.getFormId());
        if(null != form){
            runWorkflow.setFormUrl(form.getFormUrl());
        }
        runWorkflow.setSourceId(sourceId);
        runWorkflow.setRunState(1);//运行中
        runWorkflow.setAuditState(0);//状态改成审批中
        runWorkflow.setDataValue(dataValue);
        runWorkflow.setCreator(startWorkflow.getCreator());
        runWorkflow.setCreatetime(startWorkflow.getCreatetime());
        runWorkflow.setModifor(accessToken);
        runWorkflow.setModifytime(current);
        if(null == runWorkflow.getAuditors() || runWorkflow.getAuditors().length()<1) {
            throw new RuntimeException("找不到审批者");
        }
        List<AWorkflowData> list = new ArrayList();
        list.add(startWorkflow);
        list.add(runWorkflow);

        return list;
    }

    /***
     * 复制节点
     * @param node
     * @param workflow
     * @param startworkflow
     * @return
     */
    private AWorkflowData copyNodeToWorkflow(AWorkflowNode node,AWorkflowData workflow,AWorkflowData startworkflow) {
        workflow.setNodeId(node.getId());
        workflow.setNodeCode(node.getNodeCode());
        workflow.setNodeName(node.getNodeName());
        workflow.setNodeType(node.getNtype());
        workflow.setAssginIds(node.getAssginIds());
        Integer assignType = node.getAssignType();
        workflow.setAssignType(assignType);
        String auditors = "";
        if(Constant.ASSIGN_TYPE_1 ==assignType) {//按个人
            auditors = node.getAssginIds();
        }else if(Constant.ASSIGN_TYPE_2 ==assignType) {//按角色
            auditors = userOrgService.getUserByRole(node.getAssginIds());
        }else if(Constant.ASSIGN_TYPE_3 == assignType) {//按组织机构
            auditors = userOrgService.getUserByOrg(node.getAssginIds());
        }else if(Constant.ASSIGN_TYPE_4 ==assignType) {//申请人
            auditors = startworkflow.getCreator();
        }else if( Constant.ASSIGN_TYPE_5 ==assignType) { //申请人工班长
            //取创建者所在班组
            auditors = userOrgService.getUserByCurUser(startworkflow.getCreator());
        }
        workflow.setAuditors(auditors);
        workflow.setAuditMode(node.getAuditMode());
        workflow.setDataAuth(node.getDataAuth());
        workflow.setAutoRun(node.getAutoRun());
        workflow.setAutoRunAfterHour(node.getAutoRunAfterHour());
        workflow.setPassNodeId(node.getPassNodeId());
        workflow.setPassExpress(node.getPassExpress());
        workflow.setNotpassNodeId(node.getNotpassNodeId());
        workflow.setNotpassExpress(node.getNotpassExpress());
        workflow.setDepartId(startworkflow.getDepartId());
        workflow.setJobContent(startworkflow.getJobContent());
        workflow.setTrainNo(startworkflow.getTrainNo());
        return workflow;
    }

    /***
     * 批量插入
     * @param list
     */
    public void batchInsert(List<AWorkflowData> list){
        aWorkflowDataDao.batchInsert(list);
    }

    public void createWorkflow(String processCode,String key,String auditResults,String data,String userId){
        Map params = new HashMap();
        params.put("processCode", processCode);
        params.put("state", 1);
        List<AWorkflowProcess> processList = workflowProcessDao.getByProcessCodeAndState(params);
        if(null == processList || processList.size()<=0) {
            throw new RuntimeException("流程不存在或未启动.");
        }

        AWorkflowProcess process = processList.get(0);
        AWorkflowForm form = workflowFormDao.getById(process.getFormId());
        //取开始节点
        params = new HashMap();
        params.put("processId", process.getId());
        params.put("ntype", 1);
        List<AWorkflowNode> nodeList = workflowNodeDao.getNodeByProcessAndNtype(params);
        if(null == nodeList || nodeList.size()<=0) {
            throw new RuntimeException("找不到开始节点.");
        }

        AWorkflowNode startNode = nodeList.get(0);

        Date current = Calendar.getInstance().getTime();
        //创建开始节点
        AWorkflowData startWorkflow = new AWorkflowData();

        startWorkflow.setId(UUID());
        startWorkflow.setSort(1);
        //流程工单号
        startWorkflow.setWbNo(processCode+DateUtils.date2Str(new Date(),DateUtils.yyyymmddhhmmss));
        startWorkflow.setProcessId(process.getId());
        startWorkflow.setProcessCode(process.getProcessCode());
        startWorkflow.setProcessName(process.getProcessName());
        startWorkflow.setFormId(process.getFormId());
        startWorkflow.setFormUrl(form.getFormUrl());
        startWorkflow.setSourceId(key);
        startWorkflow.setRunState(0);//已过
        startWorkflow.setAuditState(1);//审批通过
        startWorkflow.setActualAuditors(userId);//开始节点的审批人为提交流程者
        startWorkflow.setDataValue(data);
        startWorkflow.setCreator(userId);
        startWorkflow.setCreatetime(current);
        startWorkflow.setModifor(userId);
        startWorkflow.setModifytime(current);
        startWorkflow.setAuditResults(auditResults);

        startWorkflow = copyNodeToWorkflow(startNode,startWorkflow,startWorkflow);
        startWorkflow.setAuditors(userId);
        Map trainNoMap = getWorkflowTrainNo(process.getProcessCode(), key);
        startWorkflow.setTrainNo(null != trainNoMap.get("trainNo")?trainNoMap.get("trainNo")+"":"");
        startWorkflow.setJobContent(null != trainNoMap.get("jobContent")?trainNoMap.get("jobContent")+"":"");
        startWorkflow.setDepartId(null != trainNoMap.get("departId")?trainNoMap.get("departId")+"":"");


        //创建下一节点
        AWorkflowNode runNode = workflowNodeDao.getById(startWorkflow.getPassNodeId());
        AWorkflowData runWorkflow = new AWorkflowData();
        runWorkflow.setId(UUID());
        runWorkflow.setWbNo(startWorkflow.getWbNo());//流程工单号
        runWorkflow.setSort(startWorkflow.getSort()+1);
        runWorkflow = copyNodeToWorkflow(runNode,runWorkflow,startWorkflow);
        if(runNode.getAssignType()== 6 ) {//自选择审批人
            String orginalValue = startWorkflow.getDataValue();
            JSONObject orginalData = new JSONObject();
            if(null != orginalValue && orginalValue.length()>0) {
                orginalData = JSONObject.parseObject(orginalValue);
            }
            runWorkflow.setAuditors(orginalData.get("assignIds")+"");
        }
        runWorkflow.setProcessId(process.getId());
        runWorkflow.setProcessCode(process.getProcessCode());
        runWorkflow.setProcessName(process.getProcessName());
        runWorkflow.setFormId(process.getFormId());
        runWorkflow.setFormUrl(form.getFormUrl());
        runWorkflow.setSourceId(key);
        if(runNode.getNtype()==3){//结束
            runWorkflow.setRunState(0);//结束
            runWorkflow.setAuditState(1);
            runWorkflow.setAuditors(userId);
            runWorkflow.setActualAuditors(userId);
            runWorkflow.setModifytime(current);
        }else {
            runWorkflow.setRunState(1);//运行中
        }

        runWorkflow.setAuditState(0);//状态改成审批中
        runWorkflow.setDataValue(data);
        runWorkflow.setCreator(userId);
        runWorkflow.setCreatetime(current);
        runWorkflow.setModifytime(current);
        runWorkflow.setModifor(userId);


        if(null == runWorkflow.getAuditors() || runWorkflow.getAuditors().length()<1) {
            throw new RuntimeException("找不到审批者");
        }

        if(runWorkflow.getNodeType()==3 ) {//结束时往工单表添加记录
            dealBusiness(runWorkflow.getSourceId(),runWorkflow.getProcessCode(),1,userId,null);
        }

        List<AWorkflowData> list = new ArrayList();
        list.add(startWorkflow);
        list.add(runWorkflow);
        aWorkflowDataDao.batchInsert(list);

        //消息提醒
        pushMsgToAppAndWeb(runWorkflow);
    }

    public Map<String,String> getWorkflowTrainNo(String processCode, String sourceId) {
        Map<String,String> resultMap = new HashMap();
        String jobContent = "";
        String trainNo="";
        String departId = "";

        if("1010".equals(processCode) || "1011".equals(processCode)) {//登车
            List<Map> list = pointService.getBoardingTrainContent(sourceId);

            String userId = "";
            if(null != list && list.size()>0) {
                Map map = list.get(0);
                trainNo = null != map.get("carNum")?map.get("carNum")+"":"";
                jobContent = null != map.get("jobContent")?map.get("jobContent")+"":"";
                userId = null != map.get("createBy")?map.get("createBy")+"":"";
                if(null != map.get("transferId") && (map.get("transferId")+"").length()>0) {
                    userId = map.get("transferId")+"";
                }
            }
            List<TSDepart> departList = null != userId && userId.length()>0?getDepartListByUserId(userId):null;
            departId = departList!=null && departList.size()>0?departList.get(0).getId():"";

        }else if("1020".equals(processCode) || "1021".equals(processCode)) {//stinger
            APointPlease pointPleaseEntity = pointService.getById(sourceId);
            String userId = "";
            if(null != pointPleaseEntity) {
                jobContent = pointPleaseEntity.getJobContent();
                userId = pointPleaseEntity.getCreateBy();
                if(null != pointPleaseEntity.getTransferId() && pointPleaseEntity.getTransferId().length()>0) {
                    userId = pointPleaseEntity.getTransferId();
                }
            }

            List<TSDepart> departList = null != userId && userId.length()>0?getDepartListByUserId(userId):null;
            departId = departList!=null && departList.size()>0?departList.get(0).getId():"";

        }else if("1030".equals(processCode) || "1031".equals(processCode)) {//人工推车

            List<Map> list = pointService.getCartTrainContent(sourceId);
            String userId = "";
            if(null != list && list.size()>0) {
                Map map = list.get(0);
                trainNo = null != map.get("trainNum")?map.get("trainNum")+"":"";
                jobContent = null != map.get("jobContent")?map.get("jobContent")+"":"";
                userId = null != map.get("createBy")?map.get("createBy")+"":"";
                if(null != map.get("transferId") && (map.get("transferId")+"").length()>0) {
                    userId = map.get("transferId")+"";
                }
            }
            List<TSDepart> departList = null != userId && userId.length()>0?getDepartListByUserId(userId):null;
            departId = departList!=null && departList.size()>0?departList.get(0).getId():"";
        }else if("1040".equals(processCode) || "1041".equals(processCode)) {//断送电
            APointPlease pointPleaseEntity = pointService.getById(sourceId);
            jobContent= pointPleaseEntity.getJobContent();
            String userId = "";
            userId = pointPleaseEntity.getCreateBy();
            List<TSDepart> departList = null != userId && userId.length()>0?getDepartListByUserId(userId):null;
            departId = departList!=null && departList.size()>0?departList.get(0).getId():"";
        }else if("2010".equals(processCode) ) {//借用
            ABorrowList aBorrowListEntity = aBorrowListDao.getById(sourceId);
            jobContent = aBorrowListEntity.getJobContent();
            departId = aBorrowListEntity.getCreatorOrg();
        }else if("2011".equals(processCode)) {//归还
            List<ABorrowListDetail> list = aBorrowListDetailDao.getByReturnNum(sourceId);
            String userId = "";
            if(null != list && list.size()>0) {
                StringBuilder jobBuil = new StringBuilder(jobContent);
                for(ABorrowListDetail record:list) {
                    jobBuil.append(record.getBorrowGoodsName()).append("[").append(record.getCode()).append("],");
//                    jobContent += record.getBorrowGoodsName()+"["+record.getCode()+"]"+",";
                    userId = record.getRestituer();
                }
                jobContent = jobBuil.toString();
            }
            List<TSDepart> departList = null != userId && userId.length()>0?getDepartListByUserId(userId):null;
            departId = departList!=null && departList.size()>0?departList.get(0).getId():"";
        }else if("3011".equals(processCode) || "3012".equals(processCode)) {//任务审批
            List<ATaskList> taskList = aTaskListDao.getByIds(sourceId);

            if(null != taskList && taskList.size()>0) {
                StringBuilder trainNoBulider = new StringBuilder(trainNo);
                StringBuilder jobContentBulider = new StringBuilder(jobContent);
                for(ATaskList record:taskList) {
                    if(null != record.getTrainNo() && trainNoBulider.indexOf(record.getTrainNo())<0) {
                        if(trainNoBulider.length()>0) {
                            trainNoBulider.append(",");
                        }
                        trainNoBulider.append(record.getTrainNo());
                    }
                    if(null != record.getTaskName() && jobContentBulider.indexOf(record.getTaskName())<0) {
                        if(jobContentBulider.length()>0) {
                            jobContentBulider.append(",");
                        }
                        jobContentBulider.append(record.getTaskName()+"["+record.getTrainNo()+"]");
                    }
                }
                trainNo = trainNoBulider.toString();
                jobContent = jobContentBulider.toString();
            }else {
                List<ATaskListHis> taskListHisList = aTaskListHisDao.getByIds(sourceId);
                if( taskListHisList.size()<=0) taskListHisList = new ArrayList();
                StringBuilder trainNoBulider = new StringBuilder(trainNo);
                StringBuilder jobContentBulider = new StringBuilder(jobContent);
                for(ATaskList record:taskList) {
                    if(null != record.getTrainNo() && trainNoBulider.indexOf(record.getTrainNo())<0) {
                        if(trainNoBulider.length()>0) {
                            trainNoBulider.append(",");
                        }
                        trainNoBulider.append(record.getTrainNo());
                    }
                    if(null != record.getTaskName() && jobContentBulider.indexOf(record.getTaskName())<0) {
                        if(jobContentBulider.length()>0) {
                            jobContentBulider.append(",");
                        }
                        jobContentBulider.append(record.getTaskName()+"["+record.getTrainNo()+"]");
                    }
                }
                trainNo = trainNoBulider.toString();
                jobContent = jobContentBulider.toString();
            }
        }else if("4011".equals(processCode) || "4012".equals(processCode)
                ||"4013".equals(processCode) || "4014".equals(processCode)
                || "4015".equals(processCode)|| "4016".equals(processCode)
                || "4017".equals(processCode)|| "4018".equals(processCode)) {//外单位外分部
            APointPleaseForgein pleaseForgein = aPointPleaseForgeinDao.getById(sourceId);
            trainNo = pleaseForgein!=null ?pleaseForgein.getTrainNo():"";
            jobContent = null != pleaseForgein.getJobContent()?pleaseForgein.getJobContent():"";
        }else if("5010".equals(processCode) ) {//外单位外分部
            AFaultInfo aFaultInfo = aFaultInfoDao.getById(sourceId);
            trainNo = aFaultInfo!=null ?aFaultInfo.getTrainNo():"";
            jobContent = null != aFaultInfo?getSubString(aFaultInfo.getFaultContents(),100):"";
            departId = null != aFaultInfo?aFaultInfo.getReporterDeptId():"";
        }else if("5011".equals(processCode)||"5012".equals(processCode)||"5013".equals(processCode) ) {//故障处理
            AFaultList aFaultList = aFaultListDao.getById(sourceId);
            AFaultInfo aFaultInfo = aFaultInfoDao.getById(aFaultList.getJobId());
            trainNo = aFaultInfo!=null ?aFaultInfo.getTrainNo():"";
            jobContent = null != aFaultInfo?getSubString(aFaultInfo.getFaultContents(),100):"";
            departId = null != aFaultList?aFaultList.getTeamDepartId():"";
        }


        resultMap.put("trainNo",trainNo);
        resultMap.put("jobContent",jobContent);
        resultMap.put("departId",departId);
        return resultMap;
    }

    private  String getSubString(String str,int num) {
        if(str.length()>num) {
            str = str.substring(0,num);
        }
        return str;
    }
    private List<TSDepart> getDepartListByUserId(String userId) {
        List<TSDepart> departList = tsDepartDao.getDepartListByUserId(userId);
        return departList;
    }

    /***
     *
     * @param workflowData 流程 记录
     */
    public void pushMsgToAppAndWeb(AWorkflowData workflowData) {

        String msg = "";
        if("1010".equals(workflowData.getProcessCode()) || "1011".equals(workflowData.getProcessCode())) {//登车请点、销点
            //请点
            APointPlease aPointPlease = aPointPleaseDao.getById(workflowData.getSourceId());
            //详细内容
            APointPleaseBoarding boarding = aPointPleaseBoardingDao.getByPointId(workflowData.getSourceId());
            msg += aPointPlease.getCreateName()+"发起";
            msg +=","+workflowData.getProcessName()+"流程("+workflowData.getWbNo()+")";
            msg += ",作业内容("+aPointPlease.getJobContent()+")";
            msg += ",车辆号("+boarding.getCarNum()+")";

        }else if("1020".equals(workflowData.getProcessCode()) || "1021".equals(workflowData.getProcessCode())) {//Stinger请点\Stinger销点

            APointPlease aPointPlease = aPointPleaseDao.getById(workflowData.getSourceId());

//            APointPleaseStinger stinger = stingerService.getByPointId(workflowData.getSourceId());
            msg += aPointPlease.getCreateName()+"发起";
            msg +=","+workflowData.getProcessName()+"流程("+workflowData.getWbNo()+")";
            msg += ",作业内容("+aPointPlease.getJobContent()+")";

        }else if("1030".equals(workflowData.getProcessCode())||"1031".equals(workflowData.getProcessCode())) {//人工推车请点\人工推车销点

            APointPlease aPointPlease = aPointPleaseDao.getById(workflowData.getSourceId());
            APointPleaseCart cart = aPointPleaseCartDao.getByPointId(workflowData.getSourceId());
            msg += aPointPlease.getCreateName()+"发起";
            msg +=","+workflowData.getProcessName()+"流程("+workflowData.getWbNo()+")";
            msg += ",作业内容("+aPointPlease.getJobContent()+")";
            msg += ",车辆号("+cart.getTrainNum()+")";

        }else if("1040".equals(workflowData.getProcessCode())||"1041".equals(workflowData.getProcessCode())) {//接触网断送电

            APointPlease aPointPlease = aPointPleaseDao.getById(workflowData.getSourceId());
            PointPleasePower power = pointPleasePowerDao.getByPointId(workflowData.getSourceId());
            msg += aPointPlease.getCreateName()+"发起";
            msg +=","+workflowData.getProcessName()+"流程("+workflowData.getWbNo()+")";
            msg += ",作业内容("+aPointPlease.getJobContent()+")";
            msg += ",股道("+power.getStock()+")";

        }else if("2010".equals(workflowData.getProcessCode()) || "2011".equals(workflowData.getProcessCode())) {//借用审批,归还审批
            TSBaseUser tsBaseUser = tSBaseUserDao.getById(workflowData.getCreator());
            msg += tsBaseUser.getRealname()+"发起";
            msg +=","+workflowData.getProcessName()+"流程("+workflowData.getWbNo()+")";
            List<ABorrowListDetail> detailList = aBorrowListDetailDao.getByBorrowId(new String[]{workflowData.getSourceId()});
            if(detailList!=null && detailList.size()>0) {
                StringBuilder detailStr = new StringBuilder("");
                for(ABorrowListDetail detail:detailList) {
                    if(detailStr.length()>0) {
                        detailStr.append(",");
                    }
                    detailStr.append(detail.getBorrowGoodsName()).append("[").append(detail.getCode()).append("]");
                }
                msg += ",物品("+detailStr.toString()+")";
            }
        }else if("3011".equals(workflowData.getProcessCode()) || "3012".equals(workflowData.getProcessCode())) {//互检审批,三检审批
            TSBaseUser tsBaseUser = tSBaseUserDao.getById(workflowData.getCreator());
            msg += tsBaseUser.getRealname()+"发起";
            msg +=","+workflowData.getProcessName()+"流程("+workflowData.getWbNo()+")";
            List<ATaskList> taskLists = aTaskListDao.getTaskByIds(workflowData.getSourceId().split(","));
            if(null != taskLists && taskLists.size()>0) {
                StringBuilder tasks = new StringBuilder("");
                for(ATaskList record:taskLists) {
                    if(tasks.length()>0) {
                        tasks.append(",");
                    }
                    tasks.append(record.getTaskName()).append("[").append(record.getTrainNo()).append("]");
                }
                msg += ",作业内容("+tasks.toString()+")";
            }

        }else if("4011".equals(workflowData.getProcessCode()) || "4012".equals(workflowData.getProcessCode())
                || "4013".equals(workflowData.getProcessCode()) || "4014".equals(workflowData.getProcessCode())
                || "4015".equals(workflowData.getProcessCode()) || "4016".equals(workflowData.getProcessCode())
                || "4017".equals(workflowData.getProcessCode()) || "4018".equals(workflowData.getProcessCode())) {//外单位人员请点,外单位人员销点,外分部请点,外分部销点,外单位保洁人员请点,外单位保洁人员销点,委外人员请点,委外人员销点

            msg += workflowData.getCreator()+"发起";
            msg +=","+workflowData.getProcessName()+"流程("+workflowData.getWbNo()+")";
            APointPleaseForgein forgein = aPointPleaseForgeinDao.getById(workflowData.getSourceId());
            msg += ",作业内容("+forgein.getJobContent()+")";
            msg += ",车辆号("+forgein.getTrainNo()+")";
        }else if("5010".equals(workflowData.getProcessCode())) {
            msg += workflowData.getCreator()+"发起故障提报";
            AFaultInfo faultInfo = aFaultInfoDao.getById(workflowData.getSourceId());
            msg += "["+faultInfo.getFaultContents()+"]";
        }
        if(workflowData.getRunState()==1) {
            msg += ",请及时审批.";
        }else {
            if(workflowData.getAuditState()==1) {
                msg += ",已审批通过.";
            }else {
                msg += ",审批不通过.";
            }
        }
        String auditors = workflowData.getAuditors();
        //APP端提醒
        if(auditors.length()>0) {
            String[] array = auditors.split(",");
            for(int i=0;i<array.length;i++) {
                jpushService.sendMessage(ConstantValue.PUSH_MSG_DAIBANG,array[i],msg);
            }
        }
        //web端提醒
        if(workflowData.getRunState()==1) {
            String[] array = auditors.split(",");
            try {
                for(int i=0;i<array.length;i++) {
                    HttpClientUtilsToServer.getInstance().doGet(serverPath,array[i],ConstantValue.PUSH_MSG_DAIBANG, msg);
                }
            }catch(IllegalArgumentException e) {
                log.error("系统内部异常", e);
            }
        }
    }

    public String UUID(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }

    public void dealBusiness(String sourceId,String processCode,int auditState,String userId,String dataValue){
        if(1 == auditState){
            if("3011".equals(processCode) ||"3012".equals(processCode)) {
                //任务审批通过，状态变更为任务完成，
                String msgtypes = ConstantValue.PUSH_MSG_GONGDAN;
                List<ATaskList> taskLists = taskListService.getTaskByIds(sourceId.split(","));
                StringBuilder auditors = new StringBuilder("");
                List<ATaskList> updateList = new ArrayList();//更新parent taskId的集合，用parentTaskId来串起来部分完成
                List<ATaskList> segmentFinishTaskList = new ArrayList();
                for(ATaskList record:taskLists) {
                    String[] opts = record.getScheOperators().split(",");
                    for(int i=0;i<opts.length;i++) {
                        if(auditors.indexOf(opts[i])<0) {
                            if(auditors.length()>0) {
                                auditors.append(",");
                            }
                            auditors.append(opts[i]);
                        }
                    }

                    //更新状态
                    Integer workState = 3;
                    if(null != record.getSurplusValue() && record.getSurplusValue().compareTo(Float.valueOf("0"))>0) {
                        workState=6;//部分完成
                        if(null == record.getParentTaskId()) {
                            record.setParentTaskId(record.getId());
                            record.setModifor(userId);
                            updateList.add(record);
                        }

                        segmentFinishTaskList.add(record);
                    }
                    taskListService.finishJob(sourceId,workState,Calendar.getInstance().getTime(),null,null,null,userId,null);
                }

                //部分完成，更新parent task id
                if(null != updateList && updateList.size()>0) {
                    aTaskListDao.updateParentTaskId(updateList);
                }
                if(null != segmentFinishTaskList && segmentFinishTaskList.size()>0) {
                    taskListService.addSchedulerFromTaskLst(userId,segmentFinishTaskList);//部分完成要插入到日计划
                }
                String[] array = auditors.toString().split(",");
                for(int i=0;i<array.length;i++) {
                    jpushService.sendMessage(ConstantValue.PUSH_MSG_GONGDAN,array[i],"任务完成审批通过.");
                }
            }else if("4011".equals(processCode) || "4013".equals(processCode) || "4015".equals(processCode) || "4017".equals(processCode)) {//外单位与外分部请点通过
                APointPleaseForgein forgein = aPointPleaseForgeinDao.getById(sourceId);
                forgein.setState(3);
                aPointPleaseForgeinDao.update(forgein);

                //维护车辆状态
                List<PointList>  parentPointList = pointListService.getAllParentPointList();
                if(null == parentPointList || parentPointList.size()<=0) parentPointList=new ArrayList();
                Map<String,String> trainNoMap = new HashMap();
                for(PointList record:parentPointList) {
                    trainNoMap.put(record.getTrainNo(),"1");
                }
                String trainNo = forgein.getTrainNo();
                String[] trainNoArray = trainNo.split(",");
                StringBuilder firstTrainNo = new StringBuilder("");
                for(int i=0;i<trainNoArray.length;i++) {
                    if(trainNoMap.get(trainNoArray[i])!=null) {//车号存在
                        pointListService.addToPointList(userId,trainNoArray[i], forgein.getId(), forgein.getJobContent(), forgein.getMajorName(),forgein.getMajorName(), forgein.getJobType(), Calendar.getInstance().getTime(), forgein.getPointHours());
//                        pointListService.updateParentMetroStatus(trainNoArray[i],userId,sourceId);

                        if(firstTrainNo.toString().length()<1) {
                            firstTrainNo = new StringBuilder(trainNoArray[i]);
                        }
                    }
                }



                if("4013".equals(processCode)) {//外分部
                    String brands = forgein.getBrands();
                    if(null != brands && brands.length()>0) {
                        JSONArray array = JSONArray.parseArray(brands);
                        StringBuilder goodIds = new StringBuilder("");
                        List<PointListGoods> goodsList = new ArrayList();
                        for(int i=0;i<array.size();i++) {
                            JSONObject json = array.getJSONObject(i);
                            if(goodIds.length()>0) {
                                goodIds.append(",");
                            }
                            goodIds.append(null != json?json.get("id")+"":"");
                            PointListGoods lstGoods = new PointListGoods();
                            lstGoods.setGoodsId(json.get("id")+"");
                            lstGoods.setGoodsCode(json.get("code")+"");
                            lstGoods.setGoodsName(json.get("name")+"");
                            lstGoods.setTrainNo(firstTrainNo.toString());
                            lstGoods.setCreator(forgein.getMajorName());
                            goodsList.add(lstGoods);
                        }
                        if(goodIds.length()>0) {//1可借用 3已使用 2已预订
                            Map params = new HashMap();
                            params.put("goodIds",goodIds.toString());
                            params.put("state",3);
                            goodsService.updateStateByGoodIds(params);
                        }
                        //批量插入挂牌
                        //车辆状态中牌自动挂在第一列车上
                        if(null != goodsList && goodsList.size()>0) {
                            pointListService.batchInsertLstGoods(goodsList);
                        }
                    }
                }
            }else if("4012".equals(processCode) || "4014".equals(processCode) || "4016".equals(processCode) || "4018".equals(processCode)) {//外单位与外分部销点通过
                APointPleaseForgein forgein = aPointPleaseForgeinDao.getById(sourceId);
                forgein.setState(6);
                aPointPleaseForgeinDao.update(forgein);
                String taskId = forgein.getTaskId()!=null ?forgein.getTaskId():"";
                String taskListIds[] = taskId.split(",");

                List<ATaskListForgein> updateList = new ArrayList();//更新parent taskId的集合，用parentTaskId来串起来部分完成
                List<ATaskListForgein> segmentFinishTaskList = new ArrayList();
                for(String id:taskListIds) {
                    ATaskListForgein taskListForgein = aTaskListForgeinDao.getById(id);

                    if(taskListForgein!=null){
                        Integer workState = 3;
                        if(null != taskListForgein && null != taskListForgein.getSurplusValue() && taskListForgein.getSurplusValue().compareTo(Float.valueOf("0"))>0) {
                            workState=6;//部分完成
                            if(null == taskListForgein.getParentTaskId()) {
                                taskListForgein.setParentTaskId(taskListForgein.getId());
                                taskListForgein.setModifor(userId);
                                updateList.add(taskListForgein);
                            }
                            ATaskListForgein newATaskListForgein =  this.copyTaskListForgein(userId,taskListForgein);
                            segmentFinishTaskList.add(newATaskListForgein);
                        }
                        //更新任务状态为作业中3
                        if(forgein.getTaskId()!=null && forgein.getTaskId().length()>0) {
                            Map params = new HashMap();
                            params.put("ppointId",forgein.getId());
                            params.put("workState",workState);
                            params.put("ids",id);
                            aTaskListForgeinDao.updateWorkStateAndPointByIds(params);
                        }
                    }else{
                        AFaultList aFaultList = aFaultListDao.getById(id);

                        Map faultListParams = new HashMap();
                        Map faultInfoParams = new HashMap();
                        faultListParams.put("modifor",userId);
                        faultInfoParams.put("modifor",userId);
                        if(aFaultList.getSurplusValue().compareTo(Float.valueOf("0.0"))>0) {//有剩余,部分完成6
                            faultListParams.put("workState",6);
                            faultInfoParams.put("faultState",3);//部分已处理
                        }else {//无剩余，完成3
                            faultListParams.put("workState",3);
                            faultInfoParams.put("faultState",5);//已处理5
                        }

                        faultInfoParams.put("id",aFaultList.getJobId());
                        faultInfoParams.put("finishJob",new Date());
                        if(null != dataValue && dataValue.length()>0) {
                            JSONObject dataObject = JSONObject.parseObject(dataValue);
                            if(dataObject.get("coeDiff")!=null){
                                faultInfoParams.put("coeDiff",dataObject.get("coeDiff"));
                            }
                        }
                        aFaultInfoDao.updateFaultStateAndCoeDiff(faultInfoParams);
                        faultListParams.put("id",aFaultList.getId());
                        aFaultListDao.updateWorkState(faultListParams);
                    }


                }

                //部分完成，更新parent task id
                if(null != updateList && updateList.size()>0) {
                    aTaskListForgeinDao.updateParentTaskId(updateList);
                }
                if(null != segmentFinishTaskList && segmentFinishTaskList.size()>0) {
                    aTaskListForgeinDao.batchInsert(segmentFinishTaskList);
                }





                //维护车辆状态
                updatePointListMetroStatus(userId,forgein.getId());
                //根据请点id删除
                pointListService.deleteByPPointId(forgein.getId());

                if("4014".equals(processCode)) {
                    String brands = forgein.getBrands();
                    if(null != brands && brands.length()>0) {
                        JSONArray array = JSONArray.parseArray(brands);
                        StringBuilder goodIds = new StringBuilder("");
                        for(int i=0;i<array.size();i++) {
                            JSONObject json = array.getJSONObject(i);
                            if(goodIds.length()>0) {
                                goodIds.append(",");
                            }
                            goodIds.append(null != json?json.get("id")+"":"");
                        }
                        if(goodIds.length()>0) {//1可借用 3已使用 2已预订
                            Map params = new HashMap();
                            params.put("goodIds",goodIds.toString());
                            params.put("state",1);
                            goodsService.updateStateByGoodIds(params);
                        }
                        pointListService.removeBrands(goodIds.toString());
                    }
                }
            }else if ("5010".equals(processCode)) {//故障提报处理
                //故障提报，成功
                //变更工单状态
                Map params = new HashMap();

                params.put("modifor",userId);
                params.put("faultState",2);//待处理
                params.put("id",sourceId);
                if(null != dataValue && dataValue.length()>0) {
                    JSONObject dataObject = JSONObject.parseObject(dataValue);
                    params.put("faultLevel",dataObject.get("faultLevel"));
                    aFaultInfoDao.updateFaultStateAndFaultLevel(params);
                }else {
                    aFaultInfoDao.updateFaultState(params);
                }

//              AFaultInfo aFaultInfo = aFaultInfoDao.getById(sourceId);

                //生成工单
//                AFaultList aFaultList = new AFaultList();
//                aFaultList.setTrainNo(aFaultInfo.getTrainNo());
//                aFaultList.setTrainUnit(aFaultInfo.getTrainUnit());
//                aFaultList.setJobId(sourceId);
//                aFaultList.setAuditType(null);

//                String msg = "登车请点单["+pointPleaseEntity.getJobContent()+"],列车号["+boardingEntities.get(0).getCarNum()+"]请点审批通过.";
//                HttpClientUtilsToClient.getInstance().doGet(pointPleaseEntity.getCreateBy(),ConstantValue.PUSH_MSG_DUANDIAN, msg);
            }else if ("5011".equals(processCode)
                    || "5012".equals(processCode)
                    ||"5013".equals(processCode)) {//故障工单处理
                //故障工单，成功
                AFaultList aFaultList = aFaultListDao.getById(sourceId);

                Map faultListParams = new HashMap();
                Map faultInfoParams = new HashMap();
                faultListParams.put("modifor",userId);
                faultInfoParams.put("modifor",userId);
                if(aFaultList.getSurplusValue().compareTo(Float.valueOf("0.0"))>0) {//有剩余,部分完成6
                    faultListParams.put("workState",6);
                    faultInfoParams.put("faultState",3);//部分已处理
                }else {//无剩余，完成3
                    faultListParams.put("workState",3);
                    faultInfoParams.put("faultState",5);//已处理5
                }

                faultInfoParams.put("id",aFaultList.getJobId());

                if(null != dataValue && dataValue.length()>0) {
                    JSONObject dataObject = JSONObject.parseObject(dataValue);
                    faultInfoParams.put("coeDiff",dataObject.get("coeDiff"));
                }
                aFaultInfoDao.updateFaultStateAndCoeDiff(faultInfoParams);
                faultListParams.put("id",aFaultList.getId());
                aFaultListDao.updateWorkState(faultListParams);
                if(null != dataValue && dataValue.length()>0 && dataValue.contains("coeDiff")) {
                    AFaultList aFaultList2 = aFaultListDao.getById(sourceId);
                    JSONObject dataObject = JSONObject.parseObject(dataValue);
                    String coeDiff2 = dataObject.get("coeDiff").toString();
                    if(null != coeDiff2 && coeDiff2.length()>0) {
                        aFaultList2.setCoeDiff(Float.valueOf(dataObject.get("coeDiff").toString()));
                    }
                    aFaultListDao.update(aFaultList2);
                }



//                AFaultInfo aFaultInfo = aFaultInfoDao.getById(sourceId);

                //生成工单
//                AFaultList aFaultList = new AFaultList();
//                aFaultList.setTrainNo(aFaultInfo.getTrainNo());
//                aFaultList.setTrainUnit(aFaultInfo.getTrainUnit());
//                aFaultList.setJobId(sourceId);
//                aFaultList.setAuditType(null);

//                String msg = "登车请点单["+pointPleaseEntity.getJobContent()+"],列车号["+boardingEntities.get(0).getCarNum()+"]请点审批通过.";
//                HttpClientUtilsToClient.getInstance().doGet(pointPleaseEntity.getCreateBy(),ConstantValue.PUSH_MSG_DUANDIAN, msg);
            }
        }else {
            if("3011".equals(processCode) ||"3012".equals(processCode)) {
                //审批不通过，状态变更为进行中
                taskListService.finishJob(sourceId,2,null,null,null,null,userId,null);
            }
            else if("4011".equals(processCode) || "4013".equals(processCode) || "4015".equals(processCode) || "4017".equals(processCode)) {//外单位与外分部请点不通过
                    //外单位与外分部请点不通过
                APointPleaseForgein forgein = aPointPleaseForgeinDao.getById(sourceId);
                forgein.setState(4);
                String brands = forgein.getBrands();
                JSONArray array = null != brands && brands.length()>0?JSONArray.parseArray(brands):new JSONArray();
                StringBuilder brandIds = new StringBuilder("");
                for(int i=0;i<array.size();i++) {
                    JSONObject obj = array.getJSONObject(i);
                    brandIds.append(",").append(obj.getString("id"));
                }
                if(brandIds.length()>0) {
                    Map params = new HashMap();
                    params.put("state","1");
                    params.put("goodIds",brandIds.substring(1));
                    goodsService.updateStateByGoodIds(params);
                }
                aPointPleaseForgeinDao.update(forgein);

                String taskId = forgein.getTaskId()!=null ?forgein.getTaskId():"";
                Map params = new HashMap();
                params.put("ppointId",sourceId);
                params.put("workState",0);//未开始
                params.put("ids",taskId);
                aTaskListForgeinDao.updateWorkStateAndPointByIds(params);

//                修改故障工单为未开始
                List<AFaultList> faultList = aFaultListDao.getFaultByPointId(sourceId);
                for(AFaultList fault:faultList){
                    Map map = new HashMap();
                    map.put("modifor",userId);
                    map.put("faultState",1);//未开始
                    map.put("id",fault.getId());
                    aFaultListDao.updateWorkState(map);
                }


            }else if("4012".equals(processCode) || "4014".equals(processCode) || "4016".equals(processCode) || "4018".equals(processCode)) {//外单位与外分部销点不通过
                APointPleaseForgein forgein = aPointPleaseForgeinDao.getById(sourceId);
                forgein.setState(7);
                aPointPleaseForgeinDao.update(forgein);
            }else if ("5010".equals(processCode)) {
                //故障提报，失败
                //变更工单状态
                Map params = new HashMap();
                params.put("modifor",userId);
                //非故障
                params.put("faultState",-1);
                params.put("id",sourceId);
                aFaultInfoDao.updateFaultState(params);
            }else if ("5011".equals(processCode)
                    ||"5012".equals(processCode)
                    ||"5013".equals(processCode)) {
                //故障工单处理
                //故障工单，失败
                AFaultList aFaultList = aFaultListDao.getById(sourceId);
                aFaultList.setModifor(userId);
                //失败
                aFaultList.setWorkState(7);
                aFaultListDao.update(aFaultList);
            }
        }
    }


    public ATaskListForgein copyTaskListForgein(String userId,ATaskListForgein taskListForgein) {
        Date current = Calendar.getInstance().getTime();
        taskListForgein.setCreatetime(current);
        taskListForgein.setCreator(userId);
        taskListForgein.setModifor(userId);
        taskListForgein.setModifytime(current);
        taskListForgein.setPointId("");
        taskListForgein.setSourceNum(taskListForgein.getSourceNum());
        taskListForgein.setWorkState(0);
        taskListForgein.setTaskSource(9);//任务来源为部分完成
        return taskListForgein;
    }

    public void updatePointListMetroStatus(String userId,String pointId) {
        //取剩余所有请点
        List<PointList> pointLists = pointListService.getAllPointList();
        if(null == pointLists || pointLists.size()<=0) pointLists=new ArrayList();
        String  trainNoStr = "";//取pointId的列车号
        StringBuilder  trainNoStrB = new StringBuilder("");//取pointId的列车号
        Map<String,Integer> trainJobCountMap = new HashMap();//取pointId关联的列车 作业数量
        Map<String,String> trainStatusMap = new HashMap();//取pointId列车原有的状态
        for(PointList pointList:pointLists) {
            if(null !=pointList.getParentId() && pointList.getParentId().length()>0) {
                Integer pointcount = trainJobCountMap.get(pointList.getTrainNo());
                if(null == pointcount ) pointcount = 0;
                if(!pointId.equals(pointList.getPpointId())) {//车辆剩余请点数（不包含pointId）
                    pointcount ++;
                }else {
                    trainStatusMap.put(pointList.getTrainNo(),pointList.getOriginalStatus());//列车原来的状态值
                    trainNoStrB.append(pointList.getTrainNo()).append(",");
                }
                trainJobCountMap.put(pointList.getTrainNo(),pointcount);//剩余作业数
            }
        }
        trainNoStr = trainNoStrB.toString();
        List<PointList> updatePointList = new ArrayList();
        for(PointList record:pointLists) {
            if(null == record.getParentId()|| record.getPpointId().trim().length()<1) {//列车结点
                if(trainNoStr.indexOf(record.getTrainNo())>=0) {//point所关联的列车
                    Integer pointCount = trainJobCountMap.get(record.getTrainNo());//剩余作业点数
                    if(0==pointCount) {//
                        record.setMetroStatus(trainStatusMap.get(record.getTrainNo()));
                        updatePointList.add(record);
                    }
                }
            }
        }

        if(null != updatePointList && updatePointList.size()>0) {
            pointListService.batchUpdateMetroStatusById(updatePointList);
        }
    }



    public List<PointList> getChidrenPointListNotContainPointId(String trainNo,List<PointList> list,String notpointId) {
        List<PointList> children = new ArrayList();
        for(PointList record:list) {
            if(null != record.getParentId() && record.getParentId().length()>0 && trainNo.equals(record.getTrainNo()) && !notpointId.equals(record.getPpointId())) {
                children.add(record);
            }
        }
        return children;
    }

    public Integer getElectricByChildrenPointList(List<PointList> children) {
        Integer electric = null;
        boolean electric_0 = false;//无电
        boolean electric_1 = false;//有电
        for(PointList child:children) {
            if(null!=child.getIsElectric()){
                if(0==child.getIsElectric()) {//无电
                    electric_0 = true;
                }else if(1==child.getIsElectric()) {//有电
                    electric_1 = true;
                }else {
                    electric_0 = true;
                    electric_1 = true;
                }
            }
        }
        if(electric_0) {//有无电作业
            if(electric_1) {//有有电作业
                electric = 2;
            }else {//无有电作业
                electric = 0;
            }
        }else {//无无电作业
            if(electric_1) {//有有电作业
                electric = 1;
            }else {//无有电作业
                electric=null;
            }
        }
        return electric;
    }

    public List<AWorkflowData> getAllWorkflowListByWorkflowId(String workflowId){
        return aWorkflowDataDao.getAllWorkflowListByWorkflowId(workflowId);
    }

    public void submitWorkflowWorkflow(String workflowId,Integer auditState,String auditResults,String data,String userId){
        AWorkflowData currentWorkflow = aWorkflowDataDao.getById(workflowId);
        if(currentWorkflow.getRunState()==0) {//当前状态为非运行状态
            throw new RuntimeException("流程已提交审批,请勿重复提交");
        }
        Map<String,Object> newValueMap = new HashMap();
        if(null != data && data.length()>0) {
            String[] array = data.split("&");
            for(int i=0;i<array.length;i++) {
                String keyvalue = array[i];
                String[] keyvalueArray = keyvalue.split("=");
                if(keyvalueArray.length>1) {
                    newValueMap.put(keyvalueArray[0], keyvalueArray[1]);
                }else {
                    newValueMap.put(keyvalueArray[0], "");
                }
            }
        }
        Date current = Calendar.getInstance().getTime();

        String orginalValue = currentWorkflow.getDataValue();
        JSONObject orginalData = new JSONObject();
        if(null != orginalValue && orginalValue.length()>0) {
            orginalData = JSONObject.parseObject(orginalValue);
        }
        //新值补充到原值中,当权限w为1时，存在新值
        String dataAuth = currentWorkflow.getDataAuth();
        if(dataAuth!=null && dataAuth.length()>0) {
            JSONArray authArray = JSONArray.parseArray(dataAuth);
            for(int i=0;i<authArray.size();i++) {
                JSONObject authObj = authArray.getJSONObject(i);
                if("1".equals(authObj.get("w")+"")) {//当数据权限为可写时，只读或隐藏字段值不变，更新新值
                    String propName = authObj.getString("propName");
                    orginalData.put(propName, newValueMap.get(propName)!=null?newValueMap.get(propName)+"":"");
                }
            }
        }
        AWorkflowData startWorkflow = aWorkflowDataDao.getStartWorkflow(currentWorkflow.getSourceId(),currentWorkflow.getWbNo());
        if(1==auditState) {//审批通过
            boolean runtonext = false;//判断流程是否能继续走下去
            String actualAuditors = null != currentWorkflow.getActualAuditors()?currentWorkflow.getActualAuditors():"";
            Integer auditMode = currentWorkflow.getAuditMode();//审批方式：1一人同意即放下流转；全部同意才往下流转

            if(1==auditMode) {
                runtonext =true;
                currentWorkflow.setActualAuditors(userId);
            }else {
                if(actualAuditors.indexOf(userId)<0) {//不存在
                    if(actualAuditors.length()>0) {
                        actualAuditors += ",";
                    }
                    actualAuditors +=  userId;
                }
                currentWorkflow.setActualAuditors(actualAuditors);//更新实际审批人
                String auditors = currentWorkflow.getAuditors();
                String[] array = auditors.split(",");
                runtonext = true;
                for(int i=0;i<array.length;i++) {
                    if(actualAuditors.indexOf(array[1])<0) {//存在还有需要审核人
                        runtonext = false;
                        break;
                    }
                }
            }

            if(runtonext) {
                currentWorkflow.setRunState(0);
                AWorkflowNode runNode = workflowNodeDao.getById(currentWorkflow.getPassNodeId());
                AWorkflowData runWorkflow = new AWorkflowData();
                runWorkflow.setId(UUID());
                runWorkflow.setWbNo(currentWorkflow.getWbNo());//流程工单号
                runWorkflow.setSort(currentWorkflow.getSort()+1);
                runWorkflow = copyNodeToWorkflow(runNode,runWorkflow,startWorkflow);
                if(runNode.getAssignType()==6) {
                    runWorkflow.setAuditors(newValueMap.get("assignIds")+"");
                }
                runWorkflow.setProcessId(currentWorkflow.getProcessId());
                runWorkflow.setProcessCode(currentWorkflow.getProcessCode());
                runWorkflow.setProcessName(currentWorkflow.getProcessName());
                runWorkflow.setFormId(currentWorkflow.getFormId());
                runWorkflow.setFormUrl(currentWorkflow.getFormUrl());
                runWorkflow.setSourceId(currentWorkflow.getSourceId());
                runWorkflow.setAuditState(0);
                if(runWorkflow.getNodeType()==3) {
                    runWorkflow.setRunState(0);//运行中
                    runWorkflow.setAuditState(auditState);
                    runWorkflow.setAuditors(startWorkflow.getCreator());
                    runWorkflow.setActualAuditors(startWorkflow.getCreator());
                    runWorkflow.setModifytime(current);
                }else {
                    runWorkflow.setRunState(1);//运行中
                }
                runWorkflow.setDataValue(orginalData.toString());
                runWorkflow.setCreator(startWorkflow.getCreator());
                runWorkflow.setCreatetime(startWorkflow.getCreatetime());
                runWorkflow.setModifor(userId);

                if(null == runWorkflow.getAuditors() || runWorkflow.getAuditors().length()<1) {
                    throw new RuntimeException("找不到审批者");
                }
                //流程结束，回调
                if(runWorkflow.getNodeType()==3 ) {//结束时往工单表添加记录

                    dealBusiness(runWorkflow.getSourceId(),runWorkflow.getProcessCode(),auditState,userId,orginalData.toString());
                }
                aWorkflowDataDao.insert(runWorkflow);
                //推送消息
                pushMsgToAppAndWeb(runWorkflow);
            }
        }else if(-1==auditState) {//审批不通过
            currentWorkflow.setRunState(0);
            AWorkflowNode runNode = workflowNodeDao.getById(currentWorkflow.getNotpassNodeId());
            AWorkflowData runWorkflow = new AWorkflowData();
            runWorkflow.setId(UUID());
            runWorkflow.setWbNo(currentWorkflow.getWbNo());//流程工单号
            runWorkflow.setSort(currentWorkflow.getSort()+1);
            runWorkflow = copyNodeToWorkflow(runNode,runWorkflow,startWorkflow);
            if(runNode.getAssignType()==6) {

                runWorkflow.setAuditors(newValueMap.get("assignIds")+"");
            }
            runWorkflow.setProcessId(currentWorkflow.getProcessId());
            runWorkflow.setProcessCode(currentWorkflow.getProcessCode());
            runWorkflow.setProcessName(currentWorkflow.getProcessName());
            runWorkflow.setFormId(currentWorkflow.getFormId());
            runWorkflow.setFormUrl(currentWorkflow.getFormUrl());
            runWorkflow.setSourceId(currentWorkflow.getSourceId());
            runWorkflow.setRunState(1);//运行中
            runWorkflow.setAuditState(0);
            runWorkflow.setDataValue(orginalData.toString());
            if(runWorkflow.getNodeType()==3) {
                runWorkflow.setRunState(0);//运行中
                runWorkflow.setAuditState(auditState);
                runWorkflow.setAuditors(startWorkflow.getCreator());
                runWorkflow.setActualAuditors(startWorkflow.getCreator());
                runWorkflow.setModifytime(current);
            }else {
                runWorkflow.setRunState(1);//运行中
            }
            runWorkflow.setCreator(startWorkflow.getCreator());
            runWorkflow.setCreatetime(startWorkflow.getCreatetime());
            runWorkflow.setModifor(userId);

            if(null == runWorkflow.getAuditors() || runWorkflow.getAuditors().length()<1) {
                throw new RuntimeException("找不到审批者");
            }
            aWorkflowDataDao.insert(runWorkflow);
            //流程结束，回调
            if(runWorkflow.getNodeType()==3 ) {//结束时往工单表添加记录
                dealBusiness(runWorkflow.getSourceId(),runWorkflow.getProcessCode(),auditState,userId,orginalData.toString());
            }
            //推送消息
            pushMsgToAppAndWeb(runWorkflow);
        }
        currentWorkflow.setDataValue(orginalData.toString());
        currentWorkflow.setAuditState(auditState);
        currentWorkflow.setAuditResults(auditResults);
        currentWorkflow.setModifor(userId);
        currentWorkflow.setModifytime(current);


        //更新当前节点
        aWorkflowDataDao.update(currentWorkflow);
        //推送消息
        pushMsgToAppAndWeb(currentWorkflow);
    }

    public AWorkflowData getWorkflowDataById(String workflowId) {
        return aWorkflowDataDao.getById(workflowId);
    }


    public List<Map<String,Object>>  getBorrowListByWorkflowId(String workflowId){
        return aWorkflowDataDao.getBorrowListByWorkflowId(workflowId);
    }

    public List<AWorkflowData> getWorkflowListBySourceIdAndWbNo(Map params) {
        return aWorkflowDataDao.getWorkflowListBySourceIdAndWbNo(params);
    }

}
