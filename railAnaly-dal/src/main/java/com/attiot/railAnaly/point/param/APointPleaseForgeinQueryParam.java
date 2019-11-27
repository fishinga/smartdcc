/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.point.param;

import com.attiot.railAnaly.foundation.SqlQueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 外来人员请点
 * @author attiot
 * 2018-05-30 11:05:36
 */
@Getter
@Setter
@ToString
public class APointPleaseForgeinQueryParam extends SqlQueryParam {

    /**id*/
    private String id;
    /**1:外单位请点；2外分部请点*/
    private Integer forgeinType;
    /**关联任务*/
    private String taskId;
    /**列车号，多辆车以逗号隔开*/
    private String trainNo;
    /**主申请人*/
    private String majorName;
    /**申请人ID*/
    private String majorId;
    /**作业内容*/
    private String jobContent;
    /**作业时间*/
    private String workingTime;
    /**作业时长*/
    private Float pointHours;
    /**有无电作业：1有电；2无电；3有|无电*/
    private Integer jobType;
    /**是否高空作业：1是：0否*/
    private String highWorkType;
    /**高度：1级；2级*/
    private Integer highLevel;
    /**高度层级：1ONE；2TWO*/
    private Integer highStep;
    /**参与作业人员*/
    private String users;
    /**借用作业牌*/
    private String brands;
    private String worker;
    private Integer state;
    /**备注*/
    private String remarks;
    /**创建时间*/
    private Date createtime;
    /**修改时间*/
    private Date modifytime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getForgeinType() {
        return forgeinType;
    }

    public void setForgeinType(Integer forgeinType) {
        this.forgeinType = forgeinType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public String getHighWorkType() {
        return highWorkType;
    }

    public void setHighWorkType(String highWorkType) {
        this.highWorkType = highWorkType;
    }

    public Float getPointHours() {
        return pointHours;
    }

    public void setPointHours(Float pointHours) {
        this.pointHours = pointHours;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getHighLevel() {
        return highLevel;
    }

    public void setHighLevel(Integer highLevel) {
        this.highLevel = highLevel;
    }

    public Integer getHighStep() {
        return highStep;
    }

    public void setHighStep(Integer highStep) {
        this.highStep = highStep;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}
