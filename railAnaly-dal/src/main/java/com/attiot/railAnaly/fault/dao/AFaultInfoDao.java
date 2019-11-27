/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.fault.dao;


import com.attiot.railAnaly.fault.entity.AFaultInfo;
import com.attiot.railAnaly.fault.param.AFaultInfoQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 故障提报信息
 *
 * @author attiot
 * 2019-03-22 09:34:08
 */
@Repository
public interface AFaultInfoDao {
    /**
     * 新增
     *
     * @param aFaultInfo 参数
     *
     * @author attiot
     * 2019-03-22 09:34:08
     */
    void insert(AFaultInfo aFaultInfo);

    /**
     * 修改
     *
     * @param aFaultInfo 参数
     *
     * @author attiot
     * 2019-03-22 09:34:08
     */
    void update(AFaultInfo aFaultInfo);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-22 09:34:08
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-22 09:34:08
     */
    List<AFaultInfo> query(AFaultInfoQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-22 09:34:08
     */
    AFaultInfo getByParam(AFaultInfoQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-22 09:34:08
     */
    long queryCount(AFaultInfoQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-22 09:34:08
     */
    AFaultInfo getById(String id);

    public void updateFaultState(Map params);

    public void updateFaultStateAndCoeDiff(Map params);

    public void updateFaultStateAndFaultLevel(Map params);

    public List<AFaultInfo> getUnfinishFaultInfoList();

    public List<AFaultInfo> getByIds(String ids);
}
