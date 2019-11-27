/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.metro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * 物品种类
 * @author attiot
 * 2018-04-04 15:06:50
 */
@Getter
@Setter
@ToString
public class BorrowCategory {
		// 
	private String	id;
	// 名称
	private String	name;
	// parentid
	private String	parentid;

}
