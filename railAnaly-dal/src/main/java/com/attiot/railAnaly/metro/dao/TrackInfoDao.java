/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.TrackInfo;
import com.attiot.railAnaly.metro.param.TrackInfoQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * 股道信息
 * @author attiot
 * 2018-09-04 10:35:11
 */
@Repository
public interface TrackInfoDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TrackInfo bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TrackInfo bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TrackInfo getById(String id);

	public TrackInfo getByTrackName(String trackName);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TrackInfo getByParam(TrackInfoQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TrackInfo> query(TrackInfoQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TrackInfoQueryParam param);
}
