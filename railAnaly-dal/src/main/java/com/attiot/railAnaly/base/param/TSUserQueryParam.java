/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import com.attiot.railAnaly.foundation.SqlQueryParam;

/**
 * InnoDB free: 599040 kB; (`id`) REFER `jeecg/t_s_base_user`(`
 * @author attiot
 * 2017-12-13 10:13:29
 */
@Getter
@Setter
@ToString
public class TSUserQueryParam extends SqlQueryParam {
	
    // id
    private String	id;
    // 邮箱
    private String	email;
    // 手机号
    private String	mobilephone;
    // 办公座机
    private String	officephone;
    // 签名文件
    private String	signaturefile;
    // 修改人
    private String	updateName;
    // 修改时间
    private Date	updateDate;
    // 修改人id
    private String	updateBy;
    // 创建人
    private String	createName;
    // 创建时间
    private Date	createDate;
    // 创建人id
    private String	createBy;

}
