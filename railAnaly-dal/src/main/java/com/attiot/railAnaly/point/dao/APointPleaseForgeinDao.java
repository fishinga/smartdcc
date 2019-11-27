/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.point.dao;


import com.attiot.railAnaly.point.entity.APointPleaseForgein;
import com.attiot.railAnaly.point.param.APointPleaseForgeinQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 外来人员请点
 *
 * @author attiot
 * 2018-05-07 21:50:52
 */
@Repository
public interface APointPleaseForgeinDao {
    /**
     * 新增
     *
     * @param aPointPleaseForgein 参数
     *
     * @author attiot
     * 2018-05-07 21:50:52
     */
    void insert(APointPleaseForgein aPointPleaseForgein);

    /**
     * 修改
     *
     * @param aPointPleaseForgein 参数
     *
     * @author attiot
     * 2018-05-07 21:50:52
     */
    void update(APointPleaseForgein aPointPleaseForgein);



    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-05-07 21:50:52
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-05-07 21:50:52
     */
    List<APointPleaseForgein> query(APointPleaseForgeinQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-05-07 21:50:52
     */
    APointPleaseForgein getByParam(APointPleaseForgeinQueryParam param);

    public List<APointPleaseForgein> getByMajor(String majorName);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-05-07 21:50:52
     */
    long queryCount(APointPleaseForgeinQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-05-07 21:50:52
     */
    APointPleaseForgein getById(String id);

    public List<APointPleaseForgein> getForgeinList(APointPleaseForgeinQueryParam param);
}
