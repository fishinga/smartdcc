/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.dao;

import com.attiot.railAnaly.task.entity.TaskTableData;
import com.attiot.railAnaly.task.param.TaskTableDataQueryParam;
import java.util.List;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author attiot
 * 2018-05-28 10:34:26
 */
@Repository
public interface TaskTableDataDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TaskTableData bo);

	public void batchInsert(List<TaskTableData> list);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TaskTableData bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TaskTableData getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TaskTableData getByParam(TaskTableDataQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TaskTableData> query(TaskTableDataQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TaskTableDataQueryParam param);

	/***
	 * 根据pointID 和 code 查询
	 * @param pointId
	 * @param colCode
	 * @return
	 */
	List<TaskTableData> getByPointIdCode(@Param("pointId") String pointId,@Param("colCode") String colCode);
}
