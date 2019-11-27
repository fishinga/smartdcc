/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 年度班组名次汇总
 * @author attiot
 * 2019-03-11 10:33:44
 */
@Getter
@Setter
@ToString
public class APerformanceStatRank  implements java.io.Serializable{
       
    /***/
    private String id;
    /**年份*/
    private String year;
    /**班组*/
    private String departId;
    /**班组类型,定修，轮值，调度*/
    private String departType;
    /**1月份排名*/
    private Integer month1;
    /**2月份排名*/
    private Integer month2;
    /**3月份排名*/
    private Integer month3;
    /**4月份排名*/
    private Integer month4;
    /**5月份排名*/
    private Integer month5;
    /**6月份排名*/
    private Integer month6;
    /**7月份排名*/
    private Integer month7;
    /**8月份排名*/
    private Integer month8;
    /**9月份排名*/
    private Integer month9;
    /**10月份排名*/
    private Integer month10;
    /**11月份排名*/
    private Integer month11;
    /**12月份排名*/
    private Integer month12;
    /**第1名次数*/
    private Integer rank1;
    /**第2名次数*/
    private Integer rank2;
    /**第3名次数*/
    private Integer rank3;
    /**第4名次数*/
    private Integer rank4;
    /**考评年月*/
    private String yearmonthBegin;
    /**考评年月*/
    private String yearmonthEnd;

    private String departName;

    private String departTypeName;
}
