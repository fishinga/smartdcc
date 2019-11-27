package com.attiot.railAnaly.metro.controller;

import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSType;
import com.attiot.railAnaly.base.service.TSTypeService;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.entity.APerformanceStatPersonal;
import com.attiot.railAnaly.metro.param.APerformanceStatPersonalQueryParam;
import com.attiot.railAnaly.metro.service.APerformanceStatPersonalService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: aPerformanceStatPersonalController
 * @Description: 员工月度绩效评分汇总
 * @author adu
 * @date 2019-03-01 10:50:08
 * @version V1.0
 *
 */
@RestController
@Slf4j
@RequestMapping("metro/aPerformanceStatPersonalController")
public class APerformanceStatPersonalController{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(APerformanceStatPersonal.class);

	@Autowired
	private APerformanceStatPersonalService aPerformanceStatPersonalService;
	@Autowired
	private TSTypeService tsTypeService;

	/***
	* easyui AJAX请求数据
	* @param param
	* @param request
	* @param response
	*/
	@RequestMapping(params = "getDataList")
	public void getDataList(APerformanceStatPersonalQueryParam param, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			Page<APerformanceStatPersonal> page = aPerformanceStatPersonalService.query(param);
			List<APerformanceStatPersonal> performanceStatPersonalList = page.getResults();
			if(performanceStatPersonalList.size()>0){
				List<TSType> resultList = tsTypeService.queryListByCode("postType");
				Map postMap = new HashMap();
				for(TSType type:resultList) {
					postMap.put(type.getTypecode(),type.getTypename());
				}
				for(APerformanceStatPersonal aPerformanceStatPersonal:performanceStatPersonalList){
					aPerformanceStatPersonal.setPositionName((null != aPerformanceStatPersonal.getPositionCode())?(String)postMap.get(aPerformanceStatPersonal.getPositionCode()):"");
				}
			}
			result.setDataList(performanceStatPersonalList);
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

}
