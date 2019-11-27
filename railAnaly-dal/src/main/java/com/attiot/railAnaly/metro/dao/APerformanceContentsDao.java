/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.APerformanceContents;
import com.attiot.railAnaly.metro.param.APerformanceContentsQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 考评内容
 *
 * @author attiot
 * 2019-02-18 10:12:02
 */
@Repository
public interface APerformanceContentsDao {
    /**
     * 新增
     *
     * @param aPerformanceContents 参数
     *
     * @author attiot
     * 2019-02-18 10:12:02
     */
    void insert(APerformanceContents aPerformanceContents);

    /**
     * 修改
     *
     * @param aPerformanceContents 参数
     *
     * @author attiot
     * 2019-02-18 10:12:02
     */
    void update(APerformanceContents aPerformanceContents);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-18 10:12:02
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-18 10:12:02
     */
    List<APerformanceContents> query(APerformanceContentsQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-18 10:12:02
     */
    APerformanceContents getByParam(APerformanceContentsQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-18 10:12:02
     */
    long queryCount(APerformanceContentsQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-18 10:12:02
     */
    APerformanceContents getById(String id);
}
