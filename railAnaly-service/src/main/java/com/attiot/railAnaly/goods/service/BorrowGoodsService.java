package com.attiot.railAnaly.goods.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.goods.dao.ABorrowGoodsDao;
import com.attiot.railAnaly.goods.dao.PointListDao;
import com.attiot.railAnaly.goods.entity.ABorrowGoods;
import com.attiot.railAnaly.goods.entity.PointList;
import com.attiot.railAnaly.goods.param.ABorrowGoodsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;



@Service
public class BorrowGoodsService {

    @Autowired
    private ABorrowGoodsDao aBorrowGoodsDao;
    @Autowired
    private PointListService pointListService;
    @Autowired
    private PointListDao pointListDao;

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-09 11:27:13
     */
    public Page<ABorrowGoods> query(ABorrowGoodsQueryParam param){
        Page<ABorrowGoods> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aBorrowGoodsDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aBorrowGoodsDao.query(param));
        }
        return page;
    }


    public ABorrowGoods getById(String id){
        return  aBorrowGoodsDao.getById(id);
    }

    /**
     * 根据ID，查询相关信息
     * @param ids
     * @return
     */
    public List<ABorrowGoods> getByIds(String[] ids){
        return  aBorrowGoodsDao.getByIds(ids);
    }

    public void editState(ABorrowGoods goods){
        aBorrowGoodsDao.editState(goods);
    }



    /**
     * 根据列车号 获取牌
     * @param map
     * @return
     */
    public List<Map<String,Object>> getGoodsCarNo(Map map){
        List<Map<String,Object>> list = aBorrowGoodsDao.getGoodsCarNo(map);
        return  list;
    }


    public List<Map<String,Object>> getContentsCarNo(Map map){
        List<Map<String,Object>> list = aBorrowGoodsDao.getContentsCarNo(map);
        return  list;
    }


    /**
     * 当前登录用户获取警示牌
     * @param map
     * @return
     */
    public List<Map<String,Object>> getGoodsByUser( Map map ){
        List<Map<String,Object>> list = aBorrowGoodsDao.getGoodsByUser(map);
        return  list;
    }

    public void updateStateByGoodIds(Map params) {
        aBorrowGoodsDao.updateStateByGoodIds(params);
    }

    @Transactional
    public void saveGoodsToPointListGoods(String trainNo,String goodsIds,String userId) {
        PointList pointList = pointListDao.getByTrainNo(trainNo);
        //如果pointList存在，执行update，否则add
        if(null != pointList){
            pointListService.addOrUpdate(pointList,trainNo,goodsIds,userId);
        }else{
            PointList pointList1 = new PointList();
            pointList1.setTrainNo(trainNo);
            pointListService.addOrUpdate(pointList1,trainNo,goodsIds,userId);
        }
    }
}
