/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.map.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import com.attiot.railAnaly.foundation.SqlQueryParam;

/**
 * 考勤区域
 * @author attiot
 * 2018-09-04 11:14:24
 */
@Getter
@Setter
@ToString
public class OaZneareaQueryParam extends SqlQueryParam {
	
    // 主键
    private String	id;
    // 所属部门
    private String	sysOrgCode;
    // 半径
    private String	raduis;
    // 中心点
    private String	lngLat;
    // 类型
    private String	type;
    // 更新人名称
    private String	updateName;
    // 更新日期
    private Date	updateDate;
    // 创建人登录名称
    private String	createBy;
    // 所属公司
    private String	sysCompanyCode;
    // 流程状态
    private String	bpmStatus;
    // 创建日期
    private Date	createDate;
    // 更新人登录名称
    private String	updateBy;
    // 创建人名称
    private String	createName;

}
