/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.point.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 登车工作许可
 *
 * @author attiot
 * 2018-04-17 14:35:11
 */
@Getter
@Setter
@ToString
public class APointPleaseBoarding{
    
    /***/
    private String id;
    /**请点id*/
    private String pointPleaseId;
    /**申请人*/
    private String proposer;
    /**班组*/
    private String teams;
    /**车号*/
    private String carNum;
    /**作业类型*/
    private String jobType;
    /**是否高空作业*/
    private Integer highWorkType;
    /**高处作业级别*/
    private Integer highLevel;
    /**高处作业措施*/
    private Integer highStep;
    /**受电弓状态*/
    private Integer pantograph;
    /**蓄电池状态*/
    private Integer battery;
    /**高速断路器*/
    private Integer breaker;
    /**停车制动*/
    private Integer braking;
    /**受电弓状态（调度用）*/
    private Integer pantograph2;
    /**蓄电池状态（调度用）*/
    private Integer battery2;
    /**高速断路器（调度用）*/
    private Integer breaker2;
    /**停车制动(调度用)*/
    private Integer braking2;
    /**处理结果**/
    private String handleInfo;

    private Map crossMap;

    /*列车状态(请点时)*/
    private java.lang.String metroStatus;

}
