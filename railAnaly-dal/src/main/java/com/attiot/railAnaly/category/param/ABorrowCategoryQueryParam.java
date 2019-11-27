/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.category.param;

import com.attiot.railAnaly.foundation.SqlQueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 物品种类
 * @author attiot
 * 2018-04-09 13:33:30
 */
@Getter
@Setter
@ToString
public class ABorrowCategoryQueryParam extends SqlQueryParam {
       
    /***/
    private String id;
    /**名称*/
    private String name;
    /**parentid*/
    private String parentid;

    private String code;

}
