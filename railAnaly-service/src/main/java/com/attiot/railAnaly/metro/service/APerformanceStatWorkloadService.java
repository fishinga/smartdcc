/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.dao.APerformanceStatWorkloadDao;
import com.attiot.railAnaly.metro.entity.APerformanceStatWorkload;
import com.attiot.railAnaly.metro.param.APerformanceStatWorkloadQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 月度工作量
 *
 * @author attiot
 * 2019-03-08 15:14:00
 */
@Service
public class APerformanceStatWorkloadService{

    @Autowired
    private APerformanceStatWorkloadDao aPerformanceStatWorkloadDao;

    /**
     * 新增
     *
     * @param aPerformanceStatWorkload 参数
     *
     * @author attiot
     * 2019-03-08 15:14:00
     */
    public void create(APerformanceStatWorkload aPerformanceStatWorkload){
        aPerformanceStatWorkloadDao.insert(aPerformanceStatWorkload);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-08 15:14:00
     */
    public void delete(String id){
        aPerformanceStatWorkloadDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
     public APerformanceStatWorkload getById(String id){
        return  aPerformanceStatWorkloadDao.getById(id);
     }


    /**
     * 修改
     *
     * @param aPerformanceStatWorkload 参数
     *
     * @author attiot
     * 2019-03-08 15:14:00
     */
    public void update(APerformanceStatWorkload aPerformanceStatWorkload){
        aPerformanceStatWorkloadDao.update(aPerformanceStatWorkload);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-08 15:14:00
     */
    public Page<APerformanceStatWorkload> query(APerformanceStatWorkloadQueryParam param){
        Page<APerformanceStatWorkload> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceStatWorkloadDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceStatWorkloadDao.query(param));
        }
        return page;
    }

}
