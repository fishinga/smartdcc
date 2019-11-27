/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.service;

import com.attiot.railAnaly.task.entity.TaskTable;
import com.attiot.railAnaly.task.dao.TaskTableDao;
import com.attiot.railAnaly.task.param.TaskTableQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 风险预控表
 * @author attiot
 * 2018-05-28 10:30:22
 */
@Service
public class TaskTableService {

	@Autowired
	private TaskTableDao taskTableDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TaskTable bo) {
		taskTableDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TaskTable bo) {
		taskTableDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		taskTableDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TaskTable getById(String id) {
		return taskTableDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TaskTable getByParam(TaskTableQueryParam param) {
		return taskTableDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TaskTable> query(TaskTableQueryParam param) {
		Page<TaskTable> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(taskTableDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(taskTableDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TaskTableQueryParam param) {
		return taskTableDao.queryCount(param);
	}
}
