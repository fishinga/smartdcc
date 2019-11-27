/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.param;

import com.attiot.railAnaly.foundation.SqlQueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 考评模块配置
 * @author attiot
 * 2019-02-11 13:38:03
 */
@Getter
@Setter
@ToString
public class APerformanceBusinessQueryParam extends SqlQueryParam {
       
    /***/
    private String id;
    /***/
    private String code;
    /**模块名称*/
    private String name;
    /**父模块ID*/
    private String parentId;
    /**已删除：0否；1是*/
    private String deleted;
    /**创建者*/
    private String creator;
    /**创建时间*/
    private Date createtime;
    /**修改者*/
    private String modifor;
    /**修改时间*/
    private Date modifytime;

}
