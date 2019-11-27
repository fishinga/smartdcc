/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.borrow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 借用表
 *
 * @author attiot
 * 2018-04-10 19:45:17
 */
@Getter
@Setter
@ToString
public class ABorrowList{
    
    /***/
    private String id;
    /**请点ID*/
    private String ppointId;
    /**创建者*/
    private String creator;
    /**创建时间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date dispatchTime;
    /**归还数量*/
    private Integer returnNum;
    
    /**借用类型0-内部借用;1-外单位借用**/
	private Integer type;
	
	/**外单位借用人员电话*/
	private String phone;
	/**外单位借用人员作业证号*/
	private String code;
    
    
    /***********非数据库字段  begin ************/
    private String borrowerName;
    private String pointType;
    /***********非数据库字段  end ************/

}
