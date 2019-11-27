/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.map.service;

import com.attiot.railAnaly.map.entity.OaZnearea;
import com.attiot.railAnaly.map.dao.OaZneareaDao;
import com.attiot.railAnaly.map.param.OaZneareaQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 考勤区域
 * @author attiot
 * 2018-09-04 11:14:24
 */
@Service
public class OaZneareaService {

	@Autowired
	private OaZneareaDao oaZneareaDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(OaZnearea bo) {
		oaZneareaDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(OaZnearea bo) {
		oaZneareaDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		oaZneareaDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public OaZnearea getById(String id) {
		return oaZneareaDao.getById(id);
	}

	/**
	 * 查询实体对象
	 */
	public OaZnearea getByParam(OaZneareaQueryParam param) {
		return oaZneareaDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<OaZnearea> query(OaZneareaQueryParam param) {
		Page<OaZnearea> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(oaZneareaDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(oaZneareaDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(OaZneareaQueryParam param) {
		return oaZneareaDao.queryCount(param);
	}


}
