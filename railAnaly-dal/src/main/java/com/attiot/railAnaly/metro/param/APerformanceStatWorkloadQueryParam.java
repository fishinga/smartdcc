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
 * 月度工作量
 * @author attiot
 * 2019-03-08 15:14:00
 */
@Getter
@Setter
@ToString
public class APerformanceStatWorkloadQueryParam extends SqlQueryParam {
       
    /***/
    private String id;
    /**年月*/
    private String yearmonth;
    /**员工*/
    private String userid;
    /***/
    private String departId;
    /**工作量*/
    private Double workload;
    /**实际工时*/
    private Double actualHours;
    /**工作效率*/
    private Double personalEfficiency;
    /**班组工作效率*/
    private Double teamEfficiency;
    /**工作量得分*/
    private Double workloadScore;
    /***/
    private String modifor;
    /***/
    private Date modifytime;

    private String userName;

    /**考评年月*/
    private String yearmonthBegin;
    /**考评年月*/
    private String yearmonthEnd;

}
