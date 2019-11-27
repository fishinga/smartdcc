/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.fault.dao;

import com.attiot.railAnaly.fault.entity.AFaultList;
import com.attiot.railAnaly.fault.param.AFaultListQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 任务工单
 *
 * @author attiot
 * 2019-03-30 22:02:26
 */
@Repository
public interface AFaultListDao {
    /**
     * 新增
     *
     * @param aFaultList 参数
     *
     * @author attiot
     * 2019-03-30 22:02:26
     */
    void insert(AFaultList aFaultList);

    /**
     * 修改
     *
     * @param aFaultList 参数
     *
     * @author attiot
     * 2019-03-30 22:02:26
     */
    void update(AFaultList aFaultList);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-30 22:02:26
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-30 22:02:26
     */
    List<AFaultList> query(AFaultListQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-30 22:02:26
     */
    AFaultList getByParam(AFaultListQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-03-30 22:02:26
     */
    long queryCount(AFaultListQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-03-30 22:02:26
     */
    AFaultList getById(String id);

    public List<AFaultList> queryByJobId(String jobId);

    public void finishFaultList(Map params);

    public void updateWorkState(Map params);

    public List<AFaultList> getByUserId(String userId);

    public List<AFaultList> getByJobId(String jobId);

    /**
     * 获取我的未完成故障工单
     * @param params
     * @return
     */
    List<AFaultList> getMyUnfinishFaultList(Map params);

    /**
     * 根据多个ID查故障信息
     * @param ids
     * @return
     */
    List getFaultByIds(String[] ids);

    /**
     * 修改故障工单中的请点ID
     * @param ids
     * @param pointId
     */
    void editFaultPoint(@Param("ids") String[] ids, @Param("pointId") String pointId);

    /**
     * 根据请点ID查询故障信息
     * @param pointId
     * @return
     */
    List<AFaultList> getFaultByPointId(@Param("pointId") String pointId);

    /**
     * 根据请点ID和当前状态更新故障工单状态
     * @param curWorkState
     * @param newWorkState
     * @param pointId
     */
    void editFaultWorkState(@Param("curWorkState")int curWorkState,@Param("newWorkState")int newWorkState,@Param("pointId")String pointId);

    /***
     * 根据任务ID和请点类型判断是否有请点过的任务
     * @param taskIds
     * @param pointType
     * @return
     */
    List<AFaultList> checkTaskRequested(@Param("taskIds") String taskIds,@Param("pointType") String pointType);

}
