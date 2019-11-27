/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.service;

import com.attiot.railAnaly.task.entity.TaskScheduler;
import com.attiot.railAnaly.task.dao.TaskSchedulerDao;
import com.attiot.railAnaly.task.param.TaskSchedulerQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 调度计划
 * @author attiot
 * 2018-04-19 19:31:52
 */
@Service
public class TaskSchedulerService {

	@Autowired
	private TaskSchedulerDao taskSchedulerDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TaskScheduler bo) {
		taskSchedulerDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TaskScheduler bo) {
		taskSchedulerDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		taskSchedulerDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TaskScheduler getById(String id) {
		return taskSchedulerDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TaskScheduler getByParam(TaskSchedulerQueryParam param) {
		return taskSchedulerDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TaskScheduler> query(TaskSchedulerQueryParam param) {
		Page<TaskScheduler> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(taskSchedulerDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(taskSchedulerDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TaskSchedulerQueryParam param) {
		return taskSchedulerDao.queryCount(param);
	}
}
