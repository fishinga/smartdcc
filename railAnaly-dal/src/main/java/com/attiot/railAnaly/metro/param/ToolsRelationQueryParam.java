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
 * 工器具  类型、位置关系维护
 * @author attiot
 * 2018-04-04 15:12:08
 */
@Getter
@Setter
@ToString
public class ToolsRelationQueryParam extends SqlQueryParam {

    /**主键*/
    private String id;
    /**创建人名称*/
    private String createName;
    /**创建人登录名称*/
    private String createBy;
    /**创建日期*/
    private Date createDate;
    /**更新人名称*/
    private String updateName;
    /**更新人登录名称*/
    private String updateBy;
    /**更新日期*/
    private Date updateDate;
    /**所属部门*/
    private String sysOrgCode;
    /**所属公司*/
    private String sysCompanyCode;
    /**流程状态*/
    private String bpmStatus;
    /**工器具类型*/
    private String toolsType;
    /**存放位置*/
    private String storageLocation;
    /**位置编码*/
    private String locationCode;
    /**状态*/
    private String status;
    /**修改人id*/
    private String updateUserid;

}
