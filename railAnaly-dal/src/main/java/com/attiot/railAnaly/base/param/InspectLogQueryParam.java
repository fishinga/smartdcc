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
 * 巡检记录
 * @author attiot
 * 2017-12-12 16:43:02
 */
@Getter
@Setter
@ToString
public class InspectLogQueryParam extends SqlQueryParam {
	
    // 
    private String	id;

}
