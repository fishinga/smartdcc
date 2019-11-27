/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.workflow.dao;

import com.attiot.railAnaly.workflow.entity.AWorkflowProcess;
import com.attiot.railAnaly.workflow.param.AWorkflowProcessQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author attiot
 * 2018-04-19 10:33:22
 */
@Repository
public interface AWorkflowProcessDao {

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-19 10:33:22
     */
    List<AWorkflowProcess> query(AWorkflowProcessQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-19 10:33:22
     */
    AWorkflowProcess getByParam(AWorkflowProcessQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-19 10:33:22
     */
    long queryCount(AWorkflowProcessQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-19 10:33:22
     */
    AWorkflowProcess getById(String id);


    /***
     * 根据编号查询
     * @param processCode
     * @return
     */
    public AWorkflowProcess getByProcessCode(String processCode);

    public List<AWorkflowProcess> getByProcessCodeAndState(Map params);
}
