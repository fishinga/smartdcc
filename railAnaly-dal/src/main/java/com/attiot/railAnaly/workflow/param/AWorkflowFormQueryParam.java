/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.workflow.param;

import com.attiot.railAnaly.foundation.SqlQueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 表单设计
 * @author attiot
 * 2018-04-19 10:46:23
 */
@Getter
@Setter
@ToString
public class AWorkflowFormQueryParam extends SqlQueryParam {
       
    /***/
    private String id;
    /***/
    private String formName;
    /**表单的URL地址*/
    private String formUrl;
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

}
