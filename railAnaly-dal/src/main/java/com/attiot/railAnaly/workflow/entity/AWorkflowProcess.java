/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.workflow.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 
 *
 * @author attiot
 * 2018-04-19 10:33:21
 */
@Getter
@Setter
@ToString
public class AWorkflowProcess{
    
    /***/
    private String id;
    /**流程编号*/
    private String processCode;
    /**流程名称*/
    private String processName;
    /**关联的form_id*/
    private String formId;
    /**1启动；0停止*/
    private Integer state;
    /**创建者*/
    private String creator;
    /**创建时间*/
    private Date createtime;
    /**修改者*/
    private String modifor;
    /***/
    private Date modifytime;

}
