/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.metro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * 股道信息
 * @author attiot
 * 2018-09-04 10:35:11
 */
@Getter
@Setter
@ToString
public class TrackInfo {
		// 
	private String	id;
	// 股道名称
	private String	trackName;
	// 编号 
	private Integer	num;
	// 停放车辆
	private String	trainNo;
	// 是否有电:1有电；0无电
	private Integer	isElectric;
	// 铁鞋:1 有；0无
	private Integer	iconShoes;
	// 创建者
	private String	creator;
	// 创建时间
	private Date	createtime;
	// 修改者
	private String	modifor;
	// 修改时间
	private Date	modifytime;

}
