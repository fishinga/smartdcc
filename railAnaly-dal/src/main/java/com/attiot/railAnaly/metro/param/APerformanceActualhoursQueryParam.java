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
 * 月度实际工时
 * @author attiot
 * 2019-02-25 16:39:43
 */
@Getter
@Setter
@ToString
public class APerformanceActualhoursQueryParam extends SqlQueryParam {
       
    /***/
    private String id;
    /**月工ID*/
    private String userId;
    /**班组*/
    private String departId;
    /**考评年月,格式:yyyyMM*/
    private String yearmonth;
    /**本月实际工时*/
    private Double actualHours;
    /***/
    private String creator;
    /***/
    private Date createtime;
    /***/
    private String modifor;
    /***/
    private Date modifytime;
    private String userName;
}
