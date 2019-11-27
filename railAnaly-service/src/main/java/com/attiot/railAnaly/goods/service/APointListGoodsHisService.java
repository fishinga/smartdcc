/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.goods.service;

import com.attiot.railAnaly.goods.entity.APointListGoodsHis;
import com.attiot.railAnaly.goods.dao.APointListGoodsHisDao;
import com.attiot.railAnaly.goods.param.APointListGoodsHisQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 请点工单作业牌关系
 * @author attiot
 * 2018-05-14 09:52:44
 */
@Service
public class APointListGoodsHisService {

	@Autowired
	private APointListGoodsHisDao aPointListGoodsHisDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(APointListGoodsHis bo) {
		aPointListGoodsHisDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(APointListGoodsHis bo) {
		aPointListGoodsHisDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		aPointListGoodsHisDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public APointListGoodsHis getById(String id) {
		return aPointListGoodsHisDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public APointListGoodsHis getByParam(APointListGoodsHisQueryParam param) {
		return aPointListGoodsHisDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<APointListGoodsHis> query(APointListGoodsHisQueryParam param) {
		Page<APointListGoodsHis> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(aPointListGoodsHisDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(aPointListGoodsHisDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(APointListGoodsHisQueryParam param) {
		return aPointListGoodsHisDao.queryCount(param);
	}
}
