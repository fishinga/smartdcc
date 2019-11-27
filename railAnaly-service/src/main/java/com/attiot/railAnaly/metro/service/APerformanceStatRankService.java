/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.dao.APerformanceStatRankDao;
import com.attiot.railAnaly.metro.entity.APerformanceStatRank;
import com.attiot.railAnaly.metro.param.APerformanceStatRankQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 年度班组名次汇总
 *
 * @author attiot
 * 2019-03-11 10:33:44
 */
@Service
public class APerformanceStatRankService{

    @Autowired
    private APerformanceStatRankDao aPerformanceStatRankDao;

    /**
     * 新增
     *
     * @param aPerformanceStatRank 参数
     *
     * @author attiot
     * 2019-03-11 10:33:44
     */
    public void create(APerformanceStatRank aPerformanceStatRank){
        aPerformanceStatRankDao.insert(aPerformanceStatRank);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-11 10:33:44
     */
    public void delete(String id){
        aPerformanceStatRankDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
     public APerformanceStatRank getById(String id){
        return  aPerformanceStatRankDao.getById(id);
     }


    /**
     * 修改
     *
     * @param aPerformanceStatRank 参数
     *
     * @author attiot
     * 2019-03-11 10:33:44
     */
    public void update(APerformanceStatRank aPerformanceStatRank){
        aPerformanceStatRankDao.update(aPerformanceStatRank);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-11 10:33:44
     */
    public Page<APerformanceStatRank> query(APerformanceStatRankQueryParam param){
        Page<APerformanceStatRank> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceStatRankDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceStatRankDao.query(param));
        }
        return page;
    }

    public Page<APerformanceStatRank> queryRank(APerformanceStatRankQueryParam param) {
        Page<APerformanceStatRank> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceStatRankDao.queryRankCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceStatRankDao.queryRank(param));
        }
        return page;
    }

}
