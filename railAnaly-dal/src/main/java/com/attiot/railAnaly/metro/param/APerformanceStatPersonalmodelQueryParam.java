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
 * 年度员工各模块分数汇总
 * @author attiot
 * 2019-03-11 11:15:55
 */
@Getter
@Setter
@ToString
public class APerformanceStatPersonalmodelQueryParam extends SqlQueryParam {
       
    /***/
    private String id;
    /**考评年*/
    private String year;
    /**被考核人*/
    private String userid;
    /**班组*/
    private String departId;
    /**检修质量分1*/
    private Double repairScore1;
    /**检修质量分2*/
    private Double repairScore2;
    /**检修质量分3*/
    private Double repairScore3;
    /**检修质量分*/
    private Double repairScore;
    /**安全生产分*/
    private Double productScore;
    /**劳动纪律分*/
    private Double labourScore;
    /**合理化建议*/
    private Double rationalSuggestScore;
    /**综合分*/
    private Double comprehensivScore;
    /**奖惩类分*/
    private Double rewardsScore;
    /**6S评分*/
    private Double sixsScore;
    /**其他*/
    private Double otherScore;
    /***/
    private Date lastUpdate;

    /**考评年月*/
    private String yearmonthBegin;
    /**考评年月*/
    private String yearmonthEnd;

    private String userName;

}
