/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.dao.APerformanceBusinessDao;
import com.attiot.railAnaly.metro.entity.APerformanceBusiness;
import com.attiot.railAnaly.metro.param.APerformanceBusinessQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 考评模块配置
 *
 * @author attiot
 * 2019-02-11 13:38:03
 */
@Service
public class APerformanceBusinessService{

    @Autowired
    private APerformanceBusinessDao aPerformanceBusinessDao;

    /**
     * 新增
     *
     * @param aPerformanceBusiness 参数
     *
     * @author attiot
     * 2019-02-11 13:38:03
     */
    public void create(APerformanceBusiness aPerformanceBusiness){
        aPerformanceBusinessDao.insert(aPerformanceBusiness);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-11 13:38:03
     */
    public void delete(String id){
        aPerformanceBusinessDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
     public APerformanceBusiness getById(String id){
        return  aPerformanceBusinessDao.getById(id);
     }


    /**
     * 修改
     *
     * @param aPerformanceBusiness 参数
     *
     * @author attiot
     * 2019-02-11 13:38:03
     */
    public void update(APerformanceBusiness aPerformanceBusiness){
        aPerformanceBusinessDao.update(aPerformanceBusiness);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-11 13:38:03
     */
    public Page<APerformanceBusiness> query(APerformanceBusinessQueryParam param){
        Page<APerformanceBusiness> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceBusinessDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceBusinessDao.query(param));
        }
        return page;
    }

    public APerformanceBusiness getByCode(String perfModelCode) {
        APerformanceBusinessQueryParam performanceBusinessQueryParam = new APerformanceBusinessQueryParam();
        performanceBusinessQueryParam.setCode(perfModelCode);
        return  aPerformanceBusinessDao.getByParam(performanceBusinessQueryParam);
    }

}
