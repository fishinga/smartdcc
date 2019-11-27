/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.APerformanceStatPersonalmodel;
import com.attiot.railAnaly.metro.param.APerformanceStatPersonalmodelQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 年度员工各模块分数汇总
 *
 * @author attiot
 * 2019-03-11 11:15:55
 */
@Repository
public interface APerformanceStatPersonalmodelDao {
    /**
     * 新增
     *
     * @param aPerformanceStatPersonalmodel 参数
     *
     * @author attiot
     * 2019-03-11 11:15:55
     */
    void insert(APerformanceStatPersonalmodel aPerformanceStatPersonalmodel);

    /**
     * 修改
     *
     * @param aPerformanceStatPersonalmodel 参数
     *
     * @author attiot
     * 2019-03-11 11:15:55
     */
    void update(APerformanceStatPersonalmodel aPerformanceStatPersonalmodel);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-11 11:15:55
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-11 11:15:55
     */
    List<APerformanceStatPersonalmodel> query(APerformanceStatPersonalmodelQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-11 11:15:55
     */
    APerformanceStatPersonalmodel getByParam(APerformanceStatPersonalmodelQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-11 11:15:55
     */
    long queryCount(APerformanceStatPersonalmodelQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-11 11:15:55
     */
    APerformanceStatPersonalmodel getById(String id);
}
