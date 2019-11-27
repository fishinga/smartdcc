package com.attiot.railAnaly.point.controller;


import com.alibaba.fastjson.JSON;
import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.entity.TSType;
import com.attiot.railAnaly.base.param.TSTypeQueryParam;
import com.attiot.railAnaly.base.service.TSBaseUserService;
import com.attiot.railAnaly.base.service.TSTypeService;
import com.attiot.railAnaly.borrow.entity.ABorrowList;
import com.attiot.railAnaly.borrow.entity.ABorrowListDetail;
import com.attiot.railAnaly.borrow.service.BorrowListDetailService;
import com.attiot.railAnaly.borrow.service.BorrowListService;
import com.attiot.railAnaly.common.CommonUtils;
import com.attiot.railAnaly.common.util.StringUtil;
import com.attiot.railAnaly.fault.entity.AFaultList;
import com.attiot.railAnaly.fault.service.FaultService;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.foundation.ParamVerifyUtil;
import com.attiot.railAnaly.foundation.exception.AppException;
import com.attiot.railAnaly.foundation.exception.ErrorInfo;
import com.attiot.railAnaly.goods.service.PointListService;
import com.attiot.railAnaly.point.entity.*;
import com.attiot.railAnaly.point.param.APointPleaseForgeinQueryParam;
import com.attiot.railAnaly.point.param.APointPleaseQueryParam;
import com.attiot.railAnaly.point.service.*;
import com.attiot.railAnaly.task.entity.ATaskList;
import com.attiot.railAnaly.task.entity.TaskTableData;
import com.attiot.railAnaly.task.service.TaskListService;
import com.attiot.railAnaly.task.service.TaskTableDataService;
import com.attiot.railAnaly.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "point/pointPleaseController")
public class PointPleaseController {

    @Autowired
    private PointPleaseService pointService;
    @Autowired
    private PointPleaseBoardingService boardingService;
    @Autowired
    private PointPleaseStingerService stingerService;
    @Autowired
    private PointListService pointListService;

    @Autowired
    private PointPleaseCartService cartService;

    @Autowired
    private TSBaseUserService userService;

    @Autowired
    private TaskListService taskListService;

    @Autowired
    private BorrowListService borrowListService;
    @Autowired
    private BorrowListDetailService borrowListDetailService;
    @Autowired
    private TaskTableDataService taskTableDataService;
    @Autowired
    private TSTypeService typeService;
    @Autowired
    private PointPleasePowerService powerService;

    @Autowired
    private FaultService faultService;
    private static final Logger logger = Logger.getLogger(PointPleaseController.class);

