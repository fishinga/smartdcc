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
 * stinger断送电
 * @author attiot
 * 2018-04-17 20:27:15
 */
@Getter
@Setter
@ToString
public class APointPleaseStingerQueryParam extends SqlQueryParam {

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
    private String confirmer;
    /**确认时间*/
    private Date confirmTime;
    /**STINGER柜送电时间*/
    private Date givePowerTime;
    /**B1电源供电*/
    private Integer b1State;
    /**B2电源供电*/
    private Integer b2State;

}
