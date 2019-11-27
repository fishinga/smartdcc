/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.base.service;

import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.dao.TSBaseUserDao;
import com.attiot.railAnaly.base.param.TSBaseUserQueryParam;
import com.attiot.railAnaly.common.util.PasswordUtil;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.foundation.ParamVerifyUtil;
import com.attiot.railAnaly.foundation.exception.AppException;
import com.attiot.railAnaly.foundation.exception.ErrorInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * InnoDB free: 600064 kB; (`departid`) REFER `jeecg/t_s_depart
 * @author attiot
 * 2017-12-13 10:25:32
 */
@Service
@Transactional
public class TSBaseUserService {

	@Autowired
	private TSBaseUserDao tSBaseUserDao;

	
	/**
	 * 判断用户是否存在于系统中。
	 * @param username
	 * @param password
	 * @return
	 */
	public TSBaseUser checkUserExits(String username, String password) {
		ParamVerifyUtil.verifyNotBlank(username, password);
		String encryptedPass = PasswordUtil.encrypt(username, password, PasswordUtil.getStaticSalt());
		TSBaseUserQueryParam param = new TSBaseUserQueryParam();
		param.setUsername(username);
		param.setPassword(encryptedPass);
		param.setDeleteFlag(0);
		long count = tSBaseUserDao.queryCount(param);
		if (count > 1) {
			throw new AppException(ErrorInfo.USER_UNUSUAL);
		}else if (count == 0) {
			throw new AppException(ErrorInfo.USER_NAME_NOT_EXIST);
		}
		return tSBaseUserDao.getByParam(param);
	}

	public String tranUserToNamesFromMap(String userIds,Map userMap) {
		StringBuilder usernames = new StringBuilder("");
		String[] userarray = userIds.split(",");
		if(null != userarray && userarray.length>0) {
			for(int i=0;i<userarray.length;i++) {

				if(usernames.length()>0) {
					usernames.append(",");
				}
				usernames.append(null!= userMap.get(userarray[i])? userMap.get(userarray[i]):"");
			}
		}
		return usernames.toString();
	}

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(TSBaseUser bo) {
		tSBaseUserDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(TSBaseUser bo) {
		tSBaseUserDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		tSBaseUserDao.delete(id);
    }

	/**
	 * 获取实体对象
	 * @param id
	 */
	public TSBaseUser getById(String id) {
		return tSBaseUserDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public TSBaseUser getByParam(TSBaseUserQueryParam param) {
		return tSBaseUserDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<TSBaseUser> query(TSBaseUserQueryParam param) {
		Page<TSBaseUser> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(tSBaseUserDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(tSBaseUserDao.query(param));
		}
		return page;
	}

	public List<TSBaseUser> getByIds(String userIds) {
		return tSBaseUserDao.getByIds(userIds);
	}

	public String getRealnamesByIds(String ids) {
		List<TSBaseUser> userList = tSBaseUserDao.getByIds(ids);
		Map<String,String> userMap = new HashMap();
		for(TSBaseUser user:userList) {
			userMap.put(user.getId(),user.getRealname());
		}
		StringBuilder usernames = new StringBuilder("");
		String[] array = ids.split(",");
		for(int i=0;i<array.length;i++) {
			if(usernames.length()>0) {
				usernames.append(",");
			}
			usernames.append(userMap.get(array[i]));
		}
		return usernames.toString();
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(TSBaseUserQueryParam param) {
		return tSBaseUserDao.queryCount(param);
	}
}
