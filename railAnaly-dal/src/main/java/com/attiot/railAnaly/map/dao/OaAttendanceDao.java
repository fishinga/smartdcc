/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.map.dao;

import com.attiot.railAnaly.map.entity.OaAttendance;
import com.attiot.railAnaly.map.param.OaAttendanceQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 考勤数据
 * @author attiot
 * 2018-09-05 09:04:42
 */
@Repository
public interface OaAttendanceDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(OaAttendance bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(OaAttendance bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	OaAttendance getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	OaAttendance getByParam(OaAttendanceQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<OaAttendance> query(OaAttendanceQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(OaAttendanceQueryParam param);

	/**
	 * 通过user_id去查询是否打卡
	 * @param user_id
	 * @return
	 */
	List<OaAttendance>findByUser_id(String user_id);

}
