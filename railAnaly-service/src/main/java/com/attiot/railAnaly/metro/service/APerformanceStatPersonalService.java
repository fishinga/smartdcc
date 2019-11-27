/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.dao.APerformanceStatPersonalDao;
import com.attiot.railAnaly.metro.entity.APerformanceStatPersonal;
import com.attiot.railAnaly.metro.param.APerformanceStatPersonalQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 员工月度绩效评分汇总
 *
 * @author attiot
 * 2019-03-01 10:50:08
 */
@Service
public class APerformanceStatPersonalService{

    @Autowired
    private APerformanceStatPersonalDao aPerformanceStatPersonalDao;

    /**
     * 新增
     *
     * @param aPerformanceStatPersonal 参数
     *
     * @author attiot
     * 2019-03-01 10:50:08
     */
    public void create(APerformanceStatPersonal aPerformanceStatPersonal){
        aPerformanceStatPersonalDao.insert(aPerformanceStatPersonal);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-01 10:50:08
     */
    public void delete(String id){
        aPerformanceStatPersonalDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
     public APerformanceStatPersonal getById(String id){
        return  aPerformanceStatPersonalDao.getById(id);
     }


    /**
     * 修改
     *
     * @param aPerformanceStatPersonal 参数
     *
     * @author attiot
     * 2019-03-01 10:50:08
     */
    public void update(APerformanceStatPersonal aPerformanceStatPersonal){
        aPerformanceStatPersonalDao.update(aPerformanceStatPersonal);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-01 10:50:08
     */
    public Page<APerformanceStatPersonal> query(APerformanceStatPersonalQueryParam param){
        Page<APerformanceStatPersonal> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceStatPersonalDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceStatPersonalDao.query(param));
        }
        return page;
    }

}
