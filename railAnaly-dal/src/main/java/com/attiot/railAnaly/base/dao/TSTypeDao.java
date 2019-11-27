/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.dao;

import com.attiot.railAnaly.base.entity.TSType;
import com.attiot.railAnaly.base.param.TSTypeQueryParam;
import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Repository;

/**
 * InnoDB free: 599040 kB; (`typegroupid`) REFER `jeecg/t_s_typ
 * @author attiot
 * 2017-09-25 10:02:03
 */
@Repository
public interface TSTypeDao {

	/**
	 * 新增
	 * @param bo
	 */
	void insert(TSType bo);

	/**
	 * 修改
	 * @param bo
	 */
	void update(TSType bo);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 获取实体对象
	 * @param id
	 */
	TSType getById(String id);

	/**
	 * 查询实体对象
	 * @param param
	 */
	TSType getByParam(TSTypeQueryParam param);

	/**
	 * 查询
	 * @param param
	 */
	List<TSType> query(TSTypeQueryParam param);

	/**
	 * 查询统计
	 * @param param
	 */
	long queryCount(TSTypeQueryParam param);

	/**
	 * 根据字典分组类型查询
	 * @param groupCode
	 * @return
	 */
	List<TSType> GetListByGroupCode(String groupCode);


	/**
	 * 查询typecode typename
	 */
	List<TSType> queryList(TSTypeQueryParam param);

	public List<TSType> queryListByCode(String code);



    List<TSType> queryMutiListByCode(String code);
}
