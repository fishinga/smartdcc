package com.attiot.railAnaly.metro.controller;

import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSType;
import com.attiot.railAnaly.base.entity.TSUser;
import com.attiot.railAnaly.base.service.TSTypeService;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.entity.APerformanceStatYearscore;
import com.attiot.railAnaly.metro.param.APerformanceStatYearscoreQueryParam;
import com.attiot.railAnaly.metro.service.APerformanceStatYearscoreService;
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
 * @Title: aPerformanceStatYearscoreController
 * @Description: 年度绩效汇总
 * @author adu
 * @date 2019-03-08 17:14:48
 * @version V1.0
 *
 */
@RestController
@Slf4j
@RequestMapping("metro/aPerformanceStatYearscoreController")
public class APerformanceStatYearscoreController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(APerformanceStatYearscore.class);

	@Autowired
	private APerformanceStatYearscoreService aPerformanceStatYearscoreService;
	@Autowired
	private TSTypeService tsTypeService;
	/***
	* easyui AJAX请求数据
	* @param param
	* @param request
	* @param response
	*/
	@RequestMapping(params = "getDataList")
	public void getDataList(APerformanceStatYearscoreQueryParam param, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			Page<APerformanceStatYearscore> page =  aPerformanceStatYearscoreService.queryStat(param);
			List<APerformanceStatYearscore> performanceStatYearscoreList = page.getResults();
			if( performanceStatYearscoreList.size()>0){
				List<TSType> teamTypeList = tsTypeService.queryMutiListByCode("teamType");
				Map teamTypeMap = new HashMap();
				for(TSType type:teamTypeList) {
					teamTypeMap.put(type.getTypecode(),type.getTypename());
				}
				for(APerformanceStatYearscore aPerformanceStatYearscore:performanceStatYearscoreList){
					aPerformanceStatYearscore.setDepartTypeName((null != aPerformanceStatYearscore.getDepartType())?(String)teamTypeMap.get(aPerformanceStatYearscore.getDepartType()):"");
				}
			}
			result.setDataList(performanceStatYearscoreList);
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

}