    /***
     * 获取请点信息：按用户ID和请点状态
     * @param request
     * @param response
     */
    @RequestMapping(value = "getPoint")
    public void getPoint(HttpServletRequest request , HttpServletResponse response){
        AppResult result = new AppResult();
        try {
            String json = request.getParameter("data");
            if(json == null){
                result.setSuccess(false);
                result.setMsg("参数错误");
                result.writer(request,response);
                return;
            }
            ParamVerifyUtil.verifyNotBlank(json);
            Map jsonMap = JSON.parseObject(json,Map.class);

            String bpmStatus = String.valueOf(jsonMap.get("bpmStatus"));
            String accessToken = String.valueOf(jsonMap.get("accessToken"));
            String pstatus = String.valueOf(jsonMap.get("pstatus"));
            APointPleaseQueryParam pointPleaseQueryParam = new APointPleaseQueryParam();
            if(StringUtil.isNotEmpty(accessToken)){
                pointPleaseQueryParam.setLimit(-1);
                pointPleaseQueryParam.setCreateBy(accessToken);
                if(StringUtil.isNotEmpty(bpmStatus) && !"null".equals(bpmStatus)){
                    pointPleaseQueryParam.setBpmStatus(bpmStatus);
                    if("3".equals(bpmStatus)){//交接人
                        pointPleaseQueryParam.setTransferId(accessToken);
                    }
                }
                if(StringUtil.isNotEmpty(pstatus)&& !"null".equals(pstatus)){
                    pointPleaseQueryParam.setPstatus(pstatus);
                }
                Page<APointPlease> page =  pointService.query(pointPleaseQueryParam);
                List lst = page.getResults();
                for(int k=0;k<lst.size();k++){
                    APointPlease pointPlease = (APointPlease) lst.get(k);
                    if("1".equals(pointPlease.getPointType())) {
                        APointPleaseBoarding boarding = boardingService.getByPointId(pointPlease.getId());
                        pointPlease.setCarNum(null != boarding?boarding.getCarNum():"");
                    }else if("2".equals(pointPlease.getPointType())) {
                        pointPlease.setCarNum("");
                    }else if("3".equals(pointPlease.getPointType())) {
                        APointPleaseCart cart = cartService.getByPointId(pointPlease.getId());
                        pointPlease.setCarNum(null != cart?cart.getTrainNum():"");
                    }
                    pointPlease.setCreateDateStr(DateUtil.parseTime(pointPlease.getCreateDate()));
                    pointPlease.setUpdateDateStr(DateUtil.parseTime(pointPlease.getUpdateDate()));
                    pointPlease.setTransferTimeStr(DateUtil.parseTime(pointPlease.getTransferTime()));
                    pointPlease.setWorkingTimeStr(DateUtil.parseTime(pointPlease.getWorkingTime()));
                    //判断任务是否完成
                    List<ATaskList>  taskLists = taskListService.getTaskByPointId(pointPlease.getId());
                    int countTask = 0;
                    for(int z=0;z<taskLists.size();z++){
                        ATaskList task = taskLists.get(z);
                        if(task.getWorkState()==3 || task.getWorkState()==4 || task.getWorkState()==6){
                            countTask++;
                        }
                    }

//                    故障工单是否有未完成的
                    List<AFaultList> faultLists = faultService.getFaultByPointId(pointPlease.getId());
                    int countFault = 0;
                    for(int index=0;index<faultLists.size();index++){
                        AFaultList fault= faultLists.get(index);
                        if(fault.getWorkState()==3 || fault.getWorkState()==4 || fault.getWorkState()==6){
                            countFault++;
                        }else {
                            if(fault.getScheOperators() != null && fault.getScheOperators().length()>0){
                                if(!fault.getScheOperators().contains(accessToken)){
                                    countFault++;
                                }
                            }
                        }
                    }

                    boolean pointFLag = true;
                    if(countTask != taskLists.size()){
                        pointFLag = false;
                    }
                    if(countFault != faultLists.size()){
                        pointFLag = false;
                    }
                    //判断是否有未还的物品
                    List<ABorrowList> borrowList =  borrowListService.getByPointId(pointPlease.getId());
                    int countBor = 0;
                    int cardFlag = 0;
                    StringBuilder borrowIds = new StringBuilder("");
                    if(null!=borrowList && borrowList.size()>0){
                        for(int l=0;l<borrowList.size();l++){
                            ABorrowList bor = borrowList.get(l);
                            if(bor.getBorrowState() == 4){//借用状态为待还
                                countBor++;
                                if(StringUtils.isNotEmpty(borrowIds.toString())&&StringUtils.isNotBlank(borrowIds.toString())){
                                    borrowIds.append(bor.getId());
                                }else{
                                    borrowIds.append(",").append(bor.getId());
                                }
                            }
                            //标记作业牌状态（2-部分申请中，1-可归还,0-已归还或者没有可归还）
                            if(bor.getBorrowState() == 3){//借用申请
                                cardFlag = 2;
                            }else if(bor.getBorrowState() == 4){//待还（可归还）
                                if(cardFlag!=2){
                                    cardFlag = 1;
                                }
                            }else{//已归还或者没有可归还
                                cardFlag = 0;
                            }
                        }
                    }
                    pointPlease.setCardFlag(cardFlag);
                    boolean borrowFLag = true;
                    if(countBor >0){
                        borrowFLag=false;//有工具未还
                    }
                    if(borrowFLag&&pointFLag){//能否销点：0-不可销点，1-可销点
                        pointPlease.setPinFlag("1");
                    }else{
                        pointPlease.setPinFlag("0");
                    }
                }

                //加载配合外来人员的数据
                APointPleaseForgeinQueryParam param = new APointPleaseForgeinQueryParam();
                param.setForgeinType(1);
                param.setState(3);
                param.setWorker(accessToken);
                List<APointPleaseForgein> forgeinList = pointService.getForgeinList(param);
                for(int i=0;i<forgeinList.size();i++){
                    APointPlease please = new APointPlease();
                    please.setId(forgeinList.get(i).getId());
                    please.setPointType("5");
                    please.setCarNum(forgeinList.get(i).getTrainNo());
                    please.setJobContent(forgeinList.get(i).getJobContent());
                    please.setPinFlag("1");
                    please.setCreateDateStr(forgeinList.get(i).getWorkingTime());
                    lst.add(please);
                }



                result.setDataList(lst);
            }else{
                result.setSuccess(false);
                result.setMsg("参数有错！");
            }
            result.writer(request,response);
        } catch (IllegalArgumentException e) {
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            log.error("系统内部异常", e);
        }
    }

