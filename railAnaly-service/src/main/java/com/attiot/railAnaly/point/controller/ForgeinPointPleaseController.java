package com.attiot.railAnaly.point.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.service.TSBaseUserService;
import com.attiot.railAnaly.base.service.TSUserService;
import com.attiot.railAnaly.borrow.controller.BorrowController;
import com.attiot.railAnaly.borrow.entity.ABorrowList;
import com.attiot.railAnaly.borrow.entity.ABorrowListDetail;
import com.attiot.railAnaly.borrow.service.BorrowListDetailService;
import com.attiot.railAnaly.borrow.service.BorrowListService;
import com.attiot.railAnaly.common.CommonUtils;
import com.attiot.railAnaly.common.util.DateUtils;
import com.attiot.railAnaly.common.util.JacksonUtil;
import com.attiot.railAnaly.common.util.StringUtil;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.foundation.ParamVerifyUtil;
import com.attiot.railAnaly.goods.entity.ABorrowGoods;
import com.attiot.railAnaly.goods.entity.PointListGoods;
import com.attiot.railAnaly.goods.service.BorrowGoodsService;
import com.attiot.railAnaly.metro.entity.ATaskTableColumns;
import com.attiot.railAnaly.metro.entity.ATaskTableData;
import com.attiot.railAnaly.metro.entity.BorrowGoods;
import com.attiot.railAnaly.point.entity.*;
import com.attiot.railAnaly.point.param.APointPleaseQueryParam;
import com.attiot.railAnaly.point.service.*;
import com.attiot.railAnaly.task.entity.ATaskList;
import com.attiot.railAnaly.util.DateUtil;
import com.attiot.railAnaly.workflow.entity.AWorkflowData;
import com.attiot.railAnaly.workflow.service.WorkflowDataService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "point/forgeinPointPleaseController")
public class ForgeinPointPleaseController {

    @Autowired
    private TSUserService userService;
    @Autowired
    private TSBaseUserService tsBaseUserService;
    @Autowired
    private WorkflowDataService workflowDataService;
    @Autowired
    private ForgeinPointPleaseService forgeinPointPleaseService;
    @Autowired
    private BorrowGoodsService borrowGoodsService;
    @Autowired
    private BorrowListService borrowService;
    @Autowired
    private BorrowListService borrowListService;
    @Autowired
    private BorrowListDetailService borrowListDetailService;
    private static final Logger logger = Logger.getLogger(ForgeinPointPleaseController.class);

