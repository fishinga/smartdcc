/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.task.dao;

import com.attiot.railAnaly.task.entity.ATaskList;
import com.attiot.railAnaly.task.entity.TaskScheduler;
import com.attiot.railAnaly.task.param.ATaskListQueryParam;
import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 任务工单
 *
 * @author attiot
 * 2018-04-10 18:11:37
 */
@Repository
public interface ATaskListDao {


    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-10 18:11:37
     */
    List<ATaskList> query(ATaskListQueryParam param);

    void insert(ATaskList aTaskList);

    /**
     * 获取任务工单
     * @param param
     * @return
     */
    public List<Map<String,Object>> queryTaskList(ATaskListQueryParam param);


    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-10 18:11:37
     */
    long queryCount(ATaskListQueryParam param);


    /***
     *  查询需要完成的任务
     * @param departId
     * @return
     */
    public List getNeedTask(@Param("departId") String departId,@Param("userId")String userId);

    /***
     * 根据多个id 查询任务信息
     * @param ids
     * @return
     */
    public List getTaskByIds(String[] ids);

    /***
     * 修改任务的请点id
     * @param ids
     * @param pointId
     */
    public void editTaskPoint(@Param("ids") String[] ids,@Param("pointId") String pointId);


    /***
     * 根据请点ID和当前状态更新任务状态
     * @param curWorkState
     * @param newWorkState
     * @param pointId
     */
    public void editTaskWorkState(@Param("curWorkState")int curWorkState,@Param("newWorkState")int newWorkState,@Param("pointId")String pointId);


    /***
     * 根据请点ID 查询任务
     * @param pointId
     * @return
     */
    public List<ATaskList> getTaskByPointId(@Param("pointId") String pointId);

    public ATaskList getById(@Param("id") String id);

    public List<Map<String,Object>> getTaskJob(@Param("id")String id);

    public List<Map<String,Object>> getTaskRefrom(@Param("id")String id);

    public List<Map<String,Object>> getTaskTemp(@Param("id")String id);

    public void insertHisList(Map params);

    void delete(String id);

    void update(ATaskList aTaskList);

    public void finishJob(Map params);

    public void updateParentTaskId(List<ATaskList> list);

    public List<Map<String,Object>> pointCar(String userId);

    public void updateWorkStateAndRemarks(Map params);

    public List<ATaskList> getByIds(String ids);

    public List<ATaskList> getATaskListByWorkDateAndTaskSource(Map params);

    public List<ATaskList> getMyUnfinishTaskList(Map params);

    /***
     * 根据任务ID和请点类型判断是否有请点过的任务
     * @param taskIds
     * @param pointType
     * @return
     */
    public List<ATaskList> checkTaskRequested(@Param("taskIds")String taskIds,@Param("pointType")String pointType);


    public Long getUnfinishTaskCountByUserId(String userId);
    
    public List<ATaskList> getByParentTaskId(String parentTaskId);
}
