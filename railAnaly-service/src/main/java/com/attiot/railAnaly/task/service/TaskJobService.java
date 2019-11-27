/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.service;

import com.attiot.railAnaly.task.entity.TaskJob;
import com.attiot.railAnaly.task.dao.TaskJobDao;
import com.attiot.railAnaly.task.param.TaskJobQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 作业包基本信息(系统修)
 * @author attiot
 * 2018-04-18 17:25:23
 */
@Service
public class TaskJobService {

	@Autowired
	private TaskJobDao taskJobDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TaskJob bo) {
		taskJobDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TaskJob bo) {
		taskJobDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		taskJobDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TaskJob getById(String id) {
		return taskJobDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TaskJob getByParam(TaskJobQueryParam param) {
		return taskJobDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TaskJob> query(TaskJobQueryParam param) {
		Page<TaskJob> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(taskJobDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(taskJobDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TaskJobQueryParam param) {
		return taskJobDao.queryCount(param);
	}
}
