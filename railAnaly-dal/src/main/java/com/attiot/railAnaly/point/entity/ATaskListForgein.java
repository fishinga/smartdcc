/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.point.entity;

import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 任务工单(外来人员)
 *
 * @author attiot
 * 2018-06-20 15:58:15
 */

@ToString
public class ATaskListForgein {
    
    /***/
    private String id;
    /**任务批次号*/
    private String sourceNum;
    /**列车号*/
    private String trainNo;
    /**源作业ID*/
    private String sourceId;
    /**外来人员请点ID,a_point_please_forgein:id*/
    private String pointId;
    /**任务来源:系统修_1,普查整改_2,故障提报_3,临时_5,日检/四日检_4*/
    private Integer taskSource;
    /**任务名称*/
    private String taskName;
    /**作业状态；0未开始；1申请中；2作业中；3完成*/
    private Integer workState;
    private String workDateStr;
    /**开始作业日期*/
    private Date startWorkDate;
    /**截止作业日期*/
    private Date endWorkDate;
    /**开始作业时间*/
    private Date startJob;
    private String startJobStr;
    /**完成作业时间*/
    private Date finishJob;
    private String finishJobStr;
    /**备注*/
    private String remarks;
    /**创建者*/
    private String creator;
    /**创建时间*/
    private Date createtime;
    /**修改者*/
    private String modifor;
    /**修改时间*/
    private Date modifytime;

    /**部分完成时父任务Id*/
    private String parentTaskId;
    //完成百分比
    private Float surplusValue;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceNum() {
        return sourceNum;
    }

    public void setSourceNum(String sourceNum) {
        this.sourceNum = sourceNum;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public Integer getTaskSource() {
        return taskSource;
    }

    public void setTaskSource(Integer taskSource) {
        this.taskSource = taskSource;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getWorkState() {
        return workState;
    }

    public void setWorkState(Integer workState) {
        this.workState = workState;
    }

    public String getWorkDateStr() {
        return workDateStr;
    }

    public void setWorkDateStr(String workDateStr) {
        this.workDateStr = workDateStr;
    }

    public Date getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(Date startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public Date getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(Date endWorkDate) {
        this.endWorkDate = endWorkDate;
    }

    public Date getStartJob() {
        return startJob;
    }

    public void setStartJob(Date startJob) {
        this.startJob = startJob;
    }

    public String getStartJobStr() {
        if(null != startJob) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            startJobStr = dateFormat.format(startJob);
        }
        return startJobStr;
    }

    public void setStartJobStr(String startJobStr) {
        this.startJobStr = startJobStr;
    }

    public Date getFinishJob() {
        return finishJob;
    }

    public void setFinishJob(Date finishJob) {
        this.finishJob = finishJob;
    }

    public String getFinishJobStr() {
        if(null != finishJob) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            finishJobStr = dateFormat.format(finishJob);
        }
        return finishJobStr;
    }

    public void setFinishJobStr(String finishJobStr) {
        this.finishJobStr = finishJobStr;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getModifor() {
        return modifor;
    }

    public void setModifor(String modifor) {
        this.modifor = modifor;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public Float getSurplusValue() {
        return surplusValue;
    }

    public void setSurplusValue(Float surplusValue) {
        this.surplusValue = surplusValue;
    }
}
