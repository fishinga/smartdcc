/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.point.dao;

import com.attiot.railAnaly.point.entity.PointPleasePower;
import com.attiot.railAnaly.point.param.PointPleasePowerQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 断送电
 * @author attiot
 * 2018-09-03 16:39:13
 */
@Repository
public interface PointPleasePowerDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(PointPleasePower bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(PointPleasePower bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	PointPleasePower getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	PointPleasePower getByParam(PointPleasePowerQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<PointPleasePower> query(PointPleasePowerQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(PointPleasePowerQueryParam param);

	public PointPleasePower getByPointId(String pointId);
}
