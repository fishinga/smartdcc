/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.MetroInfo;
import com.attiot.railAnaly.metro.entity.MetroJob;
import com.attiot.railAnaly.metro.entity.ToolsRelation;
import com.attiot.railAnaly.metro.param.MetroInfoQueryParam;
import com.attiot.railAnaly.metro.param.MetroJobQueryParam;
import com.attiot.railAnaly.metro.param.ToolsRelationQueryParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  工器具  类型、位置关系维护
 * @author attiot
 * 2018-04-03 10:18:42
 */
@Repository
public interface ToolsRelationDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(ToolsRelation bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(ToolsRelation bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	ToolsRelation getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	ToolsRelation getByParam(ToolsRelationQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<ToolsRelation> query(ToolsRelationQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(ToolsRelationQueryParam param);


	/**
	 * 获取列表信息
	 * @param param
	 */
	List<ToolsRelation> getByToolsType(ToolsRelationQueryParam param);

	/**
	 * 获取类型列表信息
	 * @param param
	 */
	List getGroupyByToolsType(ToolsRelationQueryParam param);
}
