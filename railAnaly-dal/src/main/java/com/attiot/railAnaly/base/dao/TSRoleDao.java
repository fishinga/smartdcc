/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.dao;

import com.attiot.railAnaly.base.entity.TSRole;
import com.attiot.railAnaly.base.param.TSRoleQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author attiot
 * 2018-05-02 10:37:05
 */
@Repository
public interface TSRoleDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TSRole bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TSRole bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TSRole getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TSRole getByParam(TSRoleQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TSRole> query(TSRoleQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TSRoleQueryParam param);

	public List<TSRole> getRoleCode(String userId);

	public List<TSRole> getByUserId(String userId);
}
