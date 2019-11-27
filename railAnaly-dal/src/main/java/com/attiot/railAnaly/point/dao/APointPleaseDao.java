/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.point.dao;

import com.attiot.railAnaly.point.entity.APointPlease;
import com.attiot.railAnaly.point.param.APointPleaseQueryParam;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 请点
 *
 * @author attiot
 * 2018-04-17 09:39:30
 */
@Repository
public interface APointPleaseDao {
    /**
     * 新增
     *
     * @param aPointPlease 参数
     *
     * @author attiot
     * 2018-04-17 09:39:30
     */
    void insert(APointPlease aPointPlease);

    /**
     * 修改
     *
     * @param aPointPlease 参数
     *
     * @author attiot
     * 2018-04-17 09:39:30
     */
    void update(APointPlease aPointPlease);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-17 09:39:30
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-17 09:39:30
     */
    List<APointPlease> query(APointPleaseQueryParam param);

    public long queryUnfinishCount(String userId);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-17 09:39:30
     */
    APointPlease getByParam(APointPleaseQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-17 09:39:30
     */
    long queryCount(APointPleaseQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-17 09:39:30
     */
    APointPlease getById(String id);

    /***
     * 根据多个ID查询 请点信息
     * */
    List<APointPlease> getByIds(String ids);

    public List<Map<String,Object>> getPointList(@Param("userId") String userId);
}
