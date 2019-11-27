/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.point.param;

import com.attiot.railAnaly.foundation.SqlQueryParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 请点
 * @author attiot
 * 2018-04-17 09:39:30
 */
@Getter
@Setter
@ToString
public class APointPleaseQueryParam extends SqlQueryParam {
       
    /**id*/
    private String id;
    /**创建人名称*/
    private String createName;
    /**创建人登录名称*/
    private String createBy;
    /**创建日期*/
    private Date createDate;
    /**更新人名称*/
    private String updateName;
    /**更新人登录名称*/
    private String updateBy;
    /**更新日期*/
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
    private Date workingTime;
    /***工作时长***/
    private int pointHours;
    /***交接人ID***/
    private String transferId;
    /***交接人名字***/
    private String transferName;
    /***交接时间 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date transferTime;

    /***
     * 状态集合
     */
    private String pstatus;

}
