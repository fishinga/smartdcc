/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * 
 * @author attiot
 * 2018-05-28 10:34:26
 */
@Getter
@Setter
@ToString
public class TaskTableData {
		// 
	private String	id;
	// 请点ID
	private String	ppointId;
	// 
	private String	taskTableId;
	// 
	private String	colId;
	// 编号
	private String	colCode;
	// 名称
	private String	colName;
	// 值
	private String	colValue;
	// 选项
	private String	colItems;

}
