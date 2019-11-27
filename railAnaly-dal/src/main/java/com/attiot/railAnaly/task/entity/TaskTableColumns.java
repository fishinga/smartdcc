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
 * 2018-05-28 10:33:05
 */
@Getter
@Setter
@ToString
public class TaskTableColumns {
		// 
	private String	id;
	// 
	private String	taskTableId;
	// 
	private String	parentId;
	// 字段排序
	private Integer	colSort;
	// 编号
	private String	colCode;
	// 名称
	private String	colName;
	// 类型：0目录/分类；1输入框；2单选框
	private Integer	colType;
	// 默认值
	private String	defaultVal;
	// 选项
	private String	colItems;

}
