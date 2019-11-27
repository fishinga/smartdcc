/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.workflow.dao;

import java.util.List;

import com.attiot.railAnaly.workflow.entity.AWorkflowForm;
import com.attiot.railAnaly.workflow.param.AWorkflowFormQueryParam;
import org.springframework.stereotype.Repository;

/**
 * 表单设计
 *
 * @author attiot
 * 2018-04-19 10:46:23
 */
@Repository
public interface AWorkflowFormDao {

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-19 10:46:23
     */
    List<AWorkflowForm> query(AWorkflowFormQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-19 10:46:23
     */
    AWorkflowForm getByParam(AWorkflowFormQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-19 10:46:23
     */
    long queryCount(AWorkflowFormQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-19 10:46:23
     */
    AWorkflowForm getById(String id);
}