    /**
     * 获取信号机字典数据
     * @param request
     * @param response
     */
    @RequestMapping(value = "getMessage")
    public void getMessage(HttpServletRequest request , HttpServletResponse response) {
        AppResult result = new AppResult();

        try {
            TSTypeQueryParam tsTypeQueryParam = new TSTypeQueryParam();
            String code  = "teleseme";
            List<TSType> tList = typeService.queryListByCode(code);

            if (null != tList || tList.size() !=0){
                result.setMsg("成功！");
                HashMap _map = new HashMap();
                _map.put("rtn",tList);
                List _mapLst = new ArrayList();
                _mapLst.add(_map);
                result.setDataList(_mapLst);
                result.setSuccess(true);
            }
            result.writer(request, response);
        }catch (IllegalArgumentException e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            log.error("系统内部异常", e);
        }
    }

    /***
     * 保存请点信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "savePoint")
    public void savePoint(HttpServletRequest request,HttpServletResponse response){
        AppResult result = new AppResult();
        String refnum = "";
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
            if(!jsonMap.isEmpty()){
                logger.info("savePoint(map):"+jsonMap);
                String accessToken = String.valueOf(jsonMap.get("accessToken"));
                boolean isdanger = CommonUtils.isDanger(accessToken+"_PointPleaseController_savePoint");//危险请求
                if(isdanger) {
                    result.setSuccess(false);
                    result.setMsg("操作太频繁,请稍等...");
                    result.writer(request,response);
            }

                refnum = String.valueOf(jsonMap.get("refnum"));
                if(null == refnum || "null".equals(refnum) || refnum.length()<1) {
                    result.setSuccess(false);
                    result.setMsg("提交参数无效,请退出重新登陆.");
                    result.writer(request,response);
                }
                logger.info("savePoint(refnum):"+refnum);
                //判断当前refnum的状态值:
                Integer refstate = CommonUtils.getCurrentRefnumState(refnum);
                if(1==refstate) {//提交中,请勿重新提交
                    result.setSuccess(false);
                    result.setMsg("提交中,请勿重新提交...");
                    result.writer(request,response);
                    return;
                }else if(2==refstate) {
                    result.setSuccess(false);
                    result.setMsg("已提交,请勿重新提交...");
                    result.writer(request,response);
                    return ;
                }

                logger.info("refstate:"+refstate);
                CommonUtils.setRefnum(refnum,1);//提交中
                String resultInfo = pointService.savePointWithTransaction(jsonMap);//提交请点
                result.setMsg(resultInfo);
                CommonUtils.setRefnum(refnum,2);//提交成功
            }else {
                result.setSuccess(false);
                result.setMsg("参数不能为空！");
            }
        }catch (IllegalArgumentException e){
            CommonUtils.setRefnum(refnum,0);//提交失败
            logger.info("请点保存异常:"+e.getCause());
            result.setMsg("请点保存异常,请联系管理员.");
            result.setSuccess(false);
            log.error("系统内部异常", e);
        }
        result.writer(request,response);
    }

    @RequestMapping(value = "preBoardingPoint")
    public void preBoardingPoint(HttpServletRequest request,HttpServletResponse response){
        AppResult result = new AppResult();
        String refnum = "";
        try{
            String json = request.getParameter("data");
            if(json != null){
            ParamVerifyUtil.verifyNotBlank(json);
            Map jsonMap = JSON.parseObject(json,Map.class);
            logger.info("preBoardingPoint(map):"+jsonMap);
            String carNum = String.valueOf(jsonMap.get("carNum"));
            String jobType = String.valueOf(jsonMap.get("jobType"));
            String alarmInfo = pointListService.getTrainAlarm(Integer.valueOf(jobType),carNum);
            result.setSuccess(true);
            result.setMsg(alarmInfo);
            }else {
                throw new AppException(ErrorInfo.PARAM_MISS);
            }
        }catch (IllegalArgumentException e){
            result.setMsg("请点保存异常,请联系管理员.");
            result.setSuccess(false);
            log.error("系统内部异常", e);
        }
        result.writer(request,response);
    }


    /***
     * 保存销点信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "pinPoint")
    public void pinPoint(HttpServletRequest request,HttpServletResponse response){
        AppResult result = new AppResult();
        String refnum = "";
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
            if(!jsonMap.isEmpty()){
                logger.info("pinPoint(map):"+jsonMap);
                String accessToken = String.valueOf(jsonMap.get("accessToken"));
                boolean isdanger = CommonUtils.isDanger(accessToken+"_PointPleaseController_pinPoint");
                if(isdanger) {
                    result.setSuccess(false);
                    result.setMsg("操作太频繁,请稍等...");
                    result.writer(request,response);
                    return ;
                }

                refnum = String.valueOf(jsonMap.get("refnum"));
                if(null == refnum || "null".equals(refnum) || refnum.length()<1) {
                    result.setSuccess(false);
                    result.setMsg("提交参数无效,请退出重新登陆.");
                    result.writer(request,response);
                    return;
                }
                logger.info("pinPoint(refnum):"+refnum);
                //判断当前refnum的状态值:
                Integer refstate = CommonUtils.getCurrentRefnumState(refnum);
                if(1==refstate) {//提交中,请勿重新提交
                    result.setSuccess(false);
                    result.setMsg("提交中,请勿重复提交...");
                    result.writer(request,response);
                    return;
                }else if(2==refstate) {
                    result.setSuccess(false);
                    result.setMsg("已提交,请勿重复提交...");
                    result.writer(request,response);
                    return ;
                }
                CommonUtils.setRefnum(refnum,1);//提交中
                pointService.saveFinishPoint(jsonMap);
                CommonUtils.setRefnum(refnum,2);//提交成功
            }else {
                result.setSuccess(false);
                result.setMsg("参数不能为空！");
            }
        }catch (IllegalArgumentException e){
            CommonUtils.setRefnum(refnum,0);//提交中
            result.setMsg("销点提交失败,请联系管理员");
            result.setSuccess(false);
            logger.error("系统内部异常");
        }
        result.writer(request,response);
    }



    /***
     * 获取请点信息：按用户ID和请点状态
     * @param request
     * @param response
     */
    @RequestMapping(value = "getPointById")
    public void getPointById(HttpServletRequest request , HttpServletResponse response){
        AppResult result = new AppResult();
        try {
            String json = request.getParameter("data");
            if(json == null){
                result.setSuccess(false);
                result.setMsg("参数错误");
                result.writer(request,response);
                return;
            }
            ParamVerifyUtil.verifyNotBlank(json);
            Map jsonMap = JSON.parseObject(json,Map.class);
            String departId = String.valueOf(jsonMap.get("departId"));
            String id = String.valueOf(jsonMap.get("id"));
            String accessToken = String.valueOf(jsonMap.get("accessToken"));
            String pointType = String.valueOf(jsonMap.get("pointType"));
            if(StringUtil.isNotEmpty(accessToken)&&StringUtil.isNotEmpty(id)){
                if("5".equals(pointType)){
                    List lst = new ArrayList();
                    Map<String,Object> rtnMap = new HashMap();
                    APointPleaseForgein forgein = pointService.getForgeinById(id);
                    rtnMap.put("forgein",forgein);
                    lst.add(rtnMap);
                    result.setDataList(lst);
                }else{
                    Map<String,Object> rtnMap = new HashMap();
                    APointPlease aPointPlease = pointService.getById(id);
                    aPointPlease.setCreateDateStr(DateUtil.parseTime(aPointPlease.getCreateDate()));
                    aPointPlease.setUpdateDateStr(DateUtil.parseTime(aPointPlease.getUpdateDate()));
                    aPointPlease.setTransferTimeStr(DateUtil.parseTime(aPointPlease.getTransferTime()));
                    aPointPlease.setWorkingTimeStr(DateUtil.parseTime(aPointPlease.getWorkingTime()));
                    //判断是否需要填写风险预控表
                    List<TaskTableData> taskTableDataLst = taskTableDataService.getByPointIdCode(aPointPlease.getId(),"101");
                    if(null!=taskTableDataLst && taskTableDataLst.size()>0){
                        aPointPlease.setRiskFlag(1);
                    }else{
                        aPointPlease.setRiskFlag(0);
                    }
                    rtnMap.put("point",aPointPlease);
                    if("1".equals(aPointPlease.getPointType())){
                        APointPleaseBoarding boarding = boardingService.getByPointId(aPointPlease.getId());
                        rtnMap.put("boarding",boarding);
                    }else if("2".equals(aPointPlease.getPointType())){
                        APointPleaseStinger stinger = stingerService.getByPointId(aPointPlease.getId());
                        stinger.setPowerOffTimeStr(DateUtil.parseTime(stinger.getPowerOffTime()));
                        stinger.setConfirmTimeStr(DateUtil.parseTime(stinger.getConfirmTime()));
                        stinger.setSendKeyTimeStr(DateUtil.parseTime(stinger.getSendKeyTime()));
                        stinger.setGivePowerTimeStr(DateUtil.parseTime(stinger.getGivePowerTime()));
                        stinger.setOutageTimeStr(DateUtil.parseTime(stinger.getOutageTime()));
                        stinger.setTakeBackTimeStr(DateUtil.parseTime(stinger.getTakeBackTime()));
                        rtnMap.put("stinger",stinger);
                    }else if("3".equals(aPointPlease.getPointType())){
                        APointPleaseCart cart = cartService.getByPointId(aPointPlease.getId());
                        cart.setBlockTimeStr(DateUtil.parseTime(cart.getBlockTime()));
                        rtnMap.put("cart",cart);
                    }else if("4".equals(aPointPlease.getPointType())){
                        PointPleasePower power = powerService.getByPointId(aPointPlease.getId());
                        rtnMap.put("power",power);
                    }
                    List lst = new ArrayList();
                    lst.add(rtnMap);
                    result.setDataList(lst);
                }

            }else{
                result.setSuccess(false);
                result.setMsg("参数有错！");
            }
            result.writer(request,response);
        } catch (IllegalArgumentException e) {
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            log.error("系统内部异常", e);
        }
    }

