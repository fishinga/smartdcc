/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.point.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 请点
 *
 * @author attiot
 * 2018-04-17 09:39:30
 */
@Getter
@Setter
@ToString
public class APointPlease{
    
    /**id*/
    private String id;
    /**创建人名称*/
    private String createName;
    /**创建人登录名称*/
    private String createBy;
    /**创建日期*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date createDate;
    /**更新人名称*/
    private String updateName;
    /**更新人登录名称*/
    private String updateBy;
    /**更新日期*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date updateDate;
    /**所属部门*/
    private String sysOrgCode;
    /**所属公司*/
    private String sysCompanyCode;
    /**流程状态*/
    private String bpmStatus;
    /**请点类型*/
    private String pointType;
    /**申请人*/
    private String proposer;
    /**班组*/
    private String teams;
    /**作业内容*/
    private String jobContent;
    /**工作时间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date workingTime;
    /***工作时长***/
    private double pointHours;
    /***交接人ID***/
    private String transferId;
    /***交接人名字***/
    private String transferName;
    /***交接时间 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date transferTime;
    /***销点时间 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date saleTime;

    private String carNum;

    /******非数据库 字段 begin *****/
    private String  createDateStr;
    private String  updateDateStr;
    private String  workingTimeStr;
    private String  transferTimeStr;
    private String  pinFlag;//能否销点：0-不可销点，1-可销点
    private int cardFlag;//作业牌状态：2-部分申请中，1-可归还,0-已归还或者没有可归还
    private int riskFlag;//是否需要填写风险预控表：1-必填，0-不填
    private String type;
    /******非数据库 字段 end  *****/
}
