/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.dao;

import com.attiot.railAnaly.task.entity.TaskShiftSchedulerHoliday;
import com.attiot.railAnaly.task.param.TaskShiftSchedulerHolidayQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 排班计划
 * @author attiot
 * 2018-04-19 19:09:31
 */
@Repository
public interface TaskShiftSchedulerHolidayDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TaskShiftSchedulerHoliday bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TaskShiftSchedulerHoliday bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TaskShiftSchedulerHoliday getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TaskShiftSchedulerHoliday getByParam(TaskShiftSchedulerHolidayQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TaskShiftSchedulerHoliday> query(TaskShiftSchedulerHolidayQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TaskShiftSchedulerHolidayQueryParam param);

	public TaskShiftSchedulerHoliday getByWorkdate(String workdate);
}
