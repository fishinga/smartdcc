/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.service;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.dao.APerformanceStatTeammodelDao;
import com.attiot.railAnaly.metro.entity.APerformanceStatTeammodel;
import com.attiot.railAnaly.metro.param.APerformanceStatTeammodelQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 年度班组各模块分数汇总
 *
 * @author attiot
 * 2019-03-11 13:50:59
 */
@Service
public class APerformanceStatTeammodelService {

    @Autowired
    private APerformanceStatTeammodelDao aPerformanceStatTeammodelDao;

    /**
     * 新增
     *
     * @param aPerformanceStatTeammodel 参数
     *
     * @author attiot
     * 2019-03-11 13:50:59
     */
    public void create(APerformanceStatTeammodel aPerformanceStatTeammodel){
        aPerformanceStatTeammodelDao.insert(aPerformanceStatTeammodel);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-11 13:50:59
     */
    public void delete(String id){
        aPerformanceStatTeammodelDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
     public APerformanceStatTeammodel getById(String id){
        return  aPerformanceStatTeammodelDao.getById(id);
     }


    /**
     * 修改
     *
     * @param aPerformanceStatTeammodel 参数
     *
     * @author attiot
     * 2019-03-11 13:50:59
     */
    public void update(APerformanceStatTeammodel aPerformanceStatTeammodel){
        aPerformanceStatTeammodelDao.update(aPerformanceStatTeammodel);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-11 13:50:59
     */
    public Page<APerformanceStatTeammodel> query(APerformanceStatTeammodelQueryParam param){
        Page<APerformanceStatTeammodel> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceStatTeammodelDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceStatTeammodelDao.query(param));
        }
        return page;
    }

    public Page<APerformanceStatTeammodel> queryStatTeam(APerformanceStatTeammodelQueryParam param) {
        Page<APerformanceStatTeammodel> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceStatTeammodelDao.queryStatTeamCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceStatTeammodelDao.queryStatTeam(param));
        }
        return page;
    }

}
