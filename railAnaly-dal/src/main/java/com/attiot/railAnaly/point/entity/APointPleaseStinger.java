/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.point.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

/**
 * stinger断送电
 *
 * @author attiot
 * 2018-04-17 20:27:15
 */
@Getter
@Setter
@ToString
public class APointPleaseStinger{
    
    /***/
    private String id;
    /**请点ID*/
    private String pleasePointId;
    /**申请地点*/
    private String place;
    /**方向*/
    private Integer direction;
    /**STINGER柜编号*/
    private String stingerCode;
    /**确认人*/
    private String confirmerId;
    /**确认人*/
    private String confirmer;
    /**确认时间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date confirmTime;
    /**发放stinger柜开关钥匙时间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date sendKeyTime;
    /**STINGER柜送电时间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date givePowerTime;
    /**B1电源供电*/
    private Integer b1State;
    /**B2电源供电*/
    private Integer b2State;
    /*** 断电申请时间 ***/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date powerOffTime;
    /** 实际断电时间**/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date outageTime;
    /**收回钥匙点时间**/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date takeBackTime;

    private java.lang.Integer b1State2;
    private java.lang.Integer b2State2;
    /***操作人ID**/
    private String operatorId;
    /***操作人名称**/
    private String operatorName;
    /***监控人ID**/
    private String monitorId;
    /***监控人名**/
    private String monitorName;
    /***操作人ID**/
    private String operatorId2;
    /***操作人名称**/
    private String operatorName2;
    /***监控人ID**/
    private String monitorId2;
    /***监控人名**/
    private String monitorName2;
    private Map crossMap;


    /******非数据库 字段 begin *****/
    private String confirmTimeStr;
    private String sendKeyTimeStr;
    private String givePowerTimeStr;
    private String powerOffTimeStr;
    private String outageTimeStr;
    private String takeBackTimeStr;
    /******非数据库 字段 end  *****/
}
