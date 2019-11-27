/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 员工月度绩效评分汇总
 * @author attiot
 * 2019-03-07 14:29:18
 */
@Getter
@Setter
@ToString
public class APerformanceStatTeam implements java.io.Serializable{
       
    /***/
    private String id;
    /**年月*/
    private String yearmonth;
    /**班组类别*/
    private String departType;
    /**班组*/
    private String departId;
    /**检修质量分*/
    private Double repairScore;
    /**安全生产分*/
    private Double productScore;
    /**劳动纪律分*/
    private Double labourScore;
    /**合理化建议*/
    private Double rationalSuggestScore;
    /**宣传工作*/
    private Double propWorkScore;
    /**6S得分*/
    private Double sixsScore;
    /**其他*/
    private Double otherScore;
    /**奖惩类分*/
    private Double rewardsScore;
    /**总分*/
    private Double comprehensivScore;
    /**评价得分*/
    private Double evaluteScore;
    /**排名*/
    private String evaluteLevel;
    /***/
    private Date lastUpdate;
    /**考评年月*/
    private String yearmonthBegin;
    /**考评年月*/
    private String yearmonthEnd;

    private String departName;

    private String departTypeName;
}
