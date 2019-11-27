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
 * 人工推车
 * @author attiot
 * 2018-04-18 18:13:10
 */
@Getter
@Setter
@ToString
public class APointPleaseCartQueryParam extends SqlQueryParam {
       
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
    private String bTool;
    /**B状态*/
    private Integer bState;
    /**停放制动*/
    private Integer braking;
    /**铁靯防滑*/
    private Integer ironShoes;
    /**铁靯编号*/
    private String ironShoesCode;
    /**封锁时间*/
    private Date blockTime;
    /**作业轨道*/
    private String blockTrack;
    /**封锁区间*/
    private String blockArea;

}
