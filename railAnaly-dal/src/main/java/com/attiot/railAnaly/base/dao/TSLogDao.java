/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.dao;

import com.attiot.railAnaly.base.entity.TSLog;
import com.attiot.railAnaly.base.param.TSLogQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author attiot
 * 2017-12-04 09:22:25
 */
@Repository
public interface TSLogDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TSLog bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TSLog bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TSLog getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TSLog getByParam(TSLogQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TSLog> query(TSLogQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TSLogQueryParam param);
}
