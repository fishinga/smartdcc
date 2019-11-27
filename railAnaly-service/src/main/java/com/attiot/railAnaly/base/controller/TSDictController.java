package com.attiot.railAnaly.base.controller;

import javax.servlet.http.HttpServletRequest;

import com.attiot.railAnaly.base.AppResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.attiot.railAnaly.base.entity.TSType;
import com.attiot.railAnaly.base.entity.TSTypegroup;
import com.attiot.railAnaly.base.param.TSTypeQueryParam;
import com.attiot.railAnaly.base.param.TSTypegroupQueryParam;
import com.attiot.railAnaly.base.service.TSTypeService;
import com.attiot.railAnaly.base.service.TSTypegroupService;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.foundation.exception.AppException;
import com.attiot.railAnaly.foundation.exception.ErrorInfo;

import java.util.List;

/**
 * @Description:获取数据字典
 * @author attiot
 * @date 2017-09-07
 * @version V1.0
 * @Copyright:
 */
@Slf4j
@RestController
public class TSDictController {
	@Autowired
	private TSTypeService tsTypeService;
	@Autowired
	private TSTypegroupService tsTypegroupService;
	
	/**
	 * 列表：字典
	 */
	@RequestMapping(value = "/BaseDictionary/GetListToPhone")
	@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST }, origins = "*")
	public Object typeList(HttpServletRequest request) {
		AppResult ar = new AppResult();
		try {
			TSTypeQueryParam queryParam = new TSTypeQueryParam();
			Page<TSType> resultPage = tsTypeService.query(queryParam);
			ar.setDataList(resultPage.getResults());
		} catch (IllegalArgumentException e) {
			ar.setRtn(0);
			ar.setMsg(e.getMessage());
			log.error("系统内部异常", e);
		}
		return ar;
	}

	/**
	 * 列表：字典组
	 */
	@RequestMapping(value = "/BaseDictionary/typegroupList")
	@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST }, origins = "*")
	public Object typegroupList(HttpServletRequest request) {
		AppResult ar = new AppResult();
		try {
			TSTypegroupQueryParam queryParam = new TSTypegroupQueryParam();
			Page<TSTypegroup> resultPage = tsTypegroupService.query(queryParam);
			ar.setDataList(resultPage.getResults());
		} catch (IllegalArgumentException e) {
			ar.setRtn(0);
			ar.setMsg(e.getMessage());
			log.error("系统内部异常", e);
		}
		return ar;
	}

	/**
	 * 列表：根据字典code查询字典详细列表
	 */
	@RequestMapping(value = "/BaseDictionary/queryTypeListByCode")
	@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST }, origins = "*")
	public Object queryTypeListByCode(HttpServletRequest request) {
		AppResult ar = new AppResult();
		try {
			List<TSType> resultList = tsTypeService.queryListByCode(request.getParameter("code"));
			ar.setDataList(resultList);
		} catch (IllegalArgumentException e) {
			ar.setRtn(0);
			ar.setMsg(e.getMessage());
			log.error("系统内部异常", e);
		}
		return ar;
	}
}
