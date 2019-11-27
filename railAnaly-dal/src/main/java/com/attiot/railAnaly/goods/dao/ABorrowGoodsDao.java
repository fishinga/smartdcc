/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.goods.dao;

import com.attiot.railAnaly.goods.entity.ABorrowGoods;
import com.attiot.railAnaly.goods.entity.PointListGoods;
import com.attiot.railAnaly.goods.param.ABorrowGoodsQueryParam;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 物品信息
 *
 * @author attiot
 * 2018-04-09 11:27:13
 */
@Repository
public interface ABorrowGoodsDao {

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-09 11:27:13
     */
    List<ABorrowGoods> query(ABorrowGoodsQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-09 11:27:13
     */
    long queryCount(ABorrowGoodsQueryParam param);

    /**
     * 根据物品ID，查询相关信息
     * @param ids
     * @return
     */
    public List<ABorrowGoods> getByIds(String[] ids);

    public List<Map<String,Object>> getGoodsCarNo(Map map);

    public List<Map<String,Object>> getContentsCarNo(Map map);


    public List<Map<String,Object>> getGoodsByUser( Map map );

    public Long getGoodsCountByUser(Map map);

    public ABorrowGoods getById(String id);

    public void updateStateByGoodIds(Map params);

    /**
     * 修改
     * @param bo
     */
    void editState(ABorrowGoods bo);

}
