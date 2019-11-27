/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.MetroJob;
import com.attiot.railAnaly.metro.param.MetroJobQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 车辆作业状态
 * @author attiot
 * 2018-04-04 15:12:08
 */
@Repository
public interface MetroJobDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(MetroJob bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(MetroJob bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	MetroJob getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	MetroJob getByParam(MetroJobQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<MetroJob> query(MetroJobQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(MetroJobQueryParam param);
}
