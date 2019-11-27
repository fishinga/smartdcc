/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.goods.service;

import com.attiot.railAnaly.goods.entity.APointListHis;
import com.attiot.railAnaly.goods.dao.APointListHisDao;
import com.attiot.railAnaly.goods.param.APointListHisQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 请点工单
 * @author attiot
 * 2018-05-14 19:52:29
 */
@Service
public class APointListHisService {

	@Autowired
	private APointListHisDao aPointListHisDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(APointListHis bo) {
		aPointListHisDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(APointListHis bo) {
		aPointListHisDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		aPointListHisDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public APointListHis getById(String id) {
		return aPointListHisDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public APointListHis getByParam(APointListHisQueryParam param) {
		return aPointListHisDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<APointListHis> query(APointListHisQueryParam param) {
		Page<APointListHis> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(aPointListHisDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(aPointListHisDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(APointListHisQueryParam param) {
		return aPointListHisDao.queryCount(param);
	}
}
