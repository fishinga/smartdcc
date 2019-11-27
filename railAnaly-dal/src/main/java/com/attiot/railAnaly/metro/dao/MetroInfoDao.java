/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.metro.dao;

import com.attiot.railAnaly.metro.entity.MetroInfo;
import com.attiot.railAnaly.metro.param.MetroInfoQueryParam;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author attiot
 * 2018-04-03 10:18:42
 */
@Repository
public interface MetroInfoDao {

	/**
	 * 查询
	 * @param param
	 */
	List<MetroInfo> query(MetroInfoQueryParam param);

	List<MetroInfo> queryAllTrain(MetroInfoQueryParam param);


	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(MetroInfoQueryParam param);

	public List<Map<String,Object>> getBoardingMetroInfo(@Param("pointPleaseId") String pointPleaseId);

	public List<Map<String,Object>> getCartMetroInfo(@Param("pointPleaseId") String pointPleaseId);
}
