/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.dao.APerformanceStatTeamDao;
import com.attiot.railAnaly.metro.entity.APerformanceStatTeam;
import com.attiot.railAnaly.metro.param.APerformanceStatTeamQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 员工月度绩效评分汇总
 *
 * @author attiot
 * 2019-03-07 14:29:18
 */
@Service
public class APerformanceStatTeamService{

    @Autowired
    private APerformanceStatTeamDao aPerformanceStatTeamDao;

    /**
     * 新增
     *
     * @param aPerformanceStatTeam 参数
     *
     * @author attiot
     * 2019-03-07 14:29:18
     */
    public void create(APerformanceStatTeam aPerformanceStatTeam){
        aPerformanceStatTeamDao.insert(aPerformanceStatTeam);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-07 14:29:18
     */
    public void delete(String id){
        aPerformanceStatTeamDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
     public APerformanceStatTeam getById(String id){
        return  aPerformanceStatTeamDao.getById(id);
     }


    /**
     * 修改
     *
     * @param aPerformanceStatTeam 参数
     *
     * @author attiot
     * 2019-03-07 14:29:18
     */
    public void update(APerformanceStatTeam aPerformanceStatTeam){
        aPerformanceStatTeamDao.update(aPerformanceStatTeam);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-07 14:29:18
     */
    public Page<APerformanceStatTeam> query(APerformanceStatTeamQueryParam param){
        Page<APerformanceStatTeam> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceStatTeamDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceStatTeamDao.query(param));
        }
        return page;
    }

    public Page<APerformanceStatTeam> queryStatTeam(APerformanceStatTeamQueryParam param) {
        Page<APerformanceStatTeam> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceStatTeamDao.queryStatTeamCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceStatTeamDao.queryStatTeam(param));
        }
        return page;
    }

}
