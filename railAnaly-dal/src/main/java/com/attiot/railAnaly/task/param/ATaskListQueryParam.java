/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.task.param;

import com.attiot.railAnaly.foundation.SqlQueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 任务工单
 * @author attiot
 * 2018-04-10 18:11:37
 */
@Getter
@Setter
@ToString
public class ATaskListQueryParam extends SqlQueryParam {
    /***/
    private String id;
    /**列车号*/
    private String trainNo;
    /**单元号；只针对系统修*/
    private String trainUnit;
    /**a_task_system_job:id/a_reform_job:id  日检:null*/
    private String taskLibId;
    /**任务来源;1系统修；2普查整改；3故障提报4；日检/四检*/
    private Integer taskSource;
    /**作业班组*/
    private String teamDepartId;
    /**任务名称*/
    private String taskName;
    /**作业状态；0未开始；1进行中；2已完成*/
    private Integer workState;
    /**作业日期*/
    private Date workDate;
    /**工单是否流转：1是；0否*/
    private Integer isFlow;
    /**计划作业人员*/
    private String scheOperators;
    /**工单流转作业人员*/
    private String flowOperators;
    /**实际作业人员*/
    private String actualOperators;
    /**开始作业时间*/
    private Date startJob;
    /**完成作业时间*/
    private Date finishJob;
    /**作务工时*/
    private Float taskHours;
    /**难度系数*/
    private Float coeDiff;
    /**加班系数*/
    private Float coeOvertime;
    /**协作系数*/
    private Float coeCooperation;
    /**备注*/
    private String remarks;
    /**创建者*/
    private String creator;
    /**创建时间*/
    private Date createtime;
    /**修改者*/
    private String modifor;
    /**修改时间*/
    private Date modifytime;

    private List orgList;

    private String jobId;
}
