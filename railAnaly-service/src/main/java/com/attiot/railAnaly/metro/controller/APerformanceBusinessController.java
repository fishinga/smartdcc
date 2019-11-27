package com.attiot.railAnaly.metro.controller;

import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.entity.APerformanceAccord;
import com.attiot.railAnaly.metro.entity.APerformanceBusiness;
import com.attiot.railAnaly.metro.param.APerformanceBusinessQueryParam;
import com.attiot.railAnaly.metro.service.APerformanceBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**   
 * @Title: Controller  
 * @Description: a_performance_business
 * @author onlineGenerator
 * @date 2019-02-11 14:41:57
 * @version V1.0   
 *
 */
@Slf4j
@Controller
@RequestMapping("metro/aPerformanceBusinessController")
public class APerformanceBusinessController{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(APerformanceBusinessController.class);

	@Autowired
	private APerformanceBusinessService aPerformanceBusinessService;

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param
	 */

	@RequestMapping(params = "getDataList")
	public void getDataList(APerformanceBusinessQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			Page<APerformanceBusiness> page = aPerformanceBusinessService.query(queryParam);
			result.setDataList(page.getResults());
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

}
