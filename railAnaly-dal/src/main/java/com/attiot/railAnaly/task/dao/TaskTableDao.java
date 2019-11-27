/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.dao;

import com.attiot.railAnaly.task.entity.TaskTable;
import com.attiot.railAnaly.task.param.TaskTableQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 风险预控表
 * @author attiot
 * 2018-05-28 10:30:22
 */
@Repository
public interface TaskTableDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TaskTable bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TaskTable bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TaskTable getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TaskTable getByParam(TaskTableQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TaskTable> query(TaskTableQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TaskTableQueryParam param);
}
