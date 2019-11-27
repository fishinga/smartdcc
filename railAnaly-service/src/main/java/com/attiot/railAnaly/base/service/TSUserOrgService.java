/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.service;

import com.attiot.railAnaly.base.dao.TSUserOrgDao;
import com.attiot.railAnaly.base.entity.TSUserOrg;
import com.attiot.railAnaly.base.param.TSUserOrgQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * 
 * @author attiot
 * 2017-09-14 10:31:09
 */
@Service
@Transactional
public class TSUserOrgService {

	@Autowired
	private TSUserOrgDao tSUserOrgDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String create(TSUserOrg bo) {
		tSUserOrgDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TSUserOrg bo) {
		tSUserOrgDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		tSUserOrgDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TSUserOrg getById(String id) {
		return tSUserOrgDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TSUserOrg getByParam(TSUserOrgQueryParam param) {
		return tSUserOrgDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TSUserOrg> query(TSUserOrgQueryParam param) {
		Page<TSUserOrg> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(tSUserOrgDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(tSUserOrgDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TSUserOrgQueryParam param) {
		return tSUserOrgDao.queryCount(param);
	}
	
	public void deleteUserOrg(String userId){
		tSUserOrgDao.deleteUserOrg(userId);
	}

	/**
	 * 根据userId获取用户机构信息。
	 * @param userId
	 * @return
	 */
	public TSUserOrg getByUserId(String userId) {
		TSUserOrgQueryParam param = new TSUserOrgQueryParam();
		param.setUserId(userId);
		return tSUserOrgDao.getByParam(param);
	}

	/***
	 * 获取根据角色获取用户ID
	 * @param assginIds
	 * @return
	 */
	public String getUserByRole(String assginIds){
		return  tSUserOrgDao.getUserByRole(assginIds);
	}

	/***
	 * 根据org 获取用户ID
	 * @param assginIds
	 * @return
	 */
	public String getUserByOrg(String assginIds){
		return  tSUserOrgDao.getUserByOrg(assginIds);
	}

	/**
	 * 根据当前用户ID获取工班长ID
	 * @param creator
	 * @return
	 */
	public String getUserByCurUser(String creator){
		return  tSUserOrgDao.getUserByCurUser(creator);
	}
}