    /***
     * 根据请点ID，查看任务完成情况及作业牌借用情况。
     * @param request
     * @param response
     */
    @RequestMapping(value = "getPointSituation")
    public void getPointSituation(HttpServletRequest request , HttpServletResponse response){
        AppResult result = new AppResult();
        try {
            String json = request.getParameter("data");
            if(json == null){
                result.setSuccess(false);
                result.setMsg("参数错误");
                result.writer(request,response);
                return;
            }
            ParamVerifyUtil.verifyNotBlank(json);
            Map jsonMap = JSON.parseObject(json,Map.class);
            String ppointId = String.valueOf(jsonMap.get("ppointId"));
            String accessToken = String.valueOf(jsonMap.get("accessToken"));
            List list = new ArrayList();
            list.add(getPointMap(ppointId));
            result.setDataList(list);
            result.setSuccess(true);
            result.writer(request,response);
        } catch (IllegalArgumentException e) {
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            log.error("系统内部异常", e);
        }
    }

    private Map getPointMap(String ppointId) {
        Map<String,Object> resultMap = new HashMap();
        List<ATaskList>  taskLists = taskListService.getTaskByPointId(ppointId);
        //        根据请点ID查询故障工单
        List<AFaultList> faultLists = faultService.getFaultByPointId(ppointId);
        if(faultLists!=null&&faultLists.size()>0){
            ATaskList task = new ATaskList();
            for(AFaultList fault:faultLists){
                task.setScheOperators(fault.getScheOperators());
                task.setTaskName(fault.getTaskName());
                task.setTrainNo(fault.getTrainNo());
                task.setWorkState(fault.getWorkState());
                taskLists.add(task);
            }
        }
        StringBuilder userIds = new StringBuilder("");
        if(null == taskLists || taskLists.size()<=0) {
            taskLists = new ArrayList();
        }else {
            for(ATaskList record:taskLists) {
                if(null != record.getScheOperators() && record.getScheOperators().length()>0) {//计划作业人员
                    if(userIds.length()>0) {
                        userIds.append(",");
                    }
                    userIds.append(record.getScheOperators());
                }
            }
        }



        List<ABorrowList> borrowList =  borrowListService.getByPointId(ppointId);
        List<ABorrowListDetail> borrowDetailList = borrowListService.getBorrowDetailByPointId(ppointId);
        if(null == borrowList || borrowList.size()<=0) {
            borrowList = new ArrayList();
        }
        Map<String,ABorrowList> borrowMap = new HashMap();//借用单ID：借用时间
        SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(ABorrowList record:borrowList) {
            borrowMap.put(record.getId(),record);
        }
        if(null == borrowDetailList || borrowDetailList.size()<=0) {
            borrowDetailList = new ArrayList();
        }
        Map<String,ABorrowListDetail> detailMap = new HashMap();//goodId:listdetail
        for(ABorrowListDetail record:borrowDetailList) {
            ABorrowListDetail lastDetail = detailMap.get(record.getBorrowGoodsId());
            if(null == lastDetail) {
                detailMap.put(record.getBorrowGoodsId(),record);
            }else {//比较lastDetail和record借用时间，取借用时间最大的
                ABorrowList lastBorrow = borrowMap.get(lastDetail.getBorrowListId());
                String lastBorrowTime = null !=lastBorrow.getBorrowTime()?datetimeFormat.format(lastBorrow.getBorrowTime()):"";
                ABorrowList currentBorrow = borrowMap.get(record.getBorrowListId());
                String currentBorrowTime = null != record.getBorrowGoodsCategory()?datetimeFormat.format(currentBorrow.getBorrowTime()):"";
                if(lastBorrowTime.compareTo(currentBorrowTime)<0) {
                    detailMap.put(record.getBorrowGoodsId(),record);
                }
            }
        }
        List<ABorrowListDetail> goodsList = new ArrayList();
        Iterator iterator = detailMap.keySet().iterator();

        while(iterator.hasNext()) {
            String key = (String)iterator.next();
            ABorrowListDetail record = detailMap.get(key);
            ABorrowList aBorrowList = borrowMap.get(record.getBorrowListId());

            if(aBorrowList.getBorrower()!=null && aBorrowList.getBorrower().length()>0) {
                if(userIds.length()>0) {
                    userIds.append(",");
                }
                userIds.append(aBorrowList.getBorrower());
            }
            goodsList.add(record);
        }
        List<TSBaseUser> userList = userService.getByIds(userIds.toString());
        Map<String,TSBaseUser> userMap = new HashMap();
        if(null == userList || userList.size()<=0) {
            userList = new ArrayList();
        }else {
            for(TSBaseUser record:userList) {
                userMap.put(record.getId(),record);
            }
        }

        List dataList = new ArrayList();
        Map map = new HashMap();
        for(ABorrowListDetail record:goodsList) {
            ABorrowList aBorrowList = borrowMap.get(record.getBorrowListId());
            if(aBorrowList.getBorrower()!=null && aBorrowList.getBorrower().length()>0) {
                TSBaseUser baseuser = userMap.get(aBorrowList.getBorrower());
                if(null != baseuser) {
                    record.setRestituername(baseuser.getRealname()+"["+baseuser.getUsername()+"]");
                }
            }
        }
        for(ATaskList record:taskLists) {
            StringBuilder scheUsername = new StringBuilder("");
            String[] array = record.getScheOperators().split(",");
            for(int i=0;i<array.length;i++) {
                if(scheUsername.length()>0) {
                    scheUsername.append(",");
                }
                TSBaseUser tsuser = userMap.get(array[i]);
                scheUsername.append(tsuser.getRealname()).append("[").append(tsuser.getUsername()).append("]");
            }
            record.setScheOperatornames(scheUsername.toString());
        }


        map.put("goods",goodsList);
        map.put("tasks",taskLists);
        return map;
    }

