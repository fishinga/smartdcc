/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.dao.APerformanceActualhoursDao;
import com.attiot.railAnaly.metro.entity.APerformanceActualhours;
import com.attiot.railAnaly.metro.param.APerformanceActualhoursQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 月度实际工时
 *
 * @author attiot
 * 2019-02-25 16:39:43
 */
@Service
public class APerformanceActualhoursService{

    @Autowired
    private APerformanceActualhoursDao aPerformanceActualhoursDao;

    /**
     * 新增
     *
     * @param aPerformanceActualhours 参数
     *
     * @author attiot
     * 2019-02-25 16:39:43
     */
    public void create(APerformanceActualhours aPerformanceActualhours){
        aPerformanceActualhoursDao.insert(aPerformanceActualhours);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-25 16:39:43
     */
    public void delete(String id){
        aPerformanceActualhoursDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
     public APerformanceActualhours getById(String id){
        return  aPerformanceActualhoursDao.getById(id);
     }


    /**
     * 修改
     *
     * @param aPerformanceActualhours 参数
     *
     * @author attiot
     * 2019-02-25 16:39:43
     */
    public void update(APerformanceActualhours aPerformanceActualhours){
        aPerformanceActualhoursDao.update(aPerformanceActualhours);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-25 16:39:43
     */
    public Page<APerformanceActualhours> query(APerformanceActualhoursQueryParam param){
        Page<APerformanceActualhours> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceActualhoursDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceActualhoursDao.query(param));
        }
        return page;
    }

    public APerformanceActualhours getByParam(APerformanceActualhoursQueryParam param) {
        return aPerformanceActualhoursDao.getByParam(param);
    }

}
