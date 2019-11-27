/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import com.attiot.railAnaly.foundation.SqlQueryParam;

/**
 * 
 * @author attiot
 * 2017-12-13 14:02:51
 */
@Getter
@Setter
@ToString
public class TSDepartQueryParam extends SqlQueryParam {
	
    // ID
    private String	id;
    // 部门名称
    private String	departname;
    // 描述
    private String	description;
    // 父部门ID
    private String	parentdepartid;
    // 机构编码
    private String	orgCode;
    // 机构类型,9表示作业工班
    private String	orgType;
    // 手机号
    private String	mobile;
    // 传真
    private String	fax;
    // 地址
    private String	address;
    // 排序
    private String	departOrder;
    private String departType;

}
