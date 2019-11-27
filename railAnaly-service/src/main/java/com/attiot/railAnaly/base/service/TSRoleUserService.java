/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.service;

import com.attiot.railAnaly.base.entity.TSRole;
import com.attiot.railAnaly.base.entity.TSRoleUser;
import com.attiot.railAnaly.base.dao.TSRoleUserDao;
import com.attiot.railAnaly.base.param.TSRoleUserQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;

/**
 * InnoDB free: 599040 kB; (`userid`) REFER `jeecg/t_s_user`(`i
 * @author attiot
 * 2017-09-13 10:20:17
 */
@Service
public class TSRoleUserService {

	@Autowired
	private TSRoleUserDao tSRoleUserDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TSRoleUser bo) {
		tSRoleUserDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TSRoleUser bo) {
		tSRoleUserDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		tSRoleUserDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TSRoleUser getById(String id) {
		return tSRoleUserDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TSRoleUser getByParam(TSRoleUserQueryParam param) {
		return tSRoleUserDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TSRoleUser> query(TSRoleUserQueryParam param) {
		Page<TSRoleUser> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(tSRoleUserDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(tSRoleUserDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TSRoleUserQueryParam param) {
		return tSRoleUserDao.queryCount(param);
	}


}
