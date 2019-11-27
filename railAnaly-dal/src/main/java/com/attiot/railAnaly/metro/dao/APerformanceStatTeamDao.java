/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.APerformanceStatTeam;
import com.attiot.railAnaly.metro.param.APerformanceStatTeamQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 员工月度绩效评分汇总
 *
 * @author attiot
 * 2019-03-07 14:29:18
 */
@Repository
public interface APerformanceStatTeamDao {
    /**
     * 新增
     *
     * @param aPerformanceStatTeam 参数
     *
     * @author attiot
     * 2019-03-07 14:29:18
     */
    void insert(APerformanceStatTeam aPerformanceStatTeam);

    /**
     * 修改
     *
     * @param aPerformanceStatTeam 参数
     *
     * @author attiot
     * 2019-03-07 14:29:18
     */
    void update(APerformanceStatTeam aPerformanceStatTeam);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-07 14:29:18
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-07 14:29:18
     */
    List<APerformanceStatTeam> query(APerformanceStatTeamQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-07 14:29:18
     */
    APerformanceStatTeam getByParam(APerformanceStatTeamQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-07 14:29:18
     */
    long queryCount(APerformanceStatTeamQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-07 14:29:18
     */
    APerformanceStatTeam getById(String id);

    long queryStatTeamCount(APerformanceStatTeamQueryParam param);

    List<APerformanceStatTeam> queryStatTeam(APerformanceStatTeamQueryParam param);
}
