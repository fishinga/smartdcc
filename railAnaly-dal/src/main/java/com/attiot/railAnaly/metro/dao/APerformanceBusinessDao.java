/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.APerformanceBusiness;
import com.attiot.railAnaly.metro.param.APerformanceBusinessQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 考评模块配置
 *
 * @author attiot
 * 2019-02-11 13:38:03
 */
@Repository
public interface APerformanceBusinessDao {
    /**
     * 新增
     *
     * @param aPerformanceBusiness 参数
     *
     * @author attiot
     * 2019-02-11 13:38:03
     */
    void insert(APerformanceBusiness aPerformanceBusiness);

    /**
     * 修改
     *
     * @param aPerformanceBusiness 参数
     *
     * @author attiot
     * 2019-02-11 13:38:03
     */
    void update(APerformanceBusiness aPerformanceBusiness);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-11 13:38:03
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-11 13:38:03
     */
    List<APerformanceBusiness> query(APerformanceBusinessQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-11 13:38:03
     */
    APerformanceBusiness getByParam(APerformanceBusinessQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-11 13:38:03
     */
    long queryCount(APerformanceBusinessQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-11 13:38:03
     */
    APerformanceBusiness getById(String id);

}
