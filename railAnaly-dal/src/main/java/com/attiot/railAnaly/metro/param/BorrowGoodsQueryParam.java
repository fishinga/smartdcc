/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.metro.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import com.attiot.railAnaly.foundation.SqlQueryParam;

/**
 * 物品信息
 * @author attiot
 * 2018-04-04 15:10:45
 */
@Getter
@Setter
@ToString
public class BorrowGoodsQueryParam extends SqlQueryParam {
	
    // 
    private String	id;
    // 分类
    private String	category;
    // 编号
    private String	code;
    // 物品名称
    private String	name;
    // 单位
    private String	unit;
    // 借用状态
    private Integer	state;
    // 备注
    private String	remark;
    // 创建者
    private String	creator;
    // 创建时间
    private Date	createtime;
    // 修改者
    private String	modifor;
    // 修改时间
    private Date	modifytime;

}
