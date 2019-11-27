/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.goods.dao;

import com.attiot.railAnaly.goods.entity.APointListHis;
import com.attiot.railAnaly.goods.entity.PointList;
import com.attiot.railAnaly.goods.param.APointListHisQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 请点工单
 * @author attiot
 * 2018-05-14 19:52:29
 */
@Repository
public interface APointListHisDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(APointListHis bo);
	void create(PointList bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(APointListHis bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	APointListHis getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	APointListHis getByParam(APointListHisQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<APointListHis> query(APointListHisQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(APointListHisQueryParam param);
}
