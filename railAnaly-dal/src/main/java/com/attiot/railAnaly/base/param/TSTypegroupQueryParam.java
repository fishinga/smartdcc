/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.param;

import java.util.Date;

import com.attiot.railAnaly.foundation.SqlQueryParam;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author attiot
 * 2017-09-25 10:02:30
 */
@Getter
@Setter
@ToString
public class TSTypegroupQueryParam extends SqlQueryParam {
	
    // id
    private String	id;
    // 字典分组编码
    private String	typegroupcode;
    // 字典分组名称
    private String	typegroupname;
    // 创建时间
    private Date	createDate;
    // 创建用户
    private String	createName;

}
