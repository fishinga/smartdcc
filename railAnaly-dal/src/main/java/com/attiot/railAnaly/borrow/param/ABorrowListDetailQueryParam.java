/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.borrow.param;

import com.attiot.railAnaly.foundation.SqlQueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 借用物品明细
 * @author attiot
 * 2018-04-10 19:46:33
 */
@Getter
@Setter
@ToString
public class ABorrowListDetailQueryParam extends SqlQueryParam {
       
    /***/
    private String id;
    /**借用清单Id*/
    private String borrowListId;
    /**编号*/
    private String code;
    /**单位*/
    private String unit;
    /**借用物品ID*/
    private String borrowGoodsId;
    /**借用物品名称*/
    private String borrowGoodsName;
    /**借用物品类型*/
    private String borrowGoodsCategory;
    /**类型名称*/
    private String categoryName;

}
