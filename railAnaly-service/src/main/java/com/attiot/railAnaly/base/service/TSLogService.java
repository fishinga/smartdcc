/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.service;

import com.attiot.railAnaly.base.entity.TSLog;
import com.attiot.railAnaly.base.dao.TSLogDao;
import com.attiot.railAnaly.base.param.TSLogQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 
 * @author attiot
 * 2017-12-04 09:22:25
 */
@Service
@Transactional
public class TSLogService {

	@Autowired
	private TSLogDao tSLogDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TSLog bo) {
		tSLogDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TSLog bo) {
		tSLogDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		tSLogDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TSLog getById(String id) {
		return tSLogDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TSLog getByParam(TSLogQueryParam param) {
		return tSLogDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TSLog> query(TSLogQueryParam param) {
		Page<TSLog> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(tSLogDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(tSLogDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TSLogQueryParam param) {
		return tSLogDao.queryCount(param);
	}
}
