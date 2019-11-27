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
 * 2017-09-14 10:31:09
 */
@Getter
@Setter
@ToString
public class TSUserOrgQueryParam extends SqlQueryParam {
	
    // id
    private String	id;
    // 用户id
    private String	userId;
    // 部门id
    private String	orgId;

}
