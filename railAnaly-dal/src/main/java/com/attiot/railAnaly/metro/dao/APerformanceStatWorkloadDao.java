/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.APerformanceStatWorkload;
import com.attiot.railAnaly.metro.param.APerformanceStatWorkloadQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 月度工作量
 *
 * @author attiot
 * 2019-03-08 15:14:00
 */
@Repository
public interface APerformanceStatWorkloadDao {
    /**
     * 新增
     *
     * @param aPerformanceStatWorkload 参数
     *
     * @author attiot
     * 2019-03-08 15:14:00
     */
    void insert(APerformanceStatWorkload aPerformanceStatWorkload);

    /**
     * 修改
     *
     * @param aPerformanceStatWorkload 参数
     *
     * @author attiot
     * 2019-03-08 15:14:00
     */
    void update(APerformanceStatWorkload aPerformanceStatWorkload);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-08 15:14:00
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-08 15:14:00
     */
    List<APerformanceStatWorkload> query(APerformanceStatWorkloadQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-08 15:14:00
     */
    APerformanceStatWorkload getByParam(APerformanceStatWorkloadQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-08 15:14:00
     */
    long queryCount(APerformanceStatWorkloadQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-08 15:14:00
     */
    APerformanceStatWorkload getById(String id);
}
