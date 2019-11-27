/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.service;

import com.attiot.railAnaly.task.entity.ATaskListHis;
import com.attiot.railAnaly.task.dao.ATaskListHisDao;
import com.attiot.railAnaly.task.param.ATaskListHisQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 历史任务工单
 * @author attiot
 * 2018-05-21 11:05:08
 */
@Service
public class ATaskListHisService {

	@Autowired
	private ATaskListHisDao aTaskListHisDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(ATaskListHis bo) {
		aTaskListHisDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(ATaskListHis bo) {
		aTaskListHisDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		aTaskListHisDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public ATaskListHis getById(String id) {
		return aTaskListHisDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public ATaskListHis getByParam(ATaskListHisQueryParam param) {
		return aTaskListHisDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<ATaskListHis> query(ATaskListHisQueryParam param) {
		Page<ATaskListHis> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(aTaskListHisDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(aTaskListHisDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(ATaskListHisQueryParam param) {
		return aTaskListHisDao.queryCount(param);
	}
}
