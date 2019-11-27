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
 * 作业包基本信息(系统修)
 * @author attiot
 * 2018-04-18 17:25:23
 */
@Getter
@Setter
@ToString
public class TaskJobQueryParam extends SqlQueryParam {
	
    // 
    private String	id;
    // 作业名称
    private String	jobname;
    // 作务工时
    private Double	taskHours;
    // 难度系数
    private Double	coeDiff;
    // 加班系数
    private Double	coeOvertime;
    // 协作系数
    private Double	coeCooperation;
    // 作业类型：1系统修；2日检；3四日检；4四日巡查
    private String	jobType;
    // 修程内容
    private String	contents;
    //
    private Date	lastGenerate;
    // 附件
    private String	attachePath;
    // 创建者
    private String	creator;
    // 创建时间
    private Date	createtime;
    // 修改者
    private String	modifor;
    // 修改时间
    private Date	modifytime;

}
