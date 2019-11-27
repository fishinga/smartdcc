/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.service;

import com.alibaba.fastjson.JSON;
import com.attiot.railAnaly.common.util.JacksonUtil;
import com.attiot.railAnaly.task.dao.TaskTableColumnsDao;
import com.attiot.railAnaly.task.entity.TaskTableColumns;
import com.attiot.railAnaly.task.entity.TaskTableData;
import com.attiot.railAnaly.task.dao.TaskTableDataDao;
import com.attiot.railAnaly.task.param.TaskTableDataQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author attiot
 * 2018-05-28 10:34:26
 */
@Service
public class TaskTableDataService {

	@Autowired
	private TaskTableDataDao taskTableDataDao;
	@Autowired
	private TaskTableColumnsDao taskTableColumnsDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TaskTableData bo) {
		taskTableDataDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TaskTableData bo) {
		taskTableDataDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		taskTableDataDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TaskTableData getById(String id) {
		return taskTableDataDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TaskTableData getByParam(TaskTableDataQueryParam param) {
		return taskTableDataDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TaskTableData> query(TaskTableDataQueryParam param) {
		Page<TaskTableData> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(taskTableDataDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(taskTableDataDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TaskTableDataQueryParam param) {
		return taskTableDataDao.queryCount(param);
	}

	public void saveTableData(Map jsonMap ,String ppointId){
		if(null != jsonMap.get("obj")) {
			List list =  (List)jsonMap.get("obj");
			List<TaskTableData> dataList = new ArrayList();
			for(int k=0;k<list.size();k++){
				Map _temp = (Map)list.get(k);
				TaskTableColumns taskTableColumns = JacksonUtil.toBean(_temp.toString(),TaskTableColumns.class);
				TaskTableColumns taskTableColumns1 = taskTableColumnsDao.getById(taskTableColumns.getId());
				TaskTableData taskTableData = new TaskTableData();
				taskTableData.setPpointId(ppointId);
				taskTableData.setColId(taskTableColumns.getId());
				taskTableData.setColValue(taskTableColumns.getDefaultVal());
				taskTableData.setTaskTableId(taskTableColumns1.getTaskTableId());
				taskTableData.setColCode(taskTableColumns1.getColCode());
				taskTableData.setColName(taskTableColumns1.getColName());
				taskTableData.setColItems(taskTableColumns1.getColItems());
				dataList.add(taskTableData);
			}
			if(null != dataList && dataList.size()>0) {
				taskTableDataDao.batchInsert(dataList);
			}
		}

	}

	/***
	 * 根据pointID 和 code 查询
	 * @param pointId
	 * @param colCode
	 * @return
	 */
	public List<TaskTableData> getByPointIdCode(String pointId,String colCode){
		return  taskTableDataDao.getByPointIdCode(pointId, colCode);
	}
}
