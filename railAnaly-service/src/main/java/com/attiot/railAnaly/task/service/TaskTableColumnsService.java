/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.service;

import com.attiot.railAnaly.task.entity.TaskTableColumns;
import com.attiot.railAnaly.task.dao.TaskTableColumnsDao;
import com.attiot.railAnaly.task.param.TaskTableColumnsQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 
 * @author attiot
 * 2018-05-28 10:33:05
 */
@Service
public class TaskTableColumnsService {

	@Autowired
	private TaskTableColumnsDao taskTableColumnsDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TaskTableColumns bo) {
		taskTableColumnsDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TaskTableColumns bo) {
		taskTableColumnsDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		taskTableColumnsDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TaskTableColumns getById(String id) {
		return taskTableColumnsDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TaskTableColumns getByParam(TaskTableColumnsQueryParam param) {
		return taskTableColumnsDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TaskTableColumns> query(TaskTableColumnsQueryParam param) {
		Page<TaskTableColumns> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(taskTableColumnsDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(taskTableColumnsDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TaskTableColumnsQueryParam param) {
		return taskTableColumnsDao.queryCount(param);
	}
}
