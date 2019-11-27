/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.workflow.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 流程节点
 *
 * @author attiot
 * 2018-04-19 11:03:31
 */
@Getter
@Setter
@ToString
public class AWorkflowNode{
    /***/
    private String id;
    /**流程ID*/
    private String processId;
    /**节点编号*/
    private String nodeCode;
    /**节点名称*/
    private String nodeName;
    /**节点类型: 1:开始节点；2：中间节点；3：结束节点*/
    private Integer ntype;
    /**顺序*/
    private Integer nodeSort;
    /**指派方式:1按个人，2按角色；3按组织机构*/
    private Integer assignType;
    /**审批对象*/
    private String assginIds;
    /**1:一人审批通过流程继续；2：多人同时审批通过流程继续*/
    private Integer auditMode;
    /**是否自动流转1是0否*/
    private Integer autoRun;
    /**间隔多少小时后自动流转；当auto_run=1时有效*/
    private Integer autoRunAfterHour;
    /**数据权限*/
    private String dataAuth;
    /**审批通过节点*/
    private String passNodeId;
    /***/
    private String passExpress;
    /***/
    private String notpassNodeId;
    /***/
    private String notpassExpress;
    /**备注*/
    private String remarks;
    /**创建者*/
    private String creator;
    /***/
    private Date createtime;
    /***/
    private String modifor;
    /***/
    private Date modifytime;

}
