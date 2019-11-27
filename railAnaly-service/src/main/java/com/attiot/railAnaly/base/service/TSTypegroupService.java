/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attiot.railAnaly.base.dao.TSTypegroupDao;
import com.attiot.railAnaly.base.entity.TSTypegroup;
import com.attiot.railAnaly.base.param.TSTypegroupQueryParam;
import com.attiot.railAnaly.foundation.Page;

/**
 * 
 * @author attiot
 * 2017-09-25 10:02:30
 */
@Service
@Transactional
public class TSTypegroupService {

	@Autowired
	private TSTypegroupDao tSTypegroupDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TSTypegroup bo) {
		tSTypegroupDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TSTypegroup bo) {
		tSTypegroupDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		tSTypegroupDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TSTypegroup getById(String id) {
		return tSTypegroupDao.getById(id);
	}

	/**
	 * 获取实体对象
	 * @param code
	 */
	public TSTypegroup getByCode(String code) {
		TSTypegroupQueryParam param = new TSTypegroupQueryParam();
		param.setTypegroupcode(code);
		return tSTypegroupDao.getByParam(param);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TSTypegroup getByParam(TSTypegroupQueryParam param) {
		return tSTypegroupDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TSTypegroup> query(TSTypegroupQueryParam param) {
		Page<TSTypegroup> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(tSTypegroupDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.emptyList());
		} else {
			page.setResults(tSTypegroupDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TSTypegroupQueryParam param) {
		return tSTypegroupDao.queryCount(param);
	}
}
