/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.borrow.service;

import com.attiot.railAnaly.borrow.entity.BorrowListForeign;
import com.attiot.railAnaly.borrow.dao.BorrowListForeignDao;
import com.attiot.railAnaly.borrow.param.BorrowListForeignQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 借用表
 * @author attiot
 * 2018-08-23 09:59:21
 */
@Service
public class BorrowListForeignService {

	@Autowired
	private BorrowListForeignDao borrowListForeignDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(BorrowListForeign bo) {
		borrowListForeignDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(BorrowListForeign bo) {
		borrowListForeignDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		borrowListForeignDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public BorrowListForeign getById(String id) {
		return borrowListForeignDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public BorrowListForeign getByParam(BorrowListForeignQueryParam param) {
		return borrowListForeignDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<BorrowListForeign> query(BorrowListForeignQueryParam param) {
		Page<BorrowListForeign> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(borrowListForeignDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(borrowListForeignDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(BorrowListForeignQueryParam param) {
		return borrowListForeignDao.queryCount(param);
	}
}
