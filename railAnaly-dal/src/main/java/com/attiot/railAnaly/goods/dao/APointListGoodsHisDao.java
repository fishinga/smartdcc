/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.goods.dao;

import com.attiot.railAnaly.goods.entity.APointListGoodsHis;
import com.attiot.railAnaly.goods.entity.PointList;
import com.attiot.railAnaly.goods.entity.PointListGoods;
import com.attiot.railAnaly.goods.param.APointListGoodsHisQueryParam;

import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 请点工单作业牌关系
 * @author attiot
 * 2018-05-14 09:52:44
 */
@Repository
public interface APointListGoodsHisDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(APointListGoodsHis bo);

	public void batchInsert(List<PointListGoods> list);



	void create(PointListGoods bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(APointListGoodsHis bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	APointListGoodsHis getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	APointListGoodsHis getByParam(APointListGoodsHisQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<APointListGoodsHis> query(APointListGoodsHisQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(APointListGoodsHisQueryParam param);

	public void updateModifor(Map params);
}
