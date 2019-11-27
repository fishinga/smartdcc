/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.dao;

import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.entity.TSUser;
import com.attiot.railAnaly.base.param.TSUserQueryParam;
import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * InnoDB free: 599040 kB; (`id`) REFER `jeecg/t_s_base_user`(`
 * @author attiot
 * 2017-12-13 10:13:29
 */
@Repository
public interface TSUserDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TSUser bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TSUser bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TSUser getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TSUser getByParam(TSUserQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TSUser> query(TSUserQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TSUserQueryParam param);

	List<Map<String ,Object>> getList();

	public List<Map<String ,Object>> getTSBaseUserListByUserIds(String userIds);

	public List<TSBaseUser> getTSBaseUserListByRoleCode(String roleCode);

	public List<TSBaseUser> getTSBaseUserListByDepartId(String departId);

	public List<Map<String,Object>> getUserListByIds(String ids);

	List<Map<String,String>> getUserDepartList();
}
