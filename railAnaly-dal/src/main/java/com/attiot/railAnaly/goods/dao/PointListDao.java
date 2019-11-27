/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.goods.dao;

import com.attiot.railAnaly.goods.entity.PointList;
import com.attiot.railAnaly.goods.param.PointListQueryParam;
import java.util.List;
import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 请点工单
 * @author attiot
 * 2018-04-11 17:29:04
 */
@Repository
public interface PointListDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(PointList bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(PointList bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	PointList getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	PointList getByParam(PointListQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<PointList> query(PointListQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(PointListQueryParam param);

	PointList getByTrainNo(String trainNO);

	List<PointList> getByTrainNos(String trainNOs);

	List<PointList> getParentAPointListByTrainNo(@Param(value = "trainNos")String[] trainNos);

	void removePointList(Map params);

	List<PointList> queryChildrenByTrainNo(String trainNo);

	void updateMetroStatusAndElectricByTrainNo(Map params);

	/****
	 * 更新交接情况
	 * @param params
	 */
	void updateTransfer(Map params);

	public List<PointList>  getAllParentPointList();

	public List<PointList>  getAllPointList();

	public void deleteByPPointId(String ppointId);

	public void batchUpdateMetroStatusById(List<PointList> list);

	public void updateMetroStatusById(PointList pointList);
}
