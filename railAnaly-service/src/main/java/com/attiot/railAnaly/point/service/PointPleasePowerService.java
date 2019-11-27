/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.point.service;

import com.attiot.railAnaly.point.entity.PointPleasePower;
import com.attiot.railAnaly.point.dao.PointPleasePowerDao;
import com.attiot.railAnaly.point.param.PointPleasePowerQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 断送电
 * @author attiot
 * 2018-09-03 16:39:13
 */
@Service
public class PointPleasePowerService {

	@Autowired
	private PointPleasePowerDao pointPleasePowerDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(PointPleasePower bo) {
		pointPleasePowerDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(PointPleasePower bo) {
		pointPleasePowerDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		pointPleasePowerDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public PointPleasePower getById(String id) {
		return pointPleasePowerDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public PointPleasePower getByParam(PointPleasePowerQueryParam param) {
		return pointPleasePowerDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<PointPleasePower> query(PointPleasePowerQueryParam param) {
		Page<PointPleasePower> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(pointPleasePowerDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(pointPleasePowerDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(PointPleasePowerQueryParam param) {
		return pointPleasePowerDao.queryCount(param);
	}

	public PointPleasePower getByPointId(String pointId){
		return pointPleasePowerDao.getByPointId(pointId);
	}
}
