/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author attiot
 * 2017-09-25 10:02:30
 */
@Getter
@Setter
@ToString
public class TSTypegroup {
	// id
	private String	id;
	// 字典分组编码
	private String	typegroupcode;
	// 字典分组名称
	private String	typegroupname;
	// 创建时间
	private Date	createDate;
	// 创建用户
	private String	createName;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getCreateDate() {
		return createDate;
	}
}
