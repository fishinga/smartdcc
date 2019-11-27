/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.dao;

import com.attiot.railAnaly.task.entity.TaskReform;
import com.attiot.railAnaly.task.param.TaskReformQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 普查整改作业信息
 * @author attiot
 * 2018-04-18 17:31:16
 */
@Repository
public interface TaskReformDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TaskReform bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TaskReform bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TaskReform getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TaskReform getByParam(TaskReformQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TaskReform> query(TaskReformQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TaskReformQueryParam param);
}
