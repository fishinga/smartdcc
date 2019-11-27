/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.service;

import com.attiot.railAnaly.common.util.JacksonUtil;
import com.attiot.railAnaly.task.dao.ATaskListDao;
import com.attiot.railAnaly.task.dao.TaskReformColumnsDao;
import com.attiot.railAnaly.task.entity.*;
import com.attiot.railAnaly.task.dao.TaskReformDataDao;
import com.attiot.railAnaly.task.param.TaskReformDataQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 记录表内容
 * @author attiot
 * 2018-05-31 09:12:25
 */
@Service
public class TaskReformDataService {

	@Autowired
	private TaskReformDataDao taskReformDataDao;
	@Autowired
	private ATaskListDao taskListDao;
	@Autowired
	private TaskReformColumnsDao taskReformColumnsDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TaskReformData bo) {
		taskReformDataDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TaskReformData bo) {
		taskReformDataDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		taskReformDataDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TaskReformData getById(String id) {
		return taskReformDataDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TaskReformData getByParam(TaskReformDataQueryParam param) {
		return taskReformDataDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TaskReformData> query(TaskReformDataQueryParam param) {
		Page<TaskReformData> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(taskReformDataDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(taskReformDataDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TaskReformDataQueryParam param) {
		return taskReformDataDao.queryCount(param);
	}


	public void saveReformData(Map jsonMap , String taskListId){
		List list =  (List)jsonMap.get("obj");
		List<TaskReformData> dataList = new ArrayList();
		for(int k=0;k<list.size();k++){
			Map _temp = (Map)list.get(k);
			TaskReformColumns taskReformColumns = JacksonUtil.toBean(_temp.toString(),TaskReformColumns.class);
			TaskReformColumns taskReformColumns1 = taskReformColumnsDao.getById(taskReformColumns.getId());
			ATaskList taskList = taskListDao.getById(taskListId);
			TaskReformData taskReformData = new TaskReformData();
			taskReformData.setColId(taskReformColumns.getId());
			taskReformData.setColValue(taskReformColumns.getDefaultVal());
			taskReformData.setColSort(taskReformColumns1.getColSort());
			taskReformData.setColCode(taskReformColumns1.getColCode());
			taskReformData.setColName(taskReformColumns1.getColName());
			taskReformData.setReformNum(taskList.getSourceNum());
			taskReformData.setReformId(taskList.getJobId());
			taskReformData.setTaskListId(taskListId);
			taskReformData.setTrainNo(taskList.getTrainNo());
			dataList.add(taskReformData);
		}
		if(null != dataList && dataList.size()>0) {
			taskReformDataDao.batchInsert(dataList);
		}
	}
}
