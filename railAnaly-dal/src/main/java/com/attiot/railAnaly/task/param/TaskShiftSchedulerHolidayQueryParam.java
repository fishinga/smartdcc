/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import com.attiot.railAnaly.foundation.SqlQueryParam;

/**
 * 排班计划
 * @author attiot
 * 2018-04-19 19:09:31
 */
@Getter
@Setter
@ToString
public class TaskShiftSchedulerHolidayQueryParam extends SqlQueryParam {
	
    // id
    private String	id;
    // 计划开始日期
    private Date	startDate;
    // 开始星期
    private String	startWeek;
    // 白班
    private String	dayShift;
    // 晚班
    private String	nightShift;
    // 定修1班
    private String	repairOne;
    // 定修2班
    private String	repairTwo;

}
