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
 * InnoDB free: 599040 kB; (`userid`) REFER `jeecg/t_s_user`(`i
 * @author attiot
 * 2017-09-13 10:20:17
 */
@Getter
@Setter
@ToString
public class TSRoleUserQueryParam extends SqlQueryParam {
	
    // ID
    private String	id;
    // 角色ID
    private String	roleid;
    // 用户ID
    private String	userid;

}
