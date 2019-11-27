/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.metro.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import com.attiot.railAnaly.foundation.SqlQueryParam;

/**
 * 
 * @author attiot
 * 2018-04-03 10:18:42
 */
@Getter
@Setter
@ToString
public class MetroInfoQueryParam extends SqlQueryParam {
	
    // 
    private String	id;
    // 创建人名称
    private String	createName;
    // 创建人登录名称
    private String	createBy;
    // 创建日期
    private Date	createDate;
    // 更新人名称
    private String	updateName;
    // 更新人登录名称
    private String	updateBy;
    // 更新日期
    private Date	updateDate;
    // 所属部门
    private String	sysOrgCode;
    // 所属公司
    private String	sysCompanyCode;
    // 流程状态
    private String	bpmStatus;
    // 列车号
    private String	code;
    // 车型
    private String	type;
    // 采购时间
    private Date	purchaseTime;
    // 所属线路
    private String	line;
    // 供应商
    private String	supplier;
    // 公里数奇
    private String	mileageOdd;
    // 公里数偶
    private String	mileageEven;
    // 停放位置
    private String	site;
    // 备注
    private String	remark;
    // 状态
    private String	status;

}
