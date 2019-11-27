/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.metro.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.dao.ToolsRelationDao;
import com.attiot.railAnaly.metro.entity.ToolsRelation;
import com.attiot.railAnaly.metro.param.ToolsRelationQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 车辆作业状态
 * @author attiot
 * 2018-04-04 15:12:08
 */
@Service
public class ToolsRelationService {

	@Autowired
	private ToolsRelationDao toolsRelationDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(ToolsRelation bo) {
		toolsRelationDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(ToolsRelation bo) {
		toolsRelationDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		toolsRelationDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public ToolsRelation getById(String id) {
		return toolsRelationDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public ToolsRelation getByParam(ToolsRelationQueryParam param) {
		return toolsRelationDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<ToolsRelation> query(ToolsRelationQueryParam param) {
		Page<ToolsRelation> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(toolsRelationDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(toolsRelationDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(ToolsRelationQueryParam param) {
		return toolsRelationDao.queryCount(param);
	}


	/**
	 * 获取实体对象列表
	 * @param param
	 */
	public List<ToolsRelation> getByToolsType(ToolsRelationQueryParam param) {
		return toolsRelationDao.getByToolsType(param);
	}


	/**
	 * 获取类型分组对象列表
	 * @param param
	 */
	public List getGroupyByToolsType(ToolsRelationQueryParam param) {
		return toolsRelationDao.getGroupyByToolsType(param);
	}
}
