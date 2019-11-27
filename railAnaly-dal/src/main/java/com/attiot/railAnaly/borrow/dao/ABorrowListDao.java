/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.borrow.dao;

import com.attiot.railAnaly.borrow.entity.ABorrowList;
import com.attiot.railAnaly.borrow.param.ABorrowListQueryParam;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 借用表
 *
 * @author attiot
 * 2018-04-10 19:45:17
 */
@Repository
public interface ABorrowListDao {
    /**
     * 新增
     *
     * @param aBorrowList 参数
     *
     * @author attiot
     * 2018-04-10 19:45:17
     */
    void insert(ABorrowList aBorrowList);

    /**
     * 修改
     *
     * @param aBorrowList 参数
     *
     * @author attiot
     * 2018-04-10 19:45:17
     */
    void update(ABorrowList aBorrowList);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-10 19:45:17
     */
    void delete(String id);

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-10 19:45:17
     */
    List<ABorrowList> query(ABorrowListQueryParam param);


    /**
     * 用example方式查询第一条记录
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-10 19:45:17
     */
    ABorrowList getByParam(ABorrowListQueryParam param);

    /**
     * 查询统计
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-10 19:45:17
     */
    long queryCount(ABorrowListQueryParam param);

    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-10 19:45:17
     */
    ABorrowList getById(String id);

    /***
     * 根据请点ID查询
     * @param pointId
     * @return
     */
    List<ABorrowList> getByPointId(String pointId);


    /**
     * 查询我的借用（借用历史）
     * @param param
     * @return
     */
    List getBorrowHis(HashMap<String,Object> param);

}
