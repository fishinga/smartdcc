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
 * 通知公告表
 * @author attiot
 * 2017-09-13 10:01:01
 */
@Getter
@Setter
@ToString
public class TSNoticeQueryParam extends SqlQueryParam {
	
    // ID
    private String	id;
    // 通知标题
    private String	noticeTitle;
    // 通知公告内容
    private String	noticeContent;
    // 通知公告类型（1：通知，2:公告）
    private String	noticeType;
    // 通告授权级别（1:全员，2：角色，3：用户）
    private String	noticeLevel;
    // 阅读期限
    private Date	noticeTerm;
    // 创建者
    private String	createUser;
    // 创建时间
    private Date	createDate;

    private Date newDate;

    private String cityCode;

    private String appType;

    private String roleId;

}
