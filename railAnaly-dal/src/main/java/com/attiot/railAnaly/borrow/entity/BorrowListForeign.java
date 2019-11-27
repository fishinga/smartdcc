/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.borrow.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

/**
 * 借用表
 * @author attiot
 * 2018-08-23 09:59:21
 */
@Getter
@Setter
@ToString
public class BorrowListForeign {
		// 
	private String	id;
	// 创建者
	private String	creator;
	// 创建时间
	private Date	createtime;
	// 修改者
	private String	modifor;
	// 修改时间
	private Date	modifytime;
	// 借用状态
	private Integer	borrowState;
	// 借用数量
	private Integer	borrowNum;
	// 借用人
	private String	borrower;
	// 借用时间
	private Date	borrowTime;
	// 备注
	private String	remark;
	// 调度确认人
	private String	dispatcher;
	// 调度时间
	private Date	dispatchTime;
	// 归还数
	private Integer	returnNum;
	// 
	private String	phone;
	// 
	private String	code;

}
