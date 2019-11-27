/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.metro.service;

import com.attiot.railAnaly.metro.entity.TrackInfo;
import com.attiot.railAnaly.metro.dao.TrackInfoDao;
import com.attiot.railAnaly.metro.param.TrackInfoQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

/**
 * 股道信息
 * @author attiot
 * 2018-09-04 10:35:11
 */
@Service
public class TrackInfoService {

	@Autowired
	private TrackInfoDao trackInfoDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TrackInfo bo) {
		trackInfoDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TrackInfo bo) {
		trackInfoDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		trackInfoDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TrackInfo getById(String id) {
		return trackInfoDao.getById(id);
	}

	public TrackInfo getByTrackName(String trackName) {
		return trackInfoDao.getByTrackName(trackName);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TrackInfo getByParam(TrackInfoQueryParam param) {
		return trackInfoDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TrackInfo> query(TrackInfoQueryParam param) {
		Page<TrackInfo> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(trackInfoDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(trackInfoDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TrackInfoQueryParam param) {
		return trackInfoDao.queryCount(param);
	}
}
