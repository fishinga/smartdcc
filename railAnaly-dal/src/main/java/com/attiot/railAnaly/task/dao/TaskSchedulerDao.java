/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.dao;

import com.attiot.railAnaly.task.entity.TaskScheduler;
import com.attiot.railAnaly.task.param.TaskSchedulerQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 调度计划
 * @author attiot
 * 2018-04-19 19:31:52
 */
@Repository
public interface TaskSchedulerDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TaskScheduler bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TaskScheduler bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TaskScheduler getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TaskScheduler getByParam(TaskSchedulerQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TaskScheduler> query(TaskSchedulerQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TaskSchedulerQueryParam param);

	public void batchInsert(List<TaskScheduler> list);
}
