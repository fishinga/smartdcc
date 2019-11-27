/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.dao;

import com.attiot.railAnaly.task.entity.TaskJob;
import com.attiot.railAnaly.task.param.TaskJobQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 作业包基本信息(系统修)
 * @author attiot
 * 2018-04-18 17:25:23
 */
@Repository
public interface TaskJobDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TaskJob bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TaskJob bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TaskJob getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TaskJob getByParam(TaskJobQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TaskJob> query(TaskJobQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TaskJobQueryParam param);
}
