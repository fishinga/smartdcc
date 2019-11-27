package com.attiot.railAnaly.foundation;

import lombok.Data;

import java.io.Serializable;

/**
 * 功能/模块：<br/>
 * 类描述：<br/>
 * 修订历史：：<br/>
 * 日期  作者  参考  描述：<br/>
 *
 * @author dengsc
 * @version 1.0
 * @see
 */
@Data
public class Session implements Serializable {

	private String userId;
	private String userName;
	private String realName;
	private String departId; // 所属机构
	private String companyCode;// 所属分公司
	private String stationCode;// 所属分公司
	/* private String roleCode; */
	// private String userOnlineId;
	/* private String orgName; */
	private String clientType;
	private char type;
	private String clientId;
	private Integer userType; // 1-客户 2-员工
	private String accessToken;

}
