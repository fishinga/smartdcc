/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.goods.dao;

import com.attiot.railAnaly.goods.entity.PointList;
import com.attiot.railAnaly.goods.entity.PointListGoods;
import com.attiot.railAnaly.goods.param.PointListGoodsQueryParam;
import java.util.List;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 请点工单作业牌关系
 * @author attiot
 * 2018-04-11 17:27:16
 */
@Repository
public interface PointListGoodsDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(PointListGoods bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(PointListGoods bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);


	public void deleteGoods(String goodsId);


	/**
	 * 获取实体对象
	 * @param id
	 */
	PointListGoods getById(String id);

	public List<PointListGoods> getByCreator(String creator);

	/**
	 * 查询实体对象
	 * @param param
	 */
	PointListGoods getByParam(PointListGoodsQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<PointListGoods> query(PointListGoodsQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(PointListGoodsQueryParam param);


	void batchDeleteByGoodIds(String brandIds);

	public List<PointListGoods> getByGoodsId(@Param("goodsId") String goodsId);

	public List<PointListGoods> getByGoodsIds(String goodsIds);

	public void batchInsert(List<PointListGoods> list);

	public void batchInsertHis(List<PointListGoods> list);

	public List<PointListGoods> getPointListGoodsByTrainNo(String trainNo);

	public void deleteByGoodsIds(String goodsIds);
}
