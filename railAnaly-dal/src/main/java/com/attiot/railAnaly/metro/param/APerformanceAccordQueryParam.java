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
 * 考评依据
 * @author attiot
 * 2019-02-12 16:20:14
 */
@Getter
@Setter
@ToString
public class APerformanceAccordQueryParam extends SqlQueryParam {
       
    /**id*/
    private String id;
    /**业务模块编号*/
    private String perfModelCode;
    /**考评条件*/
    private String accordingName;
    /**考评标准*/
    private String accordingStandard;
    /**个人分数*/
    private Double personalScore;
    /**班组分数*/
    private Double teamScore;
    /**是否删除*/
    private Integer deleted;
    /**创建者*/
    private String creator;
    /**创建时间*/
    private Date createtime;
    /**修改者*/
    private String modifor;
    /**修改时间*/
    private Date modifytime;
    private String accordCode;
}
