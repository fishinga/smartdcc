/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * 历史任务工单
 * @author attiot
 * 2018-05-21 11:05:08
 */
@Getter
@Setter
@ToString
public class ATaskListHis {
		// 
	private String	id;
	// 列车号
	private String	trainNo;
	// 单元号；只针对系统修
	private String	trainUnit;
	// a_task_lib:id
	private String	taskLibId;
	// 作业ID
	private String	jobId;
	// 上级作业ID
	private String	parentJobId;
	// 上级作业名称
	private String	parentJobName;
	// 交接前来源
	private Integer	preTransferSource;
	// 审查类型：1只到自检；2只到互检；3只到他检
	private Integer	auditType;
	// 任务来源:系统修_1,普查整改_2,故障提报_3,临时_5,日检/四日检_4
	private Integer	taskSource;
	// 作业班组
	private String	teamDepartId;
	// a_please_point:id
	private String	ppointId;
	// 任务名称
	private String	taskName;
	// 作业状态；0未开始；1进行中；2已完成
	private Integer	workState;
	// 作业日期
	private Date	workDate;
	// 工单是否流转：1是；0否
	private Integer	isFlow;
	// 计划作业人员
	private String	scheOperators;
	// 工单流转作业人员
	private String	flowOperators;
	// 实际作业人员
	private String	actualOperators;
	// 开始作业时间
	private Date	startJob;
	// 完成作业时间
	private Date	finishJob;
	// 作务工时
	private Double	taskHours;
	// 难度系数
	private Double	coeDiff;
	// 加班系数
	private Double	coeOvertime;
	// 协作系数
	private Double	coeCooperation;
	// 备注
	private String	remarks;
	// 创建者
	private String	creator;
	// 创建时间
	private Date	createtime;
	// 修改者
	private String	modifor;
	// 修改时间
	private Date	modifytime;
	// 任务批次号
	private String	sourceNum;

}
