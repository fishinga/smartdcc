/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.goods.dao;

import com.attiot.railAnaly.goods.entity.ABorrowGoodLoss;
import com.attiot.railAnaly.goods.param.ABorrowGoodLossQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 物品损耗信息
 *
 * @author attiot
 * 2018-05-02 20:21:58
 */
@Repository
public interface ABorrowGoodLossDao {
    /**
     * 新增
     *
     * @param aBorrowGoodLoss 参数
     *
     * @author attiot
     * 2018-05-02 20:21:58
     */
    void insert(ABorrowGoodLoss aBorrowGoodLoss);

    /**
     * 修改
     *
     * @param aBorrowGoodLoss 参数
     *
     * @author attiot
     * 2018-05-02 20:21:58
     */
    void update(ABorrowGoodLoss aBorrowGoodLoss);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-05-02 20:21:58
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-05-02 20:21:58
     */
    List<ABorrowGoodLoss> query(ABorrowGoodLossQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-05-02 20:21:58
     */
    ABorrowGoodLoss getByParam(ABorrowGoodLossQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-05-02 20:21:58
     */
    long queryCount(ABorrowGoodLossQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-05-02 20:21:58
     */
    ABorrowGoodLoss getById(String id);
}
