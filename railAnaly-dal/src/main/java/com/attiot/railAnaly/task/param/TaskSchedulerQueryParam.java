/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import com.attiot.railAnaly.foundation.SqlQueryParam;

/**
 * 调度计划
 * @author attiot
 * 2018-04-19 19:13:15
 */
@Getter
@Setter
@ToString
public class TaskSchedulerQueryParam extends SqlQueryParam {
	
    // a_task_lib:id
    private String	id;
    // 任务来源id 系统修包id,普查Id,临时id，故障提报id
    private String	sourceJobId;
    // 列车号
    private String	trainNo;
    // 单元号；只针对系统修
    private String	trainUnit;
    // 任务名称
    private String	jobName;
    // 任务来源:1系统修；2普查整改；3故障提报；4日检/四日检；5临时
    private String	jobSource;
    // 作业月份:yyyyMM
    private Integer	workmonth;
    //
    private Date workDate;
    // 作业班组
    private String	teamDepartId;
    // 作业状态；0未分配；1已分配; 2进行中;3完成
    private Integer	workState;
    // 创建者
    private String	creator;
    // 创建时间
    private Date	createtime;
    // 修改者
    private String	modifor;
    // 修改时间
    private Date	modifytime;

}
