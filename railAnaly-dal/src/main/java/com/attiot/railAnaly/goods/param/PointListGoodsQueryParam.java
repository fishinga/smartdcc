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
 * 请点工单作业牌关系
 * @author attiot
 * 2018-04-11 17:27:16
 */
@Getter
@Setter
@ToString
public class PointListGoodsQueryParam extends SqlQueryParam {
	
    // 
    private String	id;
    // 
    private String	trainNo;
    // 牌ID
    private String	goodsId;
    // 牌名称
    private String	goodsName;

}
