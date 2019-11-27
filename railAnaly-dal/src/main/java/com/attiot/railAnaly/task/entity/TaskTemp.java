/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * 临时作业信息
 * @author attiot
 * 2018-04-18 17:34:42
 */
@Getter
@Setter
@ToString
public class TaskTemp {
		// 
	private String	id;
	// 作业名称
	private String	jobname;
	// 作业内容
	private String	contents;
	// 
	private Date	startDate;

	private Date endDate;
	// 列车号
	private String	trainNo;
	// 作业班组
	private String	teamDepartId;
	// 作务工时
	private Double	taskHours;
	// 难度系数
	private Double	coeDiff;
	// 加班系数
	private Double	coeOvertime;
	// 协作系数
	private Double	coeCooperation;
	// 创建者类型1调度创建；2工班长创建
	private Integer	tempType;

	private Integer tempSource;
	// 上次生成任务时间
	private Date	lastGenerate;
	// 附件上传
	private String	attachePath;
	// 创建者
	private String	creator;
	// 创建时间
	private Date	createtime;
	// 修改者
	private String	modifor;
	// 修改时间
	private Date	modifytime;

}
