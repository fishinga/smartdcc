/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.fault.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 任务工单
 *
 * @author attiot
 * 2019-03-30 22:02:26
 */
@Getter
@Setter
@ToString
public class AFaultList{
    
    /***/
    private String id;
    /**列车号*/
    private String trainNo;
    /**单元号*/
    private String trainUnit;
    /**作业ID*/
    private String jobId;
    /**审查类型：1只到自检；2只到互检；3只到他检*/
    private Integer auditType;
    /**作业班组*/
    private String teamDepartId;
    /**a_please_point:id*/
    private String ppointId;
    /**任务名称*/
    private String taskName;
    /**作业状态；0未派工；1已派工未开始；2进行中；3完成*/
    private Integer workState;
    /**作业日期*/
    private Date workDate;
    /**计划作业人员*/
    private String scheOperators;
    private String scheOperatorsName;
    /**工单流转作业人员*/
    private String flowOperators;
    /**实际作业人员*/
    private String actualOperators;
    /**开始作业时间*/
    private Date startJob;
    /**完成作业时间*/
    private Date finishJob;
    private String finishJobStr;
    private String faultLevel;
    private String faultDepart;
    private Integer factoryJob;//是否厂家作业
    /**作务工时*/
    private Float taskHours;
    /**难度系数*/
    private Float coeDiff;
    /**加班系数*/
    private Float coeOvertime;
    /**协作系数*/
    private Float coeCooperation;
    /**备注*/
    private String remarks;
    /**故障处理情况*/
    private String doFaultRemarks;
    /**任务派发时间*/
    private Date assignTime;
    private String assignTimeStr;
    /**创建者*/
    private String creator;
    /**创建时间*/
    private Date createtime;
    /**修改者*/
    private String modifor;
    /**修改时间*/
    private Date modifytime;
    /**互检人*/
    private String mutualOperations;
    /**他检人*/
    private String speOperations;
    /**剩余百分比,单位百分比*/
    private Float surplusValue;

    public String getAssignTimeStr() {
        if(null != assignTime) {
            this.assignTimeStr = changeDateToString(this.assignTime,"yyyy-MM-dd HH:mm:ss");
        }
        return assignTimeStr;
    }

    public void setAssignTimeStr(String assignTimeStr) {
        this.assignTimeStr = assignTimeStr;
    }

    public String getFinishJobStr() {
        if(null != finishJob) {
            this.finishJobStr = changeDateToString(this.finishJob,"yyyy-MM-dd HH:mm:ss");
        }

        return finishJobStr;
    }

    public void setFinishJobStr(String finishJobStr) {
        this.finishJobStr = finishJobStr;
    }

    /***
     *
     * @Description: Date转String
     * @Param
     * @Return
     * @Author zhangbf
     * @Createtime 2017年9月28日 下午5:18:13
     */
    public static String changeDateToString(Date date,String format) {
        if(null == date ) return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
