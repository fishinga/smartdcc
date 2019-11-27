/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.service;


import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.dao.APerformanceStatYearscoreDao;
import com.attiot.railAnaly.metro.entity.APerformanceStatYearscore;
import com.attiot.railAnaly.metro.param.APerformanceStatYearscoreQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 年度绩效汇总
 *
 * @author attiot
 * 2019-03-08 17:14:48
 */
@Service
public class APerformanceStatYearscoreService{

    @Autowired
    private APerformanceStatYearscoreDao aPerformanceStatYearscoreDao;

    /**
     * 新增
     *
     * @param aPerformanceStatYearscore 参数
     *
     * @author attiot
     * 2019-03-08 17:14:48
     */
    public void create(APerformanceStatYearscore aPerformanceStatYearscore){
        aPerformanceStatYearscoreDao.insert(aPerformanceStatYearscore);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-08 17:14:48
     */
    public void delete(String id){
        aPerformanceStatYearscoreDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
     public APerformanceStatYearscore getById(String id){
        return  aPerformanceStatYearscoreDao.getById(id);
     }


    /**
     * 修改
     *
     * @param aPerformanceStatYearscore 参数
     *
     * @author attiot
     * 2019-03-08 17:14:48
     */
    public void update(APerformanceStatYearscore aPerformanceStatYearscore){
        aPerformanceStatYearscoreDao.update(aPerformanceStatYearscore);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-08 17:14:48
     */
    public Page<APerformanceStatYearscore> query(APerformanceStatYearscoreQueryParam param){
        Page<APerformanceStatYearscore> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceStatYearscoreDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceStatYearscoreDao.query(param));
        }
        return page;
    }

    public Page<APerformanceStatYearscore> queryStat(APerformanceStatYearscoreQueryParam param) {
        Page<APerformanceStatYearscore> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceStatYearscoreDao.queryStatCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceStatYearscoreDao.queryStat(param));
        }
        return page;
    }

}
