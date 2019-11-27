/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.workflow.dao;

import com.attiot.railAnaly.workflow.entity.AWorkflowData;
import com.attiot.railAnaly.workflow.param.AWorkflowDataQueryParam;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 流程记录
 *
 * @author attiot
 * 2018-04-18 18:42:09
 */
@Repository
public interface AWorkflowDataDao {
    /**
     * 新增
     *
     * @param aWorkflowData 参数
     *
     * @author attiot
     * 2018-04-18 18:42:09
     */
    void insert(AWorkflowData aWorkflowData);

    /**
     * 修改
     *
     * @param aWorkflowData 参数
     *
     * @author attiot
     * 2018-04-18 18:42:09
     */
    void update(AWorkflowData aWorkflowData);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-18 18:42:09
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-18 18:42:09
     */
    List<AWorkflowData> query(AWorkflowDataQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-18 18:42:09
     */
    AWorkflowData getByParam(AWorkflowDataQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-18 18:42:09
     */
    long queryCount(AWorkflowDataQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-18 18:42:09
     */
    AWorkflowData getById(String id);

    /***
     * 根据用户查询相对应的流程
     * @param userid
     * @return
     */
    public List getBacklog(@Param("userid") String userid);
    /***
     * 根据用户查询相对应的流程new优化后
     * @param userid
     * @return
     */
    public List getBacklogNew(@Param("userid") String userid);

    /**
     * 根据来源ID，查询流程记录
     * @param sourceId
     * @return
     */
    public List getBlockLogNode(@Param("sourceId")String sourceId,@Param("wbNo")String wbNo);

    /***
     * 批量插入
     * @param list
     */
    public void batchInsert(List<AWorkflowData> list);

    public List<AWorkflowData> getAllWorkflowListByWorkflowId(String workflowId);

    public List<AWorkflowData> getWorkflowListBySourceIdAndWbNo(Map params);

    public AWorkflowData getStartWorkflow(@Param("sourceId") String sourceId,@Param("wbNo") String wbNo);

    public List<Map<String,Object>>  getBorrowListByWorkflowId(String workflowId);

    public Long getWaitToDoCountByUserId(String userId);
}
