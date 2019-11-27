/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.entity;

import java.util.Date;

/**
 * 
 *
 * @author attiot
 * 2018-05-18 13:08:37
 */

public class ATaskTableData{
    
    /***/
    private String id;
    /***/
    private String taskTableId;

    private String ppointId;
    /***/
    private String colId;
    /**编号*/
    private String colCode;
    /***/
    private String colName;
    /***/
    private String colValue;
    /**创建者*/
    private String creator;
    /**创建时间*/
    private Date createtime;
    /**修改者*/
    private String modifor;
    /**修改时间*/
    private Date modifytime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskTableId() {
        return taskTableId;
    }

    public void setTaskTableId(String taskTableId) {
        this.taskTableId = taskTableId;
    }

    public String getColId() {
        return colId;
    }

    public void setColId(String colId) {
        this.colId = colId;
    }

    public String getColCode() {
        return colCode;
    }

    public void setColCode(String colCode) {
        this.colCode = colCode;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColValue() {
        return colValue;
    }

    public void setColValue(String colValue) {
        this.colValue = colValue;
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

    public String getPpointId() {
        return ppointId;
    }

    public void setPpointId(String ppointId) {
        this.ppointId = ppointId;
    }
}
