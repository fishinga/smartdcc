/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.workflow.dao;

import com.attiot.railAnaly.workflow.entity.AWorkflowNode;
import com.attiot.railAnaly.workflow.param.AWorkflowNodeQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 流程节点
 *
 * @author attiot
 * 2018-04-19 11:03:31
 */
@Repository
public interface AWorkflowNodeDao {

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-19 11:03:31
     */
    List<AWorkflowNode> query(AWorkflowNodeQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-19 11:03:31
     */
    AWorkflowNode getByParam(AWorkflowNodeQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-19 11:03:31
     */
    long queryCount(AWorkflowNodeQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-19 11:03:31
     */
    AWorkflowNode getById(String id);

    /***
     * 根据流程ID查询 节点信息
     * @param processId
     * @param ntype
     * @return
     */
    public AWorkflowNode getByProcessIdAndNtype(@Param("processId") String processId, @Param("ntype")String ntype);

    public List<AWorkflowNode> getNodeByProcessAndNtype(Map params);
}
