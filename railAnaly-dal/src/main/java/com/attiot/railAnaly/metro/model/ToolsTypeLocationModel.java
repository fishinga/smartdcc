package com.attiot.railAnaly.metro.model;

/**工器具-关系维护信息
 * @author Administrator
 * 2018-04-19 10:01
 **/

import lombok.Data;

@Data
public class ToolsTypeLocationModel {

    private  String id;
    /**工器具类型*/
    private String toolsType;
    /**存放位置*/
    private String storageLocation;
    private String storageLocationText;
    /**位置编码*/
    private String locationCode;
    /**状态*/
    private String status;
    private String statusText;
    /**修改人id*/
    private String updateUserid;

}
