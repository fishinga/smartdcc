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
 * 员工月度绩效评分汇总
 * @author attiot
 * 2019-03-01 10:50:08
 */
@Getter
@Setter
@ToString
public class APerformanceStatPersonalQueryParam extends SqlQueryParam {
       
    /***/
    private String id;
    /**考评年月*/
    private String yearmonth;
    /**被考核人*/
    private String userid;
    /**班组*/
    private String departId;
    /**岗位编号*/
    private String positionCode;
    /**工作量*/
    private Double workload;
    /**检修质量分*/
    private Double repairScore;
    /**安全生产分*/
    private Double productScore;
    /**劳动纪律分*/
    private Double labourScore;
    /**综合分*/
    private Double comprehensivScore;
    /**奖惩类分*/
    private Double rewardsScore;
    /**评价得分*/
    private Double evaluteScore;
    /**评价结果A/B/C*/
    private String evaluteLevel;
    /***/
    private Date lastUpdate;

    private String userName;
    /**考评年月*/
    private String yearmonthBegin;
    /**考评年月*/
    private String yearmonthEnd;
    private Double rationalSuggestScore;
    private Double sixSScore;
}
