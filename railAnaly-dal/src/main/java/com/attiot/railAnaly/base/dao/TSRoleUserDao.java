/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.dao;

import com.attiot.railAnaly.base.entity.TSRoleUser;
import com.attiot.railAnaly.base.param.TSRoleUserQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * InnoDB free: 599040 kB; (`userid`) REFER `jeecg/t_s_user`(`i
 * @author attiot
 * 2017-09-13 10:20:17
 */
@Repository
public interface TSRoleUserDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TSRoleUser bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TSRoleUser bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TSRoleUser getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TSRoleUser getByParam(TSRoleUserQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TSRoleUser> query(TSRoleUserQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TSRoleUserQueryParam param);
	
	/**
	 * deleteRoleUser
	 * @param id
	 */
	public void deleteRoleUser(String id);
}
