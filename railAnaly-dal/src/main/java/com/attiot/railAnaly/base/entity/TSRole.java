/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * 
 * @author attiot
 * 2018-05-02 10:37:05
 */
@Getter
@Setter
@ToString
public class TSRole {
		// ID
	private String	id;
	// 角色编码
	private String	rolecode;
	// 角色名字
	private String	rolename;
	// 修改人
	private String	updateName;
	// 修改时间
	private Date	updateDate;
	// 修改人id
	private String	updateBy;
	// 创建人
	private String	createName;
	// 创建时间
	private Date	createDate;
	// 创建人id
	private String	createBy;

}
