/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.borrow.dao;

import com.attiot.railAnaly.borrow.entity.BorrowListForeign;
import com.attiot.railAnaly.borrow.param.BorrowListForeignQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 借用表
 * @author attiot
 * 2018-08-23 09:59:21
 */
@Repository
public interface BorrowListForeignDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(BorrowListForeign bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(BorrowListForeign bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	BorrowListForeign getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	BorrowListForeign getByParam(BorrowListForeignQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<BorrowListForeign> query(BorrowListForeignQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(BorrowListForeignQueryParam param);
}
