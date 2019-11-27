/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.borrow.dao;

import com.attiot.railAnaly.borrow.entity.ABorrowListDetail;
import com.attiot.railAnaly.borrow.param.ABorrowListDetailQueryParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 借用物品明细
 *
 * @author attiot
 * 2018-04-10 19:46:33
 */
@Repository
public interface ABorrowListDetailDao {
    /**
     * 新增
     *
     * @param aBorrowListDetail 参数
     *
     * @author attiot
     * 2018-04-10 19:46:33
     */
    void insert(ABorrowListDetail aBorrowListDetail);

    /**
     * 修改
     *
     * @param aBorrowListDetail 参数
     *
     * @author attiot
     * 2018-04-10 19:46:33
     */
    void update(ABorrowListDetail aBorrowListDetail);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-10 19:46:33
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-10 19:46:33
     */
    List<ABorrowListDetail> query(ABorrowListDetailQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-10 19:46:33
     */
    ABorrowListDetail getByParam(ABorrowListDetailQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-10 19:46:33
     */
    long queryCount(ABorrowListDetailQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-10 19:46:33
     */
    ABorrowListDetail getById(String id);

    /***
     * 根据借用单获取借用明细信息
     * @param borrowIds
     * @return
     */
    List<ABorrowListDetail> getByBorrowId(String[] borrowIds);

    /***
     *  根据请点ID 查询借用详情
     * @param pointId
     * @return
     */
    public List<ABorrowListDetail> getBorrowByPointId(String pointId);


    public List<ABorrowListDetail> getNeedGivebackBorrowDetailByPointId(String pointId);

    /***
     * 需要归还的借用物品
     * @param param
     * @return
     */
    public List getBorrowReturn(HashMap<String,Object> param);

    public Long getBorrowReturnCount(Map params);

    /***
     * 根据商品ID获取借用明细信息
     * @param goodIds
     * @return
     */
    public List<ABorrowListDetail> getByGoodId(String[] goodIds);

    /***
     * 根据借用单号查询
     * @param returnNum
     * @return
     */
    public List<ABorrowListDetail> getByReturnNum(String returnNum);



}
