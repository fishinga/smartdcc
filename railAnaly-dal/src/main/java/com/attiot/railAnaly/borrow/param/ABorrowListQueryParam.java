/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.borrow.param;

import com.attiot.railAnaly.foundation.SqlQueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 借用表
 * @author attiot
 * 2018-04-10 19:45:17
 */
@Getter
@Setter
@ToString
public class ABorrowListQueryParam extends SqlQueryParam {
       
    /***/
    private String id;
    /**请点ID*/
    private String ppointId;
    /**创建者*/
    private String creator;
    /**创建时间*/
    private Date createtime;
    /**修改者*/
    private String modifor;
    /**修改时间*/
    private Date modifytime;
    /**借用状态*/
    private Integer borrowState;
    /**借用数量*/
    private Integer borrowNum;
    /**借用人*/
    private String borrower;
    /**借用时间*/
    private Date borrowTime;
    /**备注*/
    private String remark;
    /**作业内容*/
    private String jobContent;
    /**创建者所属机构*/
    private String creatorOrg;
    /**调度确认人*/
    private String dispatcher;
    /**调度时间*/
    private Date dispatchTime;
    /**0-员工借用：1-外部人员借用*/
    private Integer type;
    // 
    private String	phone;
    // 
    private String	code;
}
