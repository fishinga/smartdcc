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
 * 2017-12-04 09:22:25
 */
@Getter
@Setter
@ToString
public class TSLogQueryParam extends SqlQueryParam {
	
    // id
    private String	id;
    // 浏览器
    private String	broswer;
    // 日志内容
    private String	logcontent;
    // 日志级别
    private Integer	loglevel;
    // IP
    private String	note;
    // 操作时间
    private Date	operatetime;
    // 操作类型
    private Integer	operatetype;
    // 用户ID
    private String	userid;

}
