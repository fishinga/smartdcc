/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.APerformanceStatYearscore;
import com.attiot.railAnaly.metro.param.APerformanceStatYearscoreQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 年度绩效汇总
 *
 * @author attiot
 * 2019-03-08 17:14:48
 */
@Repository
public interface APerformanceStatYearscoreDao {
    /**
     * 新增
     *
     * @param aPerformanceStatYearscore 参数
     *
     * @author attiot
     * 2019-03-08 17:14:48
     */
    void insert(APerformanceStatYearscore aPerformanceStatYearscore);

    /**
     * 修改
     *
     * @param aPerformanceStatYearscore 参数
     *
     * @author attiot
     * 2019-03-08 17:14:48
     */
    void update(APerformanceStatYearscore aPerformanceStatYearscore);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-08 17:14:48
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-08 17:14:48
     */
    List<APerformanceStatYearscore> query(APerformanceStatYearscoreQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-08 17:14:48
     */
    APerformanceStatYearscore getByParam(APerformanceStatYearscoreQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-08 17:14:48
     */
    long queryCount(APerformanceStatYearscoreQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-08 17:14:48
     */
    APerformanceStatYearscore getById(String id);

    long queryStatCount(APerformanceStatYearscoreQueryParam param);

    List<APerformanceStatYearscore> queryStat(APerformanceStatYearscoreQueryParam param);
}
