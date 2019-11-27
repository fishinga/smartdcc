/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.point.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * 断送电
 * @author attiot
 * 2018-09-03 16:39:13
 */
@Getter
@Setter
@ToString
public class PointPleasePower {
		// 
	private String	id;
	// 请点ID
	private String	pleasePointId;
	// 申请股道
	private String	stock;
	// 断电/送电
	private Integer	appType;
	// 
	private String	creator;
	// 
	private Date	createtime;
	// 修改者
	private String	modifor;
	// 
	private Date	modifytime;

	private String operatorId;
	private String operatorName;
	private String monitorId;
	private String monitorName;
	private String type;

}
