/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.APerformanceStatPersonal;
import com.attiot.railAnaly.metro.param.APerformanceStatPersonalQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 员工月度绩效评分汇总
 *
 * @author attiot
 * 2019-03-01 10:50:08
 */
@Repository
public interface APerformanceStatPersonalDao {
    /**
     * 新增
     *
     * @param aPerformanceStatPersonal 参数
     *
     * @author attiot
     * 2019-03-01 10:50:08
     */
    void insert(APerformanceStatPersonal aPerformanceStatPersonal);

    /**
     * 修改
     *
     * @param aPerformanceStatPersonal 参数
     *
     * @author attiot
     * 2019-03-01 10:50:08
     */
    void update(APerformanceStatPersonal aPerformanceStatPersonal);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-01 10:50:08
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-01 10:50:08
     */
    List<APerformanceStatPersonal> query(APerformanceStatPersonalQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-01 10:50:08
     */
    APerformanceStatPersonal getByParam(APerformanceStatPersonalQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-01 10:50:08
     */
    long queryCount(APerformanceStatPersonalQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-01 10:50:08
     */
    APerformanceStatPersonal getById(String id);
}