    /***
     * 交接
     * @param request
     * @param response
     */
    @RequestMapping(value = "transfer")
    public void transfer(HttpServletRequest request,HttpServletResponse response){
        AppResult result = new AppResult();
        String refnum = "";
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

            if(!jsonMap.isEmpty()){
                logger.info("transfer(map):"+jsonMap);
                String accessToken = String.valueOf(jsonMap.get("accessToken"));
                boolean isdanger = CommonUtils.isDanger(accessToken+"_PointPleaseController_transfer");//危险请求
                if(isdanger) {
                    result.setSuccess(false);
                    result.setMsg("操作太频繁,请稍等...");
                    result.writer(request,response);
                    return;
                }

                refnum = "transfer"+ jsonMap.get("pointId") +","+ jsonMap.get("accessToken");
                if(null == refnum || "null".equals(refnum) || refnum.length()<1) {
                    result.setSuccess(false);
                    result.setMsg("提交参数无效,请退出重复登陆.");
                    result.writer(request,response);
                    return;
                }
                logger.info("transfer(refnum):"+refnum);
                //判断当前refnum的状态值:
                Integer refstate = CommonUtils.getCurrentRefnumState(refnum);
                if(1==refstate) {//提交中,请勿重新提交
                    result.setSuccess(false);
                    result.setMsg("提交中,请勿重复提交...");
                    result.writer(request,response);
                    return;
                }else if(2==refstate) {
                    result.setSuccess(false);
                    result.setMsg("已提交,请勿重复提交...");
                    result.writer(request,response);
                    return ;
                }

                CommonUtils.setRefnum(refnum,1);//提交中
                pointService.transfer(jsonMap);
                CommonUtils.setRefnum(refnum,2);//提交成功
            }else {
                result.setSuccess(false);
                result.setMsg("参数不能为空！");
            }
        }catch (IllegalArgumentException e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            CommonUtils.setRefnum(refnum,0);//提交失败
            log.error("系统内部异常", e);
        }
        result.writer(request,response);
    }
}