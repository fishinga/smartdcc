/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.service;

import com.attiot.railAnaly.base.entity.TSRole;
import com.attiot.railAnaly.base.dao.TSRoleDao;
import com.attiot.railAnaly.base.param.TSRoleQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author attiot
 * 2018-05-02 10:37:05
 */
@Service
@Transactional
public class TSRoleService {

	@Autowired
	private TSRoleDao tSRoleDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TSRole bo) {
		tSRoleDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TSRole bo) {
		tSRoleDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		tSRoleDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TSRole getById(String id) {
		return tSRoleDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TSRole getByParam(TSRoleQueryParam param) {
		return tSRoleDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TSRole> query(TSRoleQueryParam param) {
		Page<TSRole> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(tSRoleDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(tSRoleDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TSRoleQueryParam param) {
		return tSRoleDao.queryCount(param);
	}

	/**
	 * 根据当前登录用户获取roleCode
	 * @param userId
	 * @return
	 */
	public TSRole getRoleCode(String userId){
		List<TSRole> roleList = tSRoleDao.getRoleCode(userId);
		return null != roleList && roleList.size()>0?roleList.get(0):null;
	}

	public List<TSRole> getByUserId(String userId){
		return tSRoleDao.getByUserId(userId);
	}
}
