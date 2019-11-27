package com.attiot.railAnaly.borrow.controller;


import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.borrow.service.BorrowListDetailService;
import com.attiot.railAnaly.borrow.service.BorrowListService;
import com.attiot.railAnaly.common.CommonUtils;
import com.attiot.railAnaly.common.util.StringUtil;
import com.attiot.railAnaly.foundation.ParamVerifyUtil;
import com.attiot.railAnaly.foundation.exception.AppException;
import com.attiot.railAnaly.foundation.exception.ErrorInfo;
import com.attiot.railAnaly.jpush.service.JPushService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 借用
 */
@RestController
@Slf4j
@RequestMapping(value = "borrow/borrowController")
public class BorrowController {

    @Autowired
    private BorrowListService borrowService;

    @Autowired
    private BorrowListDetailService borrowDetailService;
    @Autowired
    private JPushService jpushService;
    private static final Logger logger = Logger.getLogger(BorrowController.class);

    /***
     * 获取我的借用（借用历史）
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getBorrowHis")
    public void getBorrowHis(HttpServletRequest request , HttpServletResponse response){
        AppResult result = new AppResult();
        try{
            String creator = request.getParameter("borrower");
            if(StringUtil.isNotEmpty(creator)){
                HashMap<String,Object> param = new HashMap<>();
                param.put("borrower",creator);
                List lst = borrowService.getBorrowHis(param);
                result.setDataList(lst);
                result.setSuccess(true);
            }
        }catch (IllegalArgumentException e){
            log.error("系统内部异常", e);
            result.setSuccess(false);
            result.setMsg("获取我的借用（借用历史）查询出错了");
        }
        result.writer(request,response);
    }


    /***
     * 需要归还的借用物品
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getBorrowReturn")
    public void getBorrowReturn(HttpServletRequest request , HttpServletResponse response){
        AppResult result = new AppResult();
        try{
            String borrower=request.getParameter("accessToken");
            String orgId= request.getParameter("departId");
            if(StringUtil.isNotEmpty(borrower)){
                HashMap<String,Object> param = new HashMap<>();
                param.put("borrower",borrower);
                List lst = borrowService.getBorrowReturn(param);
                Map _map = new HashMap();
                _map.put("sendBack",new Gson().toJson(lst));
                List _mapLst = new ArrayList();
                _mapLst.add(_map);
                result.setDataList(_mapLst);
                result.setSuccess(true);
                result.writer(request,response);
            }
        }catch (IllegalArgumentException e){
            log.error("系统内部异常", e);
            result.setSuccess(false);
            result.setMsg("需要归还的借用物品，查询出错了");
        }
    }

    /***
     * 保存借用物品信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "/saveBorrow")
    public void saveBorrow(HttpServletRequest request , HttpServletResponse response){
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
            Map _borrowMap =  new Gson().fromJson(json,Map.class);


            //--控制访问开始
            String accessToken = String.valueOf(_borrowMap.get("accessToken"));
            if(accessToken == null){
                result.setSuccess(false);
                result.setMsg("参数缺失");
                result.writer(request,response);
                return ;
            }
            boolean isdanger = CommonUtils.isDanger(accessToken+"_BorrowController_saveBorrow");
            if(isdanger) {
                result.setSuccess(false);
                result.setMsg("操作太频繁,请稍等...");
                result.writer(request,response);
                return ;
            }
            refnum = String.valueOf(_borrowMap.get("refnum"));
            logger.info("saveBorrow(refnum):"+refnum);
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
            //--控制访问结束

            String goodId = String.valueOf(_borrowMap.get("goodId"));
            String[] ids = goodId.split(",");
            logger.info("saveBorrow(map):"+_borrowMap);
            if(ids.length>0){
                String borrower= String.valueOf(_borrowMap.get("accessToken"));
                String orgId= String.valueOf(_borrowMap.get("departId"));
                String remark = String.valueOf(_borrowMap.get("remark"));
                String pointId = String.valueOf(_borrowMap.get("pointId"));
                String jobContent = String.valueOf(_borrowMap.get("jobContent"));
                CommonUtils.setRefnum(refnum,1);//提交中
                borrowService.saveWithTransaction(ids,borrower,orgId,pointId,jobContent,remark);
                CommonUtils.setRefnum(refnum,2);//提交中
                logger.info("saveBorrow(refnum):"+refnum+"，借用成功.");
            }
        }catch (IllegalArgumentException e){
            CommonUtils.setRefnum(refnum,0);//提交失败
            result.setSuccess(false);
            result.setMsg("保存借用物品信息出错了");
            log.error("系统内部异常", e);
        }
        result.writer(request,response);
    }

    /***
     * 保存归还物品信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "/saveSendBack")
    public void saveSendBack(HttpServletRequest request , HttpServletResponse response){
        AppResult result = new AppResult();
        try{
            String json = request.getParameter("data");
            if(json != null){
            ParamVerifyUtil.verifyNotBlank(json);
            Map _temp  =  new Gson().fromJson(json,Map.class);
            logger.info("saveSendBack(map):"+_temp);
            String borrowGoodsId = String.valueOf(_temp.get("borrowGoodsId"));
            String accessToken = String.valueOf(_temp.get("accessToken"));
            String userId = String.valueOf(_temp.get("userId"));//交接人
            String type = String.valueOf(_temp.get("type"));//0-归还，1-转借，2-交接给个人，3-交接给调度
            if(StringUtil.isNotEmpty(borrowGoodsId) && StringUtil.isNotEmpty(accessToken)){
                borrowService.saveGiveBack(borrowGoodsId.split(","),accessToken,type,userId,"");
            }
            }else {
                throw new AppException(ErrorInfo.PARAM_MISS);
            }
        }catch (IllegalArgumentException e){
            result.setSuccess(false);
            result.setMsg("保存归还物品信息出错了");
            log.error("系统内部异常", e);
        }
        result.writer(request,response);
    }

}
