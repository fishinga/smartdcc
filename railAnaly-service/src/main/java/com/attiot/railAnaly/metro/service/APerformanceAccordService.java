/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.dao.APerformanceAccordDao;
import com.attiot.railAnaly.metro.entity.APerformanceAccord;
import com.attiot.railAnaly.metro.param.APerformanceAccordQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 考评依据
 *
 * @author attiot
 * 2019-02-12 16:20:14
 */
@Service
public class APerformanceAccordService{

    @Autowired
    private APerformanceAccordDao aPerformanceAccordDao;

    /**
     * 新增
     *
     * @param aPerformanceAccord 参数
     *
     * @author attiot
     * 2019-02-12 16:20:14
     */
    public void create(APerformanceAccord aPerformanceAccord){
        aPerformanceAccordDao.insert(aPerformanceAccord);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-12 16:20:14
     */
    public void delete(String id){
        aPerformanceAccordDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
     public APerformanceAccord getById(String id){
        return  aPerformanceAccordDao.getById(id);
     }


    /**
     * 修改
     *
     * @param aPerformanceAccord 参数
     *
     * @author attiot
     * 2019-02-12 16:20:14
     */
    public void update(APerformanceAccord aPerformanceAccord){
        aPerformanceAccordDao.update(aPerformanceAccord);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-12 16:20:14
     */
    public Page<APerformanceAccord> query(APerformanceAccordQueryParam param){
        Page<APerformanceAccord> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceAccordDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceAccordDao.query(param));
        }
        return page;
    }

}
