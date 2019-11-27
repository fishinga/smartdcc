/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.service;

import com.attiot.railAnaly.task.entity.TaskTemp;
import com.attiot.railAnaly.task.dao.TaskTempDao;
import com.attiot.railAnaly.task.param.TaskTempQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 临时作业信息
 * @author attiot
 * 2018-04-18 17:34:42
 */
@Service
public class TaskTempService {

	@Autowired
	private TaskTempDao taskTempDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TaskTemp bo) {
		taskTempDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TaskTemp bo) {
		taskTempDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		taskTempDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TaskTemp getById(String id) {
		return taskTempDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TaskTemp getByParam(TaskTempQueryParam param) {
		return taskTempDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TaskTemp> query(TaskTempQueryParam param) {
		Page<TaskTemp> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(taskTempDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(taskTempDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TaskTempQueryParam param) {
		return taskTempDao.queryCount(param);
	}
}
