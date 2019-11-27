/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.dao;

import com.attiot.railAnaly.base.entity.TSUserOrg;
import com.attiot.railAnaly.base.param.TSUserOrgQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author attiot
 * 2017-09-14 10:31:09
 */
@Repository
public interface TSUserOrgDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TSUserOrg bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TSUserOrg bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TSUserOrg getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TSUserOrg getByParam(TSUserOrgQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TSUserOrg> query(TSUserOrgQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TSUserOrgQueryParam param);
	
	/**
	 * 删除TSUserOrg
	 * @param userId
	 */
	public void deleteUserOrg(String userId);

	/***
	 * 获取根据角色获取用户ID
	 * @param assginIds
	 * @return
	 */
	public String getUserByRole(String assginIds);

	/***
	 * 根据org 获取用户ID
	 * @param assginIds
	 * @return
	 */
	public String getUserByOrg(String assginIds);

	/**
	 * 根据当前用户ID获取工班长ID
	 * @param userId
	 * @return
	 */
	public String getUserByCurUser(String userId);
}
