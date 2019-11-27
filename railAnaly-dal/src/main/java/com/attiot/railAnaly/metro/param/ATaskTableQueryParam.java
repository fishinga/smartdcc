/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.param;


import com.attiot.railAnaly.foundation.SqlQueryParam;

import java.util.Date;

/**
 * 阶段表，即作业前，作业中，作业后要填写的表
 * @author attiot
 * 2018-05-18 12:31:51
 */

public class ATaskTableQueryParam extends SqlQueryParam {
       
    /***/
    private String id;
    /***/
    private String tablename;
    /***/
    private String tablecode;
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

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getTablecode() {
        return tablecode;
    }

    public void setTablecode(String tablecode) {
        this.tablecode = tablecode;
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
}
