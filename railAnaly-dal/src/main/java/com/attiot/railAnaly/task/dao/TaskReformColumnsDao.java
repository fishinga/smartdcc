/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.dao;

import com.attiot.railAnaly.task.entity.TaskReformColumns;
import com.attiot.railAnaly.task.param.TaskReformColumnsQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 记录表定义
 * @author attiot
 * 2018-05-31 09:10:41
 */
@Repository
public interface TaskReformColumnsDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TaskReformColumns bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TaskReformColumns bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TaskReformColumns getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TaskReformColumns getByParam(TaskReformColumnsQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TaskReformColumns> query(TaskReformColumnsQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TaskReformColumnsQueryParam param);
}
