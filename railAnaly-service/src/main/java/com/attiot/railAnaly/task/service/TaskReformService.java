/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.service;

import com.attiot.railAnaly.task.entity.TaskReform;
import com.attiot.railAnaly.task.dao.TaskReformDao;
import com.attiot.railAnaly.task.param.TaskReformQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 普查整改作业信息
 * @author attiot
 * 2018-04-18 17:31:16
 */
@Service
public class TaskReformService {

	@Autowired
	private TaskReformDao taskReformDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TaskReform bo) {
		taskReformDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TaskReform bo) {
		taskReformDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		taskReformDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TaskReform getById(String id) {
		return taskReformDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TaskReform getByParam(TaskReformQueryParam param) {
		return taskReformDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TaskReform> query(TaskReformQueryParam param) {
		Page<TaskReform> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(taskReformDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(taskReformDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TaskReformQueryParam param) {
		return taskReformDao.queryCount(param);
	}
}
