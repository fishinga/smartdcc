/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attiot.railAnaly.base.dao.TSBaseUserDao;
import com.attiot.railAnaly.base.dao.TSUserDao;
import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.entity.TSUser;
import com.attiot.railAnaly.base.param.TSBaseUserQueryParam;
import com.attiot.railAnaly.base.param.TSUserQueryParam;
import com.attiot.railAnaly.common.util.PasswordUtil;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.foundation.ParamVerifyUtil;
import com.attiot.railAnaly.foundation.exception.AppException;
import com.attiot.railAnaly.foundation.exception.ErrorInfo;

/**
 * InnoDB free: 599040 kB; (`id`) REFER `jeecg/t_s_base_user`(`
 * @author attiot
 * 2017-12-13 10:13:29
 */
@Service
@Transactional
public class TSUserService {

	@Autowired
	private TSBaseUserDao tSBaseUserDao;
	@Autowired
	private TSUserDao tSUserDao;
	
	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TSUser bo) {
		tSUserDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TSUser bo) {
		tSUserDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		tSUserDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TSUser getById(String id) {
		return tSUserDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TSUser getByParam(TSUserQueryParam param) {
		return tSUserDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TSUser> query(TSUserQueryParam param) {
		Page<TSUser> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(tSUserDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(tSUserDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TSUserQueryParam param) {
		return tSUserDao.queryCount(param);
	}


	public List<Map<String ,Object>> getList(){
		return tSUserDao.getList();
	}

	public  List<Map<String ,Object>> getTSBaseUserListByUserIds(String userIds){
		return tSUserDao.getTSBaseUserListByUserIds(userIds);
	}

	public List<TSBaseUser> getTSBaseUserListByRoleCode(String roleCode) {
		return tSUserDao.getTSBaseUserListByRoleCode(roleCode);
	}


	public List<TSBaseUser> getTSBaseUserListByDepartId(String departId) {
		return tSUserDao.getTSBaseUserListByDepartId(departId);
	}

	public List<Map<String,Object>> getUserListByIds(String ids){
		return tSUserDao.getUserListByIds(ids);
	}

	public List<Map<String,String>> getUserDepartList() {
		return tSUserDao.getUserDepartList();
	}
}
