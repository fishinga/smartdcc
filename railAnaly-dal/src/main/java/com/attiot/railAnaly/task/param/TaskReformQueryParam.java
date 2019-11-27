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
 * 普查整改作业信息
 * @author attiot
 * 2018-04-18 17:31:16
 */
@Getter
@Setter
@ToString
public class TaskReformQueryParam extends SqlQueryParam {
	
    // 
    private String	id;
    // 作业名称
    private String	jobname;
    // 作业班组:1轮值；2定修
    private Integer	teamType;
    // 是否包干:1是0否
    private Integer	isContract;
    // 循环值
    private Integer	cycleValue;
    // 循环单位；one;day;week;month;season;halfyear;year;
    private String	cycleUnit;
    // 上次整改日期
    private Date	lastReform;
    // 下次整改日期
    private Date	nextReform;
    // 作业内容
    private String	contents;
    // 作务工时
    private Double	taskHours;
    // 难度系数
    private Double	coeDiff;
    // 加班系数
    private Double	coeOvertime;
    // 协作系数
    private Double	coeCooperation;
    // 上次生成到任务库时间
    private Date	lastGenerate;
    // 附件上传
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
