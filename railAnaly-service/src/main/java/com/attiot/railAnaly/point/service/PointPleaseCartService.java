package com.attiot.railAnaly.point.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.point.dao.APointPleaseCartDao;
import com.attiot.railAnaly.point.entity.APointPleaseCart;
import com.attiot.railAnaly.point.param.APointPleaseCartQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 人工推车
 *
 * @author attiot
 * 2018-04-18 18:13:10
 */
@Service
public class PointPleaseCartService {
    @Autowired
    private APointPleaseCartDao aPointPleaseCartDao;

    /**
     * 新增
     *
     * @param aPointPleaseCart 参数
     *
     * @author attiot
     * 2018-04-18 18:13:10
     */
    public void create(APointPleaseCart aPointPleaseCart){
        aPointPleaseCartDao.insert(aPointPleaseCart);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-18 18:13:10
     */
    public void delete(String id){
        aPointPleaseCartDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
    public APointPleaseCart getById(String id){
        return  aPointPleaseCartDao.getById(id);
    }

    public APointPleaseCart getByPointId(String pointId){
        return  aPointPleaseCartDao.getByPointId(pointId);
    }


    /**
     * 修改
     *
     * @param aPointPleaseCart 参数
     *
     * @author attiot
     * 2018-04-18 18:13:10
     */
    public void update(APointPleaseCart aPointPleaseCart){
        aPointPleaseCartDao.update(aPointPleaseCart);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-18 18:13:10
     */
    public Page<APointPleaseCart> query(APointPleaseCartQueryParam param){
        Page<APointPleaseCart> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPointPleaseCartDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPointPleaseCartDao.query(param));
        }
        return page;
    }

}