    /***
     * 查看审批记录
     * @param request
     * @param response
     */
    @RequestMapping(value = "viewForgeinAudit")
    public void viewForgeinAudit(HttpServletRequest request,HttpServletResponse response){
        AppResult result = new AppResult();
        String workflowId = request.getParameter("id");
        String accessToken = request.getParameter("accessToken");
        try {
            List<Map<String,Object>> borrowList = workflowDataService.getBorrowListByWorkflowId(workflowId);
            AWorkflowData lastedworkflowData = null;
            AWorkflowData currentWorkflowData = workflowDataService.getWorkflowDataById(workflowId);
            Map params = new HashMap();
            params.put("sourceId",currentWorkflowData.getSourceId());
            params.put("wbNo",currentWorkflowData.getWbNo());
            List<AWorkflowData> list = workflowDataService.getWorkflowListBySourceIdAndWbNo(params);
            List nodeList = new ArrayList();
            Map<String,Object> userMap = new HashMap();
            if(null != list && list.size()>0) {//封装每个节点的审批人，审批者，审批时间
                String userIds = "";
                StringBuilder userIds2 = new StringBuilder("");
                for(AWorkflowData record:list) {
                    if(null != record.getCreator() && record.getCreator().length()>0 && userIds2.indexOf(record.getCreator())<0) {
                        userIds2.append(",").append(record.getCreator());
                    }
                    if(null != record.getModifor() && record.getModifor().length()>0 && userIds2.indexOf(record.getModifor())<0) {
                        userIds2.append(",").append(record.getModifor());
                    }
                    if(null != record.getAuditors() && record.getAuditors().length()>0) {
                        String[] auditarray = record.getAuditors().split(",");
                        for(int i=0;i<auditarray.length;i++) {
                            userIds2.append(",").append(auditarray[i]);
                        }
                    }
                }
                userIds = userIds2.toString();
                List<Map<String ,Object>> userList = null;
                if(StringUtils.isNotBlank(userIds) && StringUtils.isNotEmpty(userIds)){
                    userList = userService.getTSBaseUserListByUserIds(userIds.substring(1));
                }
                if(null == userList || userList.size()<=0) userList = new ArrayList();

                for(Map<String ,Object> record:userList) {
                    userMap.put(String.valueOf(record.get("id")),String.valueOf(record.get("realname")));
                }
                for(AWorkflowData record:list) {

                    String nodeCode = record.getNodeCode();
                    JSONObject node = new JSONObject();
                    node.put("id",record.getId());
                    node.put("nodeCode",record.getNodeCode());
                    node.put("nodeName",record.getNodeName());
                    String creator = String.valueOf(userMap.get(record.getCreator()));
                    node.put("creatorname",creator);
                    node.put("creator",record.getCreator());
                    node.put("createtime", DateUtils.date2Str(record.getCreatetime(),DateUtils.datetimeFormat));
                    String modifor = String.valueOf(userMap.get(record.getModifor()));
                    node.put("modiforname",modifor);
                    node.put("modifor",record.getModifor());
                    node.put("modifytime", DateUtils.date2Str(record.getModifytime(),DateUtils.datetimeFormat));
                    node.put("runState",record.getRunState());
                    node.put("auditState",record.getAuditState());
                    node.put("auditResults",record.getAuditResults());
                    node.put("auditors",record.getAuditors());
                    StringBuilder auditornames = new StringBuilder("");
                    String[] auditarray = record.getAuditors().split(",");
                    for(int i=0;i<auditarray.length;i++) {
                        String auditor = null != userMap.get(auditarray[i])?String.valueOf(userMap.get(auditarray[i])):"";
                        if(auditornames.length()>0) {
                            auditornames.append(",");
                        }
                        auditornames.append(auditor);
                    }
                    node.put("auditornames",auditornames.toString());
                    JSONObject data = new JSONObject();
                    if(record.getDataValue()!=null && record.getDataValue().length()>0){
                        data = JSONObject.parseObject(record.getDataValue());
                        if(borrowList.size() > 0 ){
                            Integer borrowState = (Integer) borrowList.get(0).get("borrow_state");
                            if(borrowState == 5){
                                data.put("brands", "");
                            }
                        }
                    }
                    node.put("data", data.toString());

                    if(1==record.getRunState() || record.getNodeType()==3) {
                        lastedworkflowData = record;
                    }
                    node.put("brandInfo",new JSONArray());//挂牌信息
                    if("401104".equals(record.getNodeCode()) && record.getRunState()==1) {//配合人员确认
                        APointPleaseForgein pointPleaseForgein = forgeinPointPleaseService.getById(record.getSourceId());
                        String trainNo = pointPleaseForgein.getTrainNo();
                        List<PointListGoods> goodsList = forgeinPointPleaseService.getPointListGoodsByTrainNo(trainNo);
                        if(null != goodsList && goodsList.size()>0) {
                            JSONArray brandInfo = new JSONArray();
                            for(PointListGoods pointListGoods:goodsList) {
                                JSONObject json = new JSONObject();
                                if(null == pointListGoods.getCreator() || pointListGoods.getCreator().length()<1) {
                                    json.put("info","列车["+pointListGoods.getTrainNo()+"]有挂牌["+pointListGoods.getGoodsName()+"]["+pointListGoods.getGoodsCode()+"],负责人为调度,请联系调度指派;");
                                }else {
                                    json.put("info","列车["+pointListGoods.getTrainNo()+"]有挂牌["+pointListGoods.getGoodsName()+"]["+pointListGoods.getGoodsCode()+"],负责人["+pointListGoods.getCreator()+"];");
                                }
                                brandInfo.add(json);
                            }
                            node.put("brandInfo",brandInfo);
                        }
                    }
                    nodeList.add(node);
                }
            }

            Map jsonMap = new HashMap();
            if(borrowList.size() > 0 ){
                Integer borrowState = (Integer) borrowList.get(0).get("borrow_state");
                if(borrowState == 5){
                    jsonMap.put("spjl", false);
                }
            }

            jsonMap.put("node",nodeList);
            jsonMap.put("workflowId", workflowId);
            jsonMap.put("processCode", lastedworkflowData.getProcessCode());
            jsonMap.put("nodeCode",lastedworkflowData.getNodeCode());
            jsonMap.put("runState",lastedworkflowData.getRunState());
            jsonMap.put("auditFlag",true);//判断是否可以销点
            //外单位销点或外分部销点
            if("4012".equals(lastedworkflowData.getProcessCode()) || "4014".equals(lastedworkflowData.getProcessCode())) {
                //销点前要判断工具是否已还
                List<ABorrowList> borrowlists = borrowService.getByPointId(lastedworkflowData.getSourceId());
                if(null!=borrowlists && borrowlists.size()>0){
                    int count = 0;
                    for(int k=0;k<borrowlists.size();k++){
                        ABorrowList bor = borrowlists.get(k);
                        if(bor.getBorrowState() == 6 || bor.getBorrowState() == 5){//已还或借用失败
                            count++;
                        }
                    }
                    //有工具未还
                    if(count != borrowlists.size()){
                        jsonMap.put("auditFlag",false);
                        jsonMap.put("goods",getPointMap(lastedworkflowData.getSourceId()));
                    }
                }
            }

            StringBuilder auditornames = new StringBuilder("");
            String[] auditarray = lastedworkflowData.getAuditors().split(",");
            for(int i=0;i<auditarray.length;i++) {
                String auditor = String.valueOf(userMap.get(auditarray[i]));
                if(auditornames.length()>0) {
                    auditornames.append(",");
                }
                auditornames.append(auditor);
            }


            jsonMap.put("auditors",auditornames.toString());
            jsonMap.put("auditState",lastedworkflowData.getAuditState());
            jsonMap.put("auditResults",lastedworkflowData.getAuditResults().length()<1?lastedworkflowData.getAuditResults():' ');
            List lst = new ArrayList();
            lst.add(jsonMap);
//            logger.info("viewForgeinAudit(jsonMap):"+jsonMap);
            result.setDataList(lst);
            result.writer(request,response);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMsg("加载数据失败");
            log.error("系统内部异常", e);
        }
    }

