/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.APerformanceAccord;
import com.attiot.railAnaly.metro.param.APerformanceAccordQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 考评依据
 *
 * @author attiot
 * 2019-02-12 16:20:14
 */
@Repository
public interface APerformanceAccordDao {
    /**
     * 新增
     *
     * @param aPerformanceAccord 参数
     *
     * @author attiot
     * 2019-02-12 16:20:14
     */
    void insert(APerformanceAccord aPerformanceAccord);

    /**
     * 修改
     *
     * @param aPerformanceAccord 参数
     *
     * @author attiot
     * 2019-02-12 16:20:14
     */
    void update(APerformanceAccord aPerformanceAccord);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-12 16:20:14
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-12 16:20:14
     */
    List<APerformanceAccord> query(APerformanceAccordQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-12 16:20:14
     */
    APerformanceAccord getByParam(APerformanceAccordQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-12 16:20:14
     */
    long queryCount(APerformanceAccordQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-12 16:20:14
     */
    APerformanceAccord getById(String id);
}
