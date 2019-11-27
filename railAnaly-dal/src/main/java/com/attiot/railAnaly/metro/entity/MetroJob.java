/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.metro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * 车辆作业状态
 * @author attiot
 * 2018-04-04 15:12:08
 */
@Getter
@Setter
@ToString
public class MetroJob {
		// 
	private String	id;
	// 列车号
	private String	trainNo;
	// 挂牌情况，显示作业牌名称
	private String	borrowGoodsName;
	// 牌ID，id间以逗号隔开
	private String	borrowGoodsIds;
	// 上级
	private String	parentId;
	// 请点内容
	private String	jobContent;
	// 请点人
	private String	proposer;
	// 有无电状态:0无电；1有电；2有/无电
	private Integer	electricState;
	// 开始作业时间
	private Date	workTime;
	// 请点时长
	private Double	ppointHour;
	// 创建者
	private String	creator;
	// 创建时间
	private Date	createtime;
	// 修改者
	private String	modifor;
	// 修改时间
	private Date	modifytime;

}
