/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.category.dao;

import com.attiot.railAnaly.category.entity.ABorrowCategory;
import com.attiot.railAnaly.category.param.ABorrowCategoryQueryParam;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 物品种类
 *
 * @author attiot
 * 2018-04-09 13:33:30
 */
@Repository
public interface ABorrowCategoryDao {

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-09 13:33:30
     */
    List<ABorrowCategory> query(ABorrowCategoryQueryParam param);


    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-09 13:33:30
     */
    long queryCount(ABorrowCategoryQueryParam param);

    public List<ABorrowCategory> queryByPreCode(ABorrowCategoryQueryParam param);


}
