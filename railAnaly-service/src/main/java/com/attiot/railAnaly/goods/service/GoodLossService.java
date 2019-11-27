package com.attiot.railAnaly.goods.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.goods.dao.ABorrowGoodLossDao;
import com.attiot.railAnaly.goods.entity.ABorrowGoodLoss;
import com.attiot.railAnaly.goods.param.ABorrowGoodLossQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class GoodLossService {

    @Autowired
    private ABorrowGoodLossDao aBorrowGoodLossDao;

    /**
     * 新增
     *
     * @param aBorrowGoodLoss 参数
     *
     * @author attiot
     * 2018-05-02 20:21:58
     */
    public void create(ABorrowGoodLoss aBorrowGoodLoss){
        aBorrowGoodLossDao.insert(aBorrowGoodLoss);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-05-02 20:21:58
     */
    public void delete(String id){
        aBorrowGoodLossDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
    public ABorrowGoodLoss getById(String id){
        return  aBorrowGoodLossDao.getById(id);
    }


    /**
     * 修改
     *
     * @param aBorrowGoodLoss 参数
     *
     * @author attiot
     * 2018-05-02 20:21:58
     */
    public void update(ABorrowGoodLoss aBorrowGoodLoss){
        aBorrowGoodLossDao.update(aBorrowGoodLoss);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-05-02 20:21:58
     */
    public Page<ABorrowGoodLoss> query(ABorrowGoodLossQueryParam param){
        Page<ABorrowGoodLoss> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aBorrowGoodLossDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aBorrowGoodLossDao.query(param));
        }
        return page;
    }

}
