package com.attiot.railAnaly.goods.controller;


import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.service.TSUserService;
import com.attiot.railAnaly.category.entity.ABorrowCategory;
import com.attiot.railAnaly.category.param.ABorrowCategoryQueryParam;
import com.attiot.railAnaly.category.service.BorrowCategoryService;
import com.attiot.railAnaly.common.ConstantValue;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.goods.entity.ABorrowGoods;
import com.attiot.railAnaly.goods.param.ABorrowGoodsQueryParam;
import com.attiot.railAnaly.goods.service.BorrowGoodsService;
import com.attiot.railAnaly.goods.service.PointListGoodsService;
import com.attiot.railAnaly.point.service.ForgeinPointPleaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
 * 物品信息和物品分类接口
 */
@RestController
@Slf4j
@RequestMapping(value = "goods/GoodsController")
public class GoodsController {

    @Autowired
    private BorrowCategoryService categoryService;

    @Autowired
    private BorrowGoodsService goodsService;

    @Autowired
    private PointListGoodsService pointListGoodsService;
    @Autowired
    private ForgeinPointPleaseService forgeinPointPleaseService;
    @Autowired
    private TSUserService userService;

    /***
     * 获取物品信息和物品分类
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getGoods")
    public void getGoods(HttpServletRequest request , HttpServletResponse response){
        AppResult result = new AppResult();
        try{
            /** 加载物品分类信息*/
            ABorrowCategoryQueryParam categoryQueryParam = new ABorrowCategoryQueryParam();
            categoryQueryParam.setLimit(-1);
            Page<ABorrowCategory> categoryPage = categoryService.query(categoryQueryParam);

            /***加载物品信息*****/
            ABorrowGoodsQueryParam goodsQueryParam = new ABorrowGoodsQueryParam();
            goodsQueryParam.setLimit(-1);
            goodsQueryParam.setState(1);//库内
            Page<ABorrowGoods> goodsPage = goodsService.query(goodsQueryParam);

