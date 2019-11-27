/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.goods.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import com.attiot.railAnaly.foundation.SqlQueryParam;

/**
 * 请点工单
 * @author attiot
 * 2018-04-11 17:29:04
 */
@Getter
@Setter
@ToString
public class PointListQueryParam extends SqlQueryParam {
	
    // 
    private String	id;
    // 列车号
    private String	trainNo;
    // 上级
    private String	parentId;
    // a_please_point:id
    private String	ppointId;
    // 请点内容
    private String	ppointContents;
    // 是否有电作业:1有：0无,2有/无电作业
    private Integer	isElectric;
    // 开始作业时间
    private Date	startJob;
    // 请点时长
    private Date	ppointDuration;
    // 创建者
    private String	creator;
    // 创建时间
    private Date	createtime;
    // 修改者
    private String	modifor;
    // 修改时间
    private Date	modifytime;

}
