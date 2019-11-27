/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.metro.service;

import com.attiot.railAnaly.metro.entity.MetroJob;
import com.attiot.railAnaly.metro.dao.MetroJobDao;
import com.attiot.railAnaly.metro.param.MetroJobQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 车辆作业状态
 * @author attiot
 * 2018-04-04 15:12:08
 */
@Service
public class MetroJobService {

	@Autowired
	private MetroJobDao metroJobDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(MetroJob bo) {
		metroJobDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(MetroJob bo) {
		metroJobDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		metroJobDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public MetroJob getById(String id) {
		return metroJobDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public MetroJob getByParam(MetroJobQueryParam param) {
		return metroJobDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<MetroJob> query(MetroJobQueryParam param) {
		Page<MetroJob> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(metroJobDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(metroJobDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(MetroJobQueryParam param) {
		return metroJobDao.queryCount(param);
	}
}
