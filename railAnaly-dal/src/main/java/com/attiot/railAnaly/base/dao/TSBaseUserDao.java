/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.dao;

import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.param.TSBaseUserQueryParam;
import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * InnoDB free: 600064 kB; (`departid`) REFER `jeecg/t_s_depart
 * @author attiot
 * 2017-12-13 10:25:32
 */
@Repository
public interface TSBaseUserDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TSBaseUser bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TSBaseUser bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TSBaseUser getById(String id);

	public List<TSBaseUser> getByIds(String ids);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TSBaseUser getByParam(TSBaseUserQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TSBaseUser> query(TSBaseUserQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TSBaseUserQueryParam param);

	public List<TSBaseUser> getTSBaseUserListByRoleCode(String roleCode);

	public List<TSBaseUser> getGroupManagerByUserId(String userId);


	public List<TSBaseUser> getByRoleCodeAndDepartId(Map params);
}
