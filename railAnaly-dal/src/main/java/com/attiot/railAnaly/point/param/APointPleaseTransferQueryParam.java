package com.attiot.railAnaly.point.param;

import com.attiot.railAnaly.foundation.SqlQueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class APointPleaseTransferQueryParam extends SqlQueryParam {
    /***/
    private String id;
    /**请点ID*/
    private String pointPleaseId;
    /**原持有者ID*/
    private String oldHolderId;
    /**原持有人名称*/
    private String oldHolderName;
    /**新持有人*/
    private String newHolderId;
    /**新持有人名称*/
    private String newHolderName;
    /**创建时间*/
    private Date createTime;
}
