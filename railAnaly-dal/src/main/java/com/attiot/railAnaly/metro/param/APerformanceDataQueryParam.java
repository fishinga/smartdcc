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
 * 考评明细数据
 * @author attiot
 * 2019-02-14 14:58:38
 */
@Getter
@Setter
@ToString
public class APerformanceDataQueryParam extends SqlQueryParam {
       
    /**Id*/
    private String id;
    /**被考核人*/
    private String userid;
    /**班组*/
    private String departId;
    /**岗位编号*/
    private String positionCode;
    /**考评类型（质量(分部级，班组自查，发现问题)、安全生产、合理化建议、劳动纪律、其他、奖惩类、宣传）*/
    private String perfType;
    /**考评依据ID*/
    private String accordingId;
    /**考评依据名称*/
    private String accordingName;
    /**考评内容*/
    private String perfContentsId;
    /**个人成绩*/
    private String personalScore;
    /**班组成绩*/
    private String teamScore;
    /**考评年月*/
    private String yearmonth;
    /**创建者*/
    private String creator;
    /**创建时间*/
    private Date createtime;
    /**修改者*/
    private String modifor;
    /**修改时间*/
    private Date modifytime;

    private String userName;

}
