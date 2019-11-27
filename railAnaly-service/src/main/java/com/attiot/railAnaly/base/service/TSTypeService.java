/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attiot.railAnaly.base.dao.TSTypeDao;
import com.attiot.railAnaly.base.entity.TSType;
import com.attiot.railAnaly.base.param.TSTypeQueryParam;
import com.attiot.railAnaly.foundation.Page;

/**
 * InnoDB free: 599040 kB; (`typegroupid`) REFER `jeecg/t_s_typ
 * @author attiot
 * 2017-09-25 10:02:03
 */
@Service
@Transactional
public class TSTypeService {

	@Autowired
	private TSTypeDao tSTypeDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TSType bo) {
		tSTypeDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TSType bo) {
		tSTypeDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		tSTypeDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TSType getById(String id) {
		return tSTypeDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TSType getByParam(TSTypeQueryParam param) {
		return tSTypeDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TSType> query(TSTypeQueryParam param) {
		Page<TSType> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(tSTypeDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.emptyList());
		} else {
			page.setResults(tSTypeDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TSTypeQueryParam param) {
		return tSTypeDao.queryCount(param);
	}

	/**
	 * 通过字典Code 获取集合
	 * @param
	 */
	public List<TSType> GetListByGroupCode(String groupCode) {
		return tSTypeDao.GetListByGroupCode(groupCode);
	}

	public String getTypeNameByTypeeGroupCodeAndCode(String groupCode,String code) {
		List<TSType> typeList = tSTypeDao.GetListByGroupCode(groupCode);
		if(null != typeList && typeList.size()>0) {
			for(TSType tsType:typeList) {
				if(code.equals(tsType.getTypecode())) {
					return tsType.getTypename();
				}
			}
		}
		return "";
	}
	/**
	 * 查询typecode typename
	 */
	public List<TSType> queryList(TSTypeQueryParam param){
		return tSTypeDao.queryList(param);
	}

	public List<TSType> queryListByCode(String code) {
		return tSTypeDao.queryListByCode(code);
	}

	public List<TSType> getByTypeeGroupCode(String typeGroupCode) {
		return tSTypeDao.GetListByGroupCode(typeGroupCode);
	}
	/**
	 * 关联国际化语言表查询typecode typename
	 */
	public List<TSType> queryMutiListByCode(String code) {
		return tSTypeDao.queryMutiListByCode(code);
	}
}
