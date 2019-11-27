/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.workflow.param;

import com.attiot.railAnaly.foundation.SqlQueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 流程记录
 * @author attiot
 * 2018-04-18 18:42:09
 */
@Getter
@Setter
@ToString
public class AWorkflowDataQueryParam extends SqlQueryParam {

    /***/
    private String id;
    /**工作流编号*/
    private String wbNo;
    /***/
    /*private Integer sort;*/
    /**源记录ID*/
    private String sourceId;
    /**流程主键*/
    private String processId;
    /**流程名称*/
    private String processName;
    /**节点名称*/
    private String nodeId;
    /***/
    private String nodeCode;
    /**对应表单*/
    private String formId;
    /**对应表单URL地址*/
    private String formUrl;
    /**节点名称*/
    private String nodeName;
    /**节点类型：1开始节点；2中间节点；3结束节点*/
    private Integer nodeType;
    /**指派方式:1按个人，2按角色；3按组织机构*/
    private Integer assignType;
    /**指派对象*/
    private String assginIds;
    /**计划审批人*/
    private String auditors;
    /**实际审批人*/
    private String actualAuditors;
    /**审批方式：1一人同意即放下流转；全部同意才往下流转*/
    private Integer auditMode;
    /**数据权限*/
    private String dataAuth;
    /**数据值*/
    private String dataValue;
    /**1简单配置，2高级配置*/
    private Integer configType;
    /**审批通过节点*/
    private String passNodeId;
    /**审批通过表达式*/
    private String passExpress;
    /**审批不通过节点*/
    private String notpassNodeId;
    /**审批不通过表达式*/
    private String notpassExpress;
    /**运行状态：1运行中；0已过*/
    private Integer runState;
    /**审批通过与否：1审批通过；-1审批不通过;0审批中*/
    private Integer auditState;
    /**审批意见*/
    private String auditResults;
    /***/
    private String creator;
    /**创建时间*/
    private Date createtime;
    /**修改者*/
    private String modifor;
    /***/
    private Date modifytime;
    /**当auto_run=1时有效，几个小时后自动流转*/
    private Integer autoRunAfterHour;
    /**是否自动流转：1是；0否*/
    private Integer autoRun;

}
