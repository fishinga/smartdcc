/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.dao;

import com.attiot.railAnaly.task.entity.TaskTemp;
import com.attiot.railAnaly.task.param.TaskTempQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 临时作业信息
 * @author attiot
 * 2018-04-18 17:34:42
 */
@Repository
public interface TaskTempDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TaskTemp bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TaskTemp bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TaskTemp getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TaskTemp getByParam(TaskTempQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TaskTemp> query(TaskTempQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TaskTempQueryParam param);
}
