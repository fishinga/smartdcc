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
 * 
 * @author attiot
 * 2017-09-19 09:35:14
 */
@Getter
@Setter
@ToString
public class TSAttachmentQueryParam extends SqlQueryParam {
	
    // ID
    private String	id;
    // 附件内容
    private String	attachmentcontent;
    // 附件名称
    private String	attachmenttitle;
    // 业务类主键
    private String	businesskey;
    // 创建时间
    private Date	createdate;
    // 扩展名
    private String	extend;
    // note
    private String	note;
    // 附件路径
    private String	realpath;
    // 子类名称全路径
    private String	subclassname;
    // swf格式路径
    private String	swfpath;
    // BUSENTITYNAME
    private String	busentityname;
    // INFOTYPEID
    private String	infotypeid;
    // 用户ID
    private String	userid;

}
