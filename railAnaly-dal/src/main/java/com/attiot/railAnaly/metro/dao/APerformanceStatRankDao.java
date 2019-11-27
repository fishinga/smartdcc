/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.APerformanceStatRank;
import com.attiot.railAnaly.metro.param.APerformanceStatRankQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 年度班组名次汇总
 *
 * @author attiot
 * 2019-03-11 10:33:44
 */
@Repository
public interface APerformanceStatRankDao {
    /**
     * 新增
     *
     * @param aPerformanceStatRank 参数
     *
     * @author attiot
     * 2019-03-11 10:33:44
     */
    void insert(APerformanceStatRank aPerformanceStatRank);

    /**
     * 修改
     *
     * @param aPerformanceStatRank 参数
     *
     * @author attiot
     * 2019-03-11 10:33:44
     */
    void update(APerformanceStatRank aPerformanceStatRank);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-11 10:33:44
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-11 10:33:44
     */
    List<APerformanceStatRank> query(APerformanceStatRankQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-11 10:33:44
     */
    APerformanceStatRank getByParam(APerformanceStatRankQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-11 10:33:44
     */
    long queryCount(APerformanceStatRankQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-11 10:33:44
     */
    APerformanceStatRank getById(String id);

    long queryRankCount(APerformanceStatRankQueryParam param);

    List<APerformanceStatRank> queryRank(APerformanceStatRankQueryParam param);
}
