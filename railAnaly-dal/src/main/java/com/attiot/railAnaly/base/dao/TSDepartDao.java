/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.dao;

import com.attiot.railAnaly.base.entity.TSDepart;
import com.attiot.railAnaly.base.param.TSDepartQueryParam;
import java.util.List;
import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author attiot
 * 2017-12-13 14:02:51
 */
@Repository
public interface TSDepartDao {

	/**
	 * 获取实体对象
	 * @param id
	 */
	TSDepart getById(String id);

	public List<TSDepart> getByIds(String ids);



	/**
	 * 查询
	 * @param param
	 */
	List<TSDepart> query(TSDepartQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TSDepartQueryParam param);

	public List<String> byManager(@Param("manager")String manager );


	public List<Map<String ,Object>> getWorkUserList(@Param("departId")String departId);

	public  List<TSDepart> getDepartListByUserId(String userId);
}
