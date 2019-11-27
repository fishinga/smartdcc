package com.attiot.railAnaly.borrow.service;

import com.attiot.railAnaly.borrow.dao.ABorrowListDetailDao;
import com.attiot.railAnaly.borrow.entity.ABorrowListDetail;
import com.attiot.railAnaly.borrow.param.ABorrowListDetailQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;


@Service
public class BorrowListDetailService {
    @Autowired
    private ABorrowListDetailDao aBorrowListDetailDao;

    /**
     * 新增
     *
     * @param aBorrowListDetail 参数
     *
     * @author attiot
     * 2018-04-10 19:46:33
     */
    public void create(ABorrowListDetail aBorrowListDetail){
        aBorrowListDetailDao.insert(aBorrowListDetail);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-10 19:46:33
     */
    public void delete(String id){
        aBorrowListDetailDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
    public ABorrowListDetail getById(String id){
        return  aBorrowListDetailDao.getById(id);
    }


    /**
     * 修改
     *
     * @param aBorrowListDetail 参数
     *
     * @author attiot
     * 2018-04-10 19:46:33
     */
    public void update(ABorrowListDetail aBorrowListDetail){
        aBorrowListDetailDao.update(aBorrowListDetail);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-10 19:46:33
     */
    public Page<ABorrowListDetail> query(ABorrowListDetailQueryParam param){
        Page<ABorrowListDetail> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aBorrowListDetailDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aBorrowListDetailDao.query(param));
        }
        return page;
    }


    /***
     * 根据借用单获取借用明细信息
     * @param borrowIds
     * @return
     */
    public List<ABorrowListDetail> getByBorrowId(String[] borrowIds){
        return  aBorrowListDetailDao.getByBorrowId(borrowIds);
    }


    /***
     *  根据请点ID 查询借用详情
     * @param pointId
     * @return
     */
    public List<ABorrowListDetail> getBorrowByPointId(String pointId){
        return aBorrowListDetailDao.getBorrowByPointId(pointId);
    }

    /***
     * 取需要待还状态的物品
     * @param pointId
     * @return
     */
    public List<ABorrowListDetail> getNeedGivebackBorrowDetailByPointId(String pointId){
        return aBorrowListDetailDao.getNeedGivebackBorrowDetailByPointId(pointId);
    }

    /***
     * 需要归还的借用物品
     * @param param
     * @return
     */
    public List getBorrowReturn(HashMap<String,Object> param){
        return aBorrowListDetailDao.getBorrowReturn(param);
    }


    /***
     * 根据商品ID获取借用明细信息
     * @param goodIds
     * @return
     */
    public List<ABorrowListDetail> getByGoodId(String[] goodIds){
        return  aBorrowListDetailDao.getByGoodId(goodIds);
    }


    /***
     * 根据借用单号查询
     * @param returnNum
     * @return
     */
    public List<ABorrowListDetail> getByReturnNum(String returnNum){
        return  aBorrowListDetailDao.getByReturnNum(returnNum);
    }


}
