package com.attiot.railAnaly.metro.entity;

import lombok.Data;

import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 工器具类型位置关系维护表
 * @author onlineGenerator
 * @date 2018-04-18 09:40:29
 * @version V1.0   
 *
 */
 @Data
public class ToolsRelation implements java.io.Serializable {
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
	private String toolsTypeText;
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
