/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.goods.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * 请点工单
 * @author attiot
 * 2018-05-14 19:52:29
 */
@Getter
@Setter
@ToString
public class APointListHis {
		// 
	private String	id;
	// 列车号
	private String	trainNo;
	// 上级
	private String	parentId;
	// a_please_point:id
	private String	ppointId;
	// 请点内容
	private String	ppointContents;
	// 请点人
	private String	ppointCreator;
	// 销点人
	private String	ppointTransfer;
	// 是否有电作业:1有：2无,3有/无电作业
	private Integer	isElectric;
	// 开始作业时间
	private Date	startJob;
	// 请点时长
	private Double	ppointDuration;
	// 列车状态
	private String	metroStatus;
	// 停放位置
	private String	trackName;
	// 创建者
	private String	creator;
	// 创建时间
	private Date	createtime;
	// 修改者
	private String	modifor;
	// 修改时间
	private Date	modifytime;

}
