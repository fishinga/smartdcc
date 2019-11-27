/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.service;

import com.attiot.railAnaly.task.entity.TaskShiftSchedulerHoliday;
import com.attiot.railAnaly.task.dao.TaskShiftSchedulerHolidayDao;
import com.attiot.railAnaly.task.param.TaskShiftSchedulerHolidayQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 排班计划
 * @author attiot
 * 2018-04-19 19:09:31
 */
@Service
public class TaskShiftSchedulerHolidayService {

	@Autowired
	private TaskShiftSchedulerHolidayDao taskShiftSchedulerHolidayDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TaskShiftSchedulerHoliday bo) {
		taskShiftSchedulerHolidayDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TaskShiftSchedulerHoliday bo) {
		taskShiftSchedulerHolidayDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		taskShiftSchedulerHolidayDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TaskShiftSchedulerHoliday getById(String id) {
		return taskShiftSchedulerHolidayDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TaskShiftSchedulerHoliday getByParam(TaskShiftSchedulerHolidayQueryParam param) {
		return taskShiftSchedulerHolidayDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TaskShiftSchedulerHoliday> query(TaskShiftSchedulerHolidayQueryParam param) {
		Page<TaskShiftSchedulerHoliday> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(taskShiftSchedulerHolidayDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(taskShiftSchedulerHolidayDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TaskShiftSchedulerHolidayQueryParam param) {
		return taskShiftSchedulerHolidayDao.queryCount(param);
	}
}
