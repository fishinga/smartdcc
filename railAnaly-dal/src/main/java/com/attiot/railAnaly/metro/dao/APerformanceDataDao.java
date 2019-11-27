/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.APerformanceData;
import com.attiot.railAnaly.metro.param.APerformanceDataQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 考评明细数据
 *
 * @author attiot
 * 2019-02-14 14:58:38
 */
@Repository
public interface APerformanceDataDao {
    /**
     * 新增
     *
     * @param aPerformanceData 参数
     *
     * @author attiot
     * 2019-02-14 14:58:38
     */
    void insert(APerformanceData aPerformanceData);

    /**
     * 修改
     *
     * @param aPerformanceData 参数
     *
     * @author attiot
     * 2019-02-14 14:58:38
     */
    void update(APerformanceData aPerformanceData);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-14 14:58:38
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-14 14:58:38
     */
    List<APerformanceData> query(APerformanceDataQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-14 14:58:38
     */
    APerformanceData getByParam(APerformanceDataQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-14 14:58:38
     */
    long queryCount(APerformanceDataQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-14 14:58:38
     */
    APerformanceData getById(String id);

    long queryCountTen(APerformanceDataQueryParam param);

    List<APerformanceData> queryTen(APerformanceDataQueryParam param);
}
