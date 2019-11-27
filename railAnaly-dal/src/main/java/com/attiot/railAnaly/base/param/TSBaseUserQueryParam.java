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
 * InnoDB free: 600064 kB; (`departid`) REFER `jeecg/t_s_depart
 * @author attiot
 * 2017-12-13 10:25:32
 */
@Getter
@Setter
@ToString
public class TSBaseUserQueryParam extends SqlQueryParam {
	
    // ID
    private String	id;
    // 同步流程
    private Integer	activitisync;
    // 浏览器
    private String	browser;
    // 密码
    private String	password;
    // 真实名字
    private String	realname;
    // 签名
    private byte[]	signature;
    // 有效状态
    private Integer	status;
    // 用户KEY
    private String	userkey;
    // 用户账号
    private String	username;
    // 部门ID
    private String	departid;
    // 删除状态
    private Integer	deleteFlag;

}
