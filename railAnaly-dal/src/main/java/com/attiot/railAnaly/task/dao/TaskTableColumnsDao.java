/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.dao;

import com.attiot.railAnaly.task.entity.TaskTableColumns;
import com.attiot.railAnaly.task.param.TaskTableColumnsQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author attiot
 * 2018-05-28 10:33:05
 */
@Repository
public interface TaskTableColumnsDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TaskTableColumns bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TaskTableColumns bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TaskTableColumns getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TaskTableColumns getByParam(TaskTableColumnsQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TaskTableColumns> query(TaskTableColumnsQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TaskTableColumnsQueryParam param);
}
