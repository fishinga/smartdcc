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
 * 考勤数据
 * @author attiot
 * 2018-09-05 09:04:42
 */
@Getter
@Setter
@ToString
public class OaAttendanceQueryParam extends SqlQueryParam {
	
    // 
    private String	id;
    // 操作人员
    private String	userId;
    // 签到，签退类型，1签到，2签退
    private String	signType;
    // 考勤时间
    private String	signTime;
    // 考勤状态:1正常2迟到，3早退
    private String	signState;
    // 创建时间
    private Date	createtime;
    // 修改时间
    private Date	modifytime;
    // 修改者
    private String	modifor;

}
