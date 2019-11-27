/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * 记录表定义
 * @author attiot
 * 2018-05-31 09:10:41
 */
@Getter
@Setter
@ToString
public class TaskReformColumns {
		// 
	private String	id;
	// 
	private String	reformId;
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
