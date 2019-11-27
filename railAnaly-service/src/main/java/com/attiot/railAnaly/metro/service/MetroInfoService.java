/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.metro.service;

import com.attiot.railAnaly.metro.dao.MetroInfoDao;
import com.attiot.railAnaly.metro.entity.MetroInfo;
import com.attiot.railAnaly.metro.param.MetroInfoQueryParam;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.point.dao.APointPleaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author attiot
 * 2018-04-03 10:18:42
 */
@Service
public class MetroInfoService {

	@Autowired
	private MetroInfoDao metroInfoDao;
	@Autowired
	private APointPleaseDao pointPleaseDao;

	/**
	 * 查询
	 * @param param
	 */
	public Page<MetroInfo> query(MetroInfoQueryParam param) {
		Page<MetroInfo> page = new Page<>();
		List<MetroInfo> list = metroInfoDao.queryAllTrain(param);
		if(null == list || list.size()<=0) list = new ArrayList();
		page.setTotalNum(list.size());
		page.setResults(list);
		return page;
	}

	List<MetroInfo> queryAllTrain(MetroInfoQueryParam param) {
		return metroInfoDao.queryAllTrain(param);
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(MetroInfoQueryParam param) {
		return metroInfoDao.queryCount(param);
	}


	/**
	 * 根据登录人获取清点id
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> getPointList(String userId){
		return pointPleaseDao.getPointList(userId);
	}


	public List<Map<String,Object>> getMetroInfo(String pointPleaseId,String pointType){
		if("1".equals(pointType)){
			return metroInfoDao.getBoardingMetroInfo(pointPleaseId);
		}else{
			return metroInfoDao.getCartMetroInfo(pointPleaseId);
		}
	}
}
