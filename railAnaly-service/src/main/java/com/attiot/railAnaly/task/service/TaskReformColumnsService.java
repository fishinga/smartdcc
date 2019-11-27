/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.service;

import com.attiot.railAnaly.task.entity.TaskReformColumns;
import com.attiot.railAnaly.task.dao.TaskReformColumnsDao;
import com.attiot.railAnaly.task.param.TaskReformColumnsQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 记录表定义
 * @author attiot
 * 2018-05-31 09:10:41
 */
@Service
public class TaskReformColumnsService {

	@Autowired
	private TaskReformColumnsDao taskReformColumnsDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TaskReformColumns bo) {
		taskReformColumnsDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TaskReformColumns bo) {
		taskReformColumnsDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		taskReformColumnsDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TaskReformColumns getById(String id) {
		return taskReformColumnsDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TaskReformColumns getByParam(TaskReformColumnsQueryParam param) {
		return taskReformColumnsDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TaskReformColumns> query(TaskReformColumnsQueryParam param) {
		Page<TaskReformColumns> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(taskReformColumnsDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(taskReformColumnsDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TaskReformColumnsQueryParam param) {
		return taskReformColumnsDao.queryCount(param);
	}
}
