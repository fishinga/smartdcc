/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.map.dao;

import com.attiot.railAnaly.map.entity.OaZnearea;
import com.attiot.railAnaly.map.param.OaZneareaQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 考勤区域
 * @author attiot
 * 2018-09-04 11:14:24
 */
@Repository
public interface OaZneareaDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(OaZnearea bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(OaZnearea bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	OaZnearea getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	OaZnearea getByParam(OaZneareaQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<OaZnearea> query(OaZneareaQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(OaZneareaQueryParam param);
}
