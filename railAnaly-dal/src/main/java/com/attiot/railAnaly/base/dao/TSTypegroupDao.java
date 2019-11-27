/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.dao;

import com.attiot.railAnaly.base.entity.TSTypegroup;
import com.attiot.railAnaly.base.param.TSTypegroupQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author attiot
 * 2017-09-25 10:02:30
 */
@Repository
public interface TSTypegroupDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TSTypegroup bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TSTypegroup bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TSTypegroup getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TSTypegroup getByParam(TSTypegroupQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TSTypegroup> query(TSTypegroupQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TSTypegroupQueryParam param);
}