    private List<ABorrowListDetail> getPointMap(String ppointId) {
        Map<String,Object> resultMap = new HashMap();

        StringBuilder userIds = new StringBuilder("");

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
        List<TSBaseUser> userList = tsBaseUserService.getByIds(userIds.toString());
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

        return goodsList;
    }

    @RequestMapping(value = "riskTable")
    public void riskTable(HttpServletRequest request,HttpServletResponse response){
        AppResult result = new AppResult();
        String ppointid = request.getParameter("ppointid");
        String tablecode = request.getParameter("tablecode");
        String accessToken = request.getParameter("accessToken");
        try {
            List<ATaskTableColumns> columnList = forgeinPointPleaseService.getTableColumnsByTableCode(tablecode);
            List<ATaskTableData> dataList = forgeinPointPleaseService.getTableDataByPPointId(ppointid);
            if( dataList.size()<=0) {
                dataList = new ArrayList();
            }
            Map dataMap = new HashMap();
            for(ATaskTableData record:dataList) {
                dataMap.put(record.getColCode(),null != record.getColValue()?record.getColValue():"");
            }
            Map jsonMap = new HashMap();
            jsonMap.put("columns",columnList);
            jsonMap.put("data",dataMap);
            List lst = new ArrayList();
            lst.add(jsonMap);
            result.setDataList(lst);
            result.writer(request,response);
        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMsg("加载数据失败");
            log.error("系统内部异常", e);
        }
    }

    @RequestMapping(value = "submitWorkflow")
    public void submitWorkflow(HttpServletRequest request,HttpServletResponse response) {
        AppResult result = new AppResult();
        String json = request.getParameter("data");
        if(json == null) {
            result.setSuccess(false);
            result.setMsg("参数不能为空");
            result.writer(request,response);
            return ;
        }
        ParamVerifyUtil.verifyNotBlank(json);
//        Map _map =  new Gson().fromJson(json,Map.class);
        Map _map = JSON.parseObject(json,Map.class);
        String refnum="";
        try {
            logger.info("ForgeinPointPleaseController_submitWorkflow(map):"+_map);
            String accessToken = String.valueOf(_map.get("accessToken"));
            boolean isdanger = CommonUtils.isDanger(accessToken+"_PointPleaseController_submitWorkflow");
            if(isdanger) {
                result.setSuccess(false);
                result.setMsg("操作太频繁,请稍等...");
                result.writer(request,response);
                return ;
            }

            refnum = String.valueOf(_map.get("workflowId"));
            if(null == refnum || "null".equals(refnum) || refnum.length()<1) {
                result.setSuccess(false);
                result.setMsg("提交参数无效,请退出重新登陆.");
                result.writer(request,response);
                return;
            }
            logger.info("submitWorkflow(refnum):"+refnum);
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
            forgeinPointPleaseService.submitWorkflow(_map);
            CommonUtils.setRefnum(refnum,2);//提交成功
            List lst = new ArrayList();
            result.setDataList(lst);
            result.setMsg("success");
            result.setRtn(1);
            result.writer(request,response);
        }catch(IllegalArgumentException e) {
            CommonUtils.setRefnum(refnum,0);//提交失败
            result.setMsg("审批提交失败，请联系管理员.");
            result.setRtn(0);
            log.error("系统内部异常", e);
        }
    }
}