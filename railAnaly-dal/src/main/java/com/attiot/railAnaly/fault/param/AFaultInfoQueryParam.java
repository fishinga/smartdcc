/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.fault.param;

import com.attiot.railAnaly.foundation.SqlQueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 故障提报信息
 * @author attiot
 * 2019-03-22 09:34:08
 */
@Getter
@Setter
@ToString
public class AFaultInfoQueryParam extends SqlQueryParam{
       
    /***/
    private String id;
    /**报告者*/
    private String reporter;
    /**报告人班组*/
    private String reporterDeptId;
    /**汇报日期*/
    private String reportTime;
    /**故障发生时间*/
    private String faultTime;
    /**故障部件*/
    private String faultDepart;
    /**列车号*/
    private String trainNo;
    /**车体号,0700307004...*/
    private String trainUnit;
    /**车厢号*/
    private String carriageNo;
    /**故障描述*/
    private String faultContents;
    /**车辆公里数*/
    private Float trainMiles;
    /**发生故障期间：1正线运营；2备用；3调试；4维修；5起动*/
    private Integer faultJob;
    /**故障期间在维修, 1系统修；2日检；3四日检*/
    private Integer faultJobDetail;
    /**故障等级,A/B/C*/
    private String faultLevel;
    /**故障完成情况:0提报中；1待处理; 2处理中；3处理完成*/
    private String faultState;
    /**附件*/
    private String attachPath;
    /**创建者*/
    private String creator;
    /**创建时间*/
    private Date createtime;
    /**修改者*/
    private String modifor;
    /**修改时间*/
    private Date modifytime;
    /**任务完成后提交流程：1只到自检；2只到互检；3需要他检*/
    private Integer jobRule;
    /**关联任务ID，对应a_task_list:parent_task_id*/
    private String parentTaskId;
    /**是否已删除，0未删除，1已删除*/
    private Integer deleted;

}
