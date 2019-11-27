/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.point.dao;

import java.util.List;
import java.util.Map;

import com.attiot.railAnaly.point.entity.APointPleaseCart;
import com.attiot.railAnaly.point.param.APointPleaseCartQueryParam;
import org.springframework.stereotype.Repository;

/**
 * 人工推车
 *
 * @author attiot
 * 2018-04-18 18:13:10
 */
@Repository
public interface APointPleaseCartDao {
    /**
     * 新增
     *
     * @param aPointPleaseCart 参数
     *
     * @author attiot
     * 2018-04-18 18:13:10
     */
    void insert(APointPleaseCart aPointPleaseCart);

    /**
     * 修改
     *
     * @param aPointPleaseCart 参数
     *
     * @author attiot
     * 2018-04-18 18:13:10
     */
    void update(APointPleaseCart aPointPleaseCart);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-18 18:13:10
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-18 18:13:10
     */
    List<APointPleaseCart> query(APointPleaseCartQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-18 18:13:10
     */
    APointPleaseCart getByParam(APointPleaseCartQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-18 18:13:10
     */
    long queryCount(APointPleaseCartQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-18 18:13:10
     */
    APointPleaseCart getById(String id);

    APointPleaseCart getByPointId(String pointId);

    public List<Map> getCartTrainContent(String sourceId);
}
