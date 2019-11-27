/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.APerformanceStatTeammodel;
import com.attiot.railAnaly.metro.param.APerformanceStatTeammodelQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 年度班组各模块分数汇总
 *
 * @author attiot
 * 2019-03-11 13:50:59
 */
@Repository
public interface APerformanceStatTeammodelDao {
    /**
     * 新增
     *
     * @param aPerformanceStatTeammodel 参数
     *
     * @author attiot
     * 2019-03-11 13:50:59
     */
    void insert(APerformanceStatTeammodel aPerformanceStatTeammodel);

    /**
     * 修改
     *
     * @param aPerformanceStatTeammodel 参数
     *
     * @author attiot
     * 2019-03-11 13:50:59
     */
    void update(APerformanceStatTeammodel aPerformanceStatTeammodel);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-11 13:50:59
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-11 13:50:59
     */
    List<APerformanceStatTeammodel> query(APerformanceStatTeammodelQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-11 13:50:59
     */
    APerformanceStatTeammodel getByParam(APerformanceStatTeammodelQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-11 13:50:59
     */
    long queryCount(APerformanceStatTeammodelQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-11 13:50:59
     */
    APerformanceStatTeammodel getById(String id);

    long queryStatTeamCount(APerformanceStatTeammodelQueryParam param);

    List<APerformanceStatTeammodel> queryStatTeam(APerformanceStatTeammodelQueryParam param);
}
