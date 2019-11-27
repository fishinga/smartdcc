/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.point.dao;

import java.util.List;

import com.attiot.railAnaly.point.entity.APointPleaseStinger;
import com.attiot.railAnaly.point.param.APointPleaseStingerQueryParam;
import org.springframework.stereotype.Repository;

/**
 * stinger断送电
 *
 * @author attiot
 * 2018-04-17 20:27:15
 */
@Repository
public interface APointPleaseStingerDao {
    /**
     * 新增
     *
     * @param aPointPleaseStinger 参数
     *
     * @author attiot
     * 2018-04-17 20:27:15
     */
    void insert(APointPleaseStinger aPointPleaseStinger);

    /**
     * 修改
     *
     * @param aPointPleaseStinger 参数
     *
     * @author attiot
     * 2018-04-17 20:27:15
     */
    void update(APointPleaseStinger aPointPleaseStinger);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-17 20:27:15
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-17 20:27:15
     */
    List<APointPleaseStinger> query(APointPleaseStingerQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-17 20:27:15
     */
    APointPleaseStinger getByParam(APointPleaseStingerQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-17 20:27:15
     */
    long queryCount(APointPleaseStingerQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-17 20:27:15
     */
    APointPleaseStinger getById(String id);

    /**
     * 根据请点ID 查询
     * @param pointId
     * @return
     */
    APointPleaseStinger getByPointId(String pointId);
}
