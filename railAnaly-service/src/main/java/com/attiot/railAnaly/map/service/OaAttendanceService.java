/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.map.service;

import com.attiot.railAnaly.map.entity.OaAttendance;
import com.attiot.railAnaly.map.dao.OaAttendanceDao;
import com.attiot.railAnaly.map.param.OaAttendanceQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * 考勤数据
 * @author attiot
 * 2018-09-05 09:04:42
 */
@Service
public class OaAttendanceService {

	@Autowired
	private OaAttendanceDao oaAttendanceDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(OaAttendance bo) {
		oaAttendanceDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(OaAttendance bo) {
		oaAttendanceDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		oaAttendanceDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public OaAttendance getById(String id) {
		return oaAttendanceDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public OaAttendance getByParam(OaAttendanceQueryParam param) {
		return oaAttendanceDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<OaAttendance> query(OaAttendanceQueryParam param) {
		Page<OaAttendance> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(oaAttendanceDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(oaAttendanceDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(OaAttendanceQueryParam param) {
		return oaAttendanceDao.queryCount(param);
	}

	/**
	 * 是否打卡
	 * @param user_id
	 * @return
	 */
	public List<OaAttendance> findByUser_id(String user_id) {
		return oaAttendanceDao.findByUser_id(user_id);
	}

}