            HashMap<String,Object> _tempMap = new HashMap<>();
            _tempMap.put("category",categoryPage);
            _tempMap.put("goods",goodsPage);
            List lst = new ArrayList();
            lst.add(_tempMap);
            result.setDataList(lst);
            result.setSuccess(true);
        }catch (IllegalArgumentException e){
            log.error("系统内部异常", e);
            result.setSuccess(false);
            result.setMsg("获取物品信息和物品分类出错了");
        }
        result.writer(request,response);
    }

    /**
     * 获取牌所在的车辆
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getGoodsCarNo")
    public void getGoodsCarNo(HttpServletRequest request, HttpServletResponse response){
        AppResult result = new AppResult();
        String category = request.getParameter("category");
        String trainNo = request.getParameter("trainNo");
        Map map = new HashMap();
        Map map2 = new HashMap();
        map.put("category",category);
        map.put("trainNo",trainNo);
        try {
            List<Map<String,Object>> list = goodsService.getGoodsCarNo(map);
            for(int z = 0;z<list.size();z++){
                String userName = (String) list.get(z).get("userName");
                if(StringUtils.isNotBlank(userName)){
                    if(userName.contains(",")){
                        List<Map<String ,Object>> userList = userService.getTSBaseUserListByUserIds(userName);
                        StringBuilder names = new StringBuilder("");
                        for(int k=0;k<userList.size();k++){
                            names.append(userList.get(k).get("realname")).append(",");
                        }
                        names = new StringBuilder(names.substring(0,names.length()-1));
                        list.get(z).put("userName",names.toString());
                    }
                }
            }
            List<Map<String,Object>> list2 = goodsService.getContentsCarNo(map);
            map2.put("brands",list);
            if(null != list2 && list2.size()>0) {
                for(int i=0;i<list2.size();i++) {
                    Map<String,Object> pointMap = list2.get(i);
                    String ppointId = pointMap.get("ppointId")!=null?pointMap.get("ppointId")+"":"";
                    List<TSBaseUser> userList = forgeinPointPleaseService.getCoWorkerByPointId(ppointId);
                    pointMap.put("coWorkers","");
                    if(null != userList && userList.size()>0) {
                        StringBuilder coWorkers = new StringBuilder("");
                        for(TSBaseUser record:userList) {
                            if(coWorkers.length()>0) {
                                coWorkers.append(",");
                            }
                            coWorkers.append(record.getRealname());
                        }
                        pointMap.put("coWorkers",coWorkers.toString());
                    }
                }
            }
            map2.put("contents",list2);
            List lst =new ArrayList();
            lst.add(map2);
            result.setDataList(lst);
            result.writer(request,response);
        } catch (IllegalArgumentException e) {
            log.error("系统内部异常", e);
            result.setSuccess(false);
            result.setMsg("获取物品信息和车辆编号出错");
        }
    }

    /**
     * 挂牌
     * @param request
     * @param response
     */
    @RequestMapping(value = "/upGoods")
    public void upGoods(HttpServletRequest request, HttpServletResponse response){
        AppResult result = new AppResult();
        String ids = request.getParameter("goodsIds");
        String trainNo = request.getParameter("trainNo");
        String userId = request.getParameter("accessToken");
        try {
            if(ids == null || trainNo == null || userId == null){
                result.setSuccess(false);
                result.setMsg("参数错误！");
                result.writer(request,response);
                return;
            }
            goodsService.saveGoodsToPointListGoods( trainNo, ids, userId);
            result.writer(request,response);
        } catch (IllegalArgumentException e) {
            log.error("系统内部异常", e);
            result.setSuccess(false);
            result.setMsg("挂牌出错！");
        }
    }

    /**
     * 拆牌
     * @param request
     * @param response
     */
    @RequestMapping(value = "/downGoods")
    public void downGoods(HttpServletRequest request, HttpServletResponse response){
        AppResult result = new AppResult();
        String ids = request.getParameter("goodsIds");
        String userId = request.getParameter("accessToken");
        try {
            pointListGoodsService.deleteGoods(ids,userId);
            result.writer(request,response);
        } catch (IllegalArgumentException e) {
            log.error("系统内部异常", e);
            result.setSuccess(false);
            result.setMsg("拆牌出错！");
        }
    }


    /***
     * 刷新
     * @param request
     * @param response
     */
    @RequestMapping(value = "/refreshGoods")
    public void refreshGoods(HttpServletRequest request, HttpServletResponse response){
        AppResult result = new AppResult();

        String userId = request.getParameter("accessToken");
        try {
            pointListGoodsService.deleteRepeatGoods(userId);

            String category = ConstantValue.BORROW_CATEGORY_BRAND;
            Map map  = new HashMap();
            map.put("userId",userId);
            map.put("category",category);
            List<Map<String,Object>> list = goodsService.getGoodsByUser(map);
            result.setDataList(list);

            result.writer(request,response);
        } catch (IllegalArgumentException e) {
            log.error("系统内部异常", e);
            result.setSuccess(false);
            result.setMsg("刷新挂牌数据失败.");
        }
    }

    /**
     * 当前登录用户获取警示牌
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getGoodsByUser")
    public void getGoodsByUser(HttpServletRequest request, HttpServletResponse response){
        AppResult result = new AppResult();
        String userId = request.getParameter("accessToken");
        String category = ConstantValue.BORROW_CATEGORY_BRAND;
        Map map  = new HashMap();
        map.put("userId",userId);
        map.put("category",category);
        try {
            List<Map<String,Object>> list = goodsService.getGoodsByUser(map);
            result.setDataList(list);
            result.writer(request,response);
        } catch (IllegalArgumentException e) {
            log.error("系统内部异常", e);
            result.setSuccess(false);
            result.setMsg("当前登录用户获取警示牌出错");
        }
    }

}
