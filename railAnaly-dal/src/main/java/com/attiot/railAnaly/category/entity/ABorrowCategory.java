/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.category.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 物品种类
 *
 * @author attiot
 * 2018-04-09 13:33:30
 */

public class ABorrowCategory{
    
    /***/
    private String id;
    /**名称*/
    private String code;
    /**名称*/
    private String name;
    /**parentid*/
    private String parentid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
}
