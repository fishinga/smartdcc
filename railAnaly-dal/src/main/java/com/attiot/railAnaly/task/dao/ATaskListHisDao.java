/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.dao;

import com.attiot.railAnaly.task.entity.ATaskListHis;
import com.attiot.railAnaly.task.param.ATaskListHisQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 历史任务工单
 * @author attiot
 * 2018-05-21 11:05:08
 */
@Repository
public interface ATaskListHisDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(ATaskListHis bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(ATaskListHis bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	ATaskListHis getById(String id);

	List<ATaskListHis> getByIds(String ids);

	/**
	 * 查询实体对象
	 * @param param
	 */
	ATaskListHis getByParam(ATaskListHisQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<ATaskListHis> query(ATaskListHisQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(ATaskListHisQueryParam param);
}
