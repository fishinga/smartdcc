/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.APerformanceActualhours;
import com.attiot.railAnaly.metro.param.APerformanceActualhoursQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 月度实际工时
 *
 * @author attiot
 * 2019-02-25 16:39:43
 */
@Repository
public interface APerformanceActualhoursDao {
    /**
     * 新增
     *
     * @param aPerformanceActualhours 参数
     *
     * @author attiot
     * 2019-02-25 16:39:43
     */
    void insert(APerformanceActualhours aPerformanceActualhours);

    /**
     * 修改
     *
     * @param aPerformanceActualhours 参数
     *
     * @author attiot
     * 2019-02-25 16:39:43
     */
    void update(APerformanceActualhours aPerformanceActualhours);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-25 16:39:43
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-25 16:39:43
     */
    List<APerformanceActualhours> query(APerformanceActualhoursQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-25 16:39:43
     */
    APerformanceActualhours getByParam(APerformanceActualhoursQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-25 16:39:43
     */
    long queryCount(APerformanceActualhoursQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-25 16:39:43
     */
    APerformanceActualhours getById(String id);
}
