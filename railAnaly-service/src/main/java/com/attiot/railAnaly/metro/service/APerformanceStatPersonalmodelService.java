/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.dao.APerformanceStatPersonalmodelDao;
import com.attiot.railAnaly.metro.entity.APerformanceStatPersonalmodel;
import com.attiot.railAnaly.metro.param.APerformanceStatPersonalmodelQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 年度员工各模块分数汇总
 *
 * @author attiot
 * 2019-03-11 11:15:55
 */
@Service
public class APerformanceStatPersonalmodelService{

    @Autowired
    private APerformanceStatPersonalmodelDao aPerformanceStatPersonalmodelDao;

    /**
     * 新增
     *
     * @param aPerformanceStatPersonalmodel 参数
     *
     * @author attiot
     * 2019-03-11 11:15:55
     */
    public void create(APerformanceStatPersonalmodel aPerformanceStatPersonalmodel){
        aPerformanceStatPersonalmodelDao.insert(aPerformanceStatPersonalmodel);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-11 11:15:55
     */
    public void delete(String id){
        aPerformanceStatPersonalmodelDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
     public APerformanceStatPersonalmodel getById(String id){
        return  aPerformanceStatPersonalmodelDao.getById(id);
     }


    /**
     * 修改
     *
     * @param aPerformanceStatPersonalmodel 参数
     *
     * @author attiot
     * 2019-03-11 11:15:55
     */
    public void update(APerformanceStatPersonalmodel aPerformanceStatPersonalmodel){
        aPerformanceStatPersonalmodelDao.update(aPerformanceStatPersonalmodel);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-11 11:15:55
     */
    public Page<APerformanceStatPersonalmodel> query(APerformanceStatPersonalmodelQueryParam param){
        Page<APerformanceStatPersonalmodel> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceStatPersonalmodelDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceStatPersonalmodelDao.query(param));
        }
        return page;
    }

}
