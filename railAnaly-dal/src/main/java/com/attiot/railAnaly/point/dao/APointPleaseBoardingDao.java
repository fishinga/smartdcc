/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.point.dao;

import java.util.List;
import java.util.Map;

import com.attiot.railAnaly.point.entity.APointPleaseBoarding;
import com.attiot.railAnaly.point.param.APointPleaseBoardingQueryParam;
import org.springframework.stereotype.Repository;

/**
 * 登车工作许可
 *
 * @author attiot
 * 2018-04-17 14:35:11
 */
@Repository
public interface APointPleaseBoardingDao {
    /**
     * 新增
     *
     * @param aPointPleaseBoarding 参数
     *
     * @author attiot
     * 2018-04-17 14:35:11
     */
    void insert(APointPleaseBoarding aPointPleaseBoarding);

    /**
     * 修改
     *
     * @param aPointPleaseBoarding 参数
     *
     * @author attiot
     * 2018-04-17 14:35:11
     */
    void update(APointPleaseBoarding aPointPleaseBoarding);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-17 14:35:11
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-17 14:35:11
     */
    List<APointPleaseBoarding> query(APointPleaseBoardingQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-17 14:35:11
     */
    APointPleaseBoarding getByParam(APointPleaseBoardingQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-17 14:35:11
     */
    long queryCount(APointPleaseBoardingQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-17 14:35:11
     */
    APointPleaseBoarding getById(String id);

    public APointPleaseBoarding getByPointId(String pointId);

    public List<Map> getBoardingTrainContent(String sourceId);
}
