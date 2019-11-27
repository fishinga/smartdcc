package com.attiot.railAnaly.metro.controller;


import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSType;
import com.attiot.railAnaly.base.service.TSTypeService;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.entity.APerformanceStatTeam;
import com.attiot.railAnaly.metro.entity.APerformanceStatWorkload;
import com.attiot.railAnaly.metro.param.APerformanceStatTeamQueryParam;
import com.attiot.railAnaly.metro.service.APerformanceStatTeamService;
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
 * @Title: aPerformanceStatTeamController
 * @Description: 员工月度绩效评分汇总
 * @author adu
 * @date 2019-03-07 14:29:18
 * @version V1.0
 *
 */
@RestController
@Slf4j
@RequestMapping("metro/aPerformanceStatTeamController")
public class APerformanceStatTeamController{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(APerformanceStatTeam.class);

	@Autowired
	private APerformanceStatTeamService aPerformanceStatTeamService;
	@Autowired
	private TSTypeService tsTypeService;
	/***
	* easyui AJAX请求数据
	* @param param
	* @param request
	* @param response
	*/
	@RequestMapping(params = "getDataList")
	public void getDataList(APerformanceStatTeamQueryParam param, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			Page<APerformanceStatTeam> page = aPerformanceStatTeamService.queryStatTeam(param);
			List<APerformanceStatTeam> performanceStatTeamList = page.getResults();
			if(null != performanceStatTeamList && performanceStatTeamList.size()>0){
				List<TSType> teamTypeList = tsTypeService.queryMutiListByCode("teamType");
				Map teamTypeMap = new HashMap();
				for(TSType type:teamTypeList) {
					teamTypeMap.put(type.getTypecode(),type.getTypename());
				}
				for(APerformanceStatTeam aPerformanceStatTeam:performanceStatTeamList){
					aPerformanceStatTeam.setDepartTypeName((null != aPerformanceStatTeam.getDepartType())?(String)teamTypeMap.get(aPerformanceStatTeam.getDepartType()):"");
					if(aPerformanceStatTeam.getDepartName() == null || aPerformanceStatTeam.getDepartName().length()<1){
						aPerformanceStatTeam.setDepartName(aPerformanceStatTeam.getDepartId());
					}
				}
			}
			result.setDataList(page.getResults());
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

}
