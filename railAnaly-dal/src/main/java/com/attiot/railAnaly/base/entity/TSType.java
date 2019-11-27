/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * InnoDB free: 599040 kB; (`typegroupid`) REFER `jeecg/t_s_typ
 * @author attiot
 * 2017-09-25 10:02:03
 */
@Getter
@Setter
@ToString
public class TSType {
	// id
	private String	id;
	// 字典编码
	private String	typecode;
	// 字典名称
	private String	typename;
	// 无用字段
	private String	typepid;
	// 字典组ID
	private String	typegroupid;
	// 创建时间
	private Date	createDate;
	// 创建用户
	private String	createName;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getCreateDate() {
		return createDate;
	}
}
