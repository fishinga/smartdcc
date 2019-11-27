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
 * 人工推车
 *
 * @author attiot
 * 2018-04-18 18:13:10
 */
@Getter
@Setter
@ToString
public class APointPleaseCart{
    
    /***/
    private String id;
    /**请点ID*/
    private String pointPleaseId;
    /**作业列车号*/
    private String trainNum;
    /**作业轨道*/
    private String track;
    /**推车区域*/
    private String trolleyArea;
    /**封锁轨道*/
    private String blockadeOrbit;
    /**信号机*/
    private String signalMachine;
    /**隔离开关*/
    private Integer isolatingSwitch;
    /**接地线*/
    private Integer groundWire;
    /**自定义工具*/
    private String isbtool;
    /**B状态*/
    private Integer isbstate;
    /**停放制动*/
    private Integer braking;
    /**铁靯防滑*/
    private Integer ironShoes;
    /**铁靯编号*/
    private String ironShoesCode;
    private java.lang.String ironShoesCode3;
    /**封锁时间*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat
    private Date blockTime;
    /**作业轨道*/
    private String blockTrack;
    /**封锁区间*/
    private String blockArea;
    /**信号机*/
    private String signalMachine2;
    /** 备注**/
    private String remarks;

    private Map crossMap;

    /**铁靯防滑*/
    private Integer ironShoes3;
    /**停放制动*/
    private Integer braking3;
    /**B状态*/
    private Integer b3state;
    /**自定义工具*/
    private String b3tool;
    /***摆放位置**/
    private String putLocation;
    /**隔离开关*/
    private Integer isolatingSwitch3;
    /**接地线*/
    private Integer groundWire3;

    /***摆放位置**/
    /*** 非数据库字段 begin  ***/
    private String blockTimeStr;
    /*** 非数据库字段 end   ***/

    /*列车状态(请点时)*/
    private java.lang.String metroStatus;
}
