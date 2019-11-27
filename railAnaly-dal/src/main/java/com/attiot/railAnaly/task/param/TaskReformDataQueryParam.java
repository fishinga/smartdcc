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
 * 记录表内容
 * @author attiot
 * 2018-05-31 09:12:25
 */
@Getter
@Setter
@ToString
public class TaskReformDataQueryParam extends SqlQueryParam {
	
    // 
    private String	id;
    // 整改批次号：a_task_list:source_num
    private String	reformNum;
    // 外键：a_task_list:id
    private String	taskListId;
    // 车号,a_task_list:train_no
    private String	trainNo;
    // 列ID
    private String	colId;
    // 字段排序
    private Integer	colSort;
    // 编号
    private String	colCode;
    // 名称
    private String	colName;
    // 值
    private String	colValue;

}
