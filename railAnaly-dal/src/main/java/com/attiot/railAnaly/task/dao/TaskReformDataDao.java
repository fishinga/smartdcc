/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.dao;

import com.attiot.railAnaly.task.entity.TaskReformData;
import com.attiot.railAnaly.task.param.TaskReformDataQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 记录表内容
 * @author attiot
 * 2018-05-31 09:12:25
 */
@Repository
public interface TaskReformDataDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TaskReformData bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TaskReformData bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TaskReformData getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TaskReformData getByParam(TaskReformDataQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TaskReformData> query(TaskReformDataQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TaskReformDataQueryParam param);

	public void batchInsert(List<TaskReformData> list);
}
