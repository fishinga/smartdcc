/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 年度绩效汇总
 * @author attiot
 * 2019-03-08 17:14:48
 */
@Getter
@Setter
@ToString
public class APerformanceStatYearscore implements java.io.Serializable{
       
    /***/
    private String id;
    /**年份*/
    private String year;
    /**班组,t_s_depart:id*/
    private String departId;
    /**班组类别*/
    private String departType;
    /**员工*/
    private String userid;
    private String userName;
    /**1月份得分*/
    private Double month1;

    /**2月份得分*/
    private Double month2;
    /**3月份得分*/
    private Double month3;
    /**4月份得分*/
    private Double month4;
    /**5月份得分*/
    private Double month5;
    /**6月份得分*/
    private Double month6;
    /**7月份得分*/
    private Double month7;
    /**8月份得分*/
    private Double month8;
    /**9月份得分*/
    private Double month9;
    /**10月份得分*/
    private Double month10;
    /**11月份得分*/
    private Double month11;
    /**12月份得分*/
    private Double month12;
    /**考评年月*/
    private String yearmonthBegin;
    /**考评年月*/
    private String yearmonthEnd;

    private String departName;

    private String departTypeName;

    private Double quarter1;
    private Double quarter2;
    private Double quarter3;
    private Double quarter4;
}
