package com.attiot.railAnaly.metro.controller;

import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.entity.APerformanceStatTeammodel;
import com.attiot.railAnaly.metro.param.APerformanceStatTeammodelQueryParam;
import com.attiot.railAnaly.metro.service.APerformanceStatTeammodelService;
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
import java.util.List;
import java.util.Map;

/**
 * @Title: aPerformanceStatTeammodelController
 * @Description: 年度班组各模块分数汇总
 * @author adu
 * @date 2019-03-11 13:50:59
 * @version V1.0
 *
 */
@RestController
@Slf4j
@RequestMapping("metro/aPerformanceStatTeammodelController")
public class APerformanceStatTeammodelController{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(APerformanceStatTeammodel.class);

	@Autowired
	private APerformanceStatTeammodelService aPerformanceStatTeammodelService;

	/***
	* easyui AJAX请求数据
	* @param param
	* @param request
	* @param response
	*/
	@RequestMapping(params = "getDataList")
	public void getDataList(APerformanceStatTeammodelQueryParam param, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			Page<APerformanceStatTeammodel> page =  aPerformanceStatTeammodelService.queryStatTeam(param);
			for(APerformanceStatTeammodel apt:page.getResults()){
				if(apt.getDepartName() == null || apt.getDepartName().length()<1){
					apt.setDepartName(apt.getDepartId());
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
