/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.service;

import com.attiot.railAnaly.base.entity.TSDepart;
import com.attiot.railAnaly.base.dao.TSDepartDao;
import com.attiot.railAnaly.base.param.TSDepartQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author attiot
 * 2017-12-13 14:02:51
 */
@Service
@Transactional
public class TSDepartService {

	@Autowired
	private TSDepartDao tSDepartDao;

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TSDepart getById(String id) {
		return tSDepartDao.getById(id);
	}

	public List<TSDepart> getDepartListByUserId(String userId) {
		return tSDepartDao.getDepartListByUserId(userId);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TSDepart> query(TSDepartQueryParam param) {
		Page<TSDepart> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(tSDepartDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(tSDepartDao.query(param));
		}
		return page;
	}

	public List<String> byManager(String userId){
		return tSDepartDao. byManager(userId);
	}


	/**
	 * 获取作业人员列表
	 * @param departId
	 * @return
	 */
	public List<Map<String ,Object>> getWorkUserList(String departId){
		return tSDepartDao. getWorkUserList(departId);
	}
}
