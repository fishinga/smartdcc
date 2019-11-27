package com.attiot.railAnaly.metro.controller;

import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.entity.APerformanceAccord;
import com.attiot.railAnaly.metro.entity.APerformanceData;
import com.attiot.railAnaly.metro.param.APerformanceAccordQueryParam;
import com.attiot.railAnaly.metro.service.APerformanceAccordService;
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
 * @Description: a_performance_accord
 * @author onlineGenerator
 * @date 2019-02-12 16:30:23
 * @version V1.0   
 *
 */
@Slf4j
@Controller
@RequestMapping("metro/aPerformanceAccordController")
public class APerformanceAccordController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(APerformanceAccordController.class);

	@Autowired
	private APerformanceAccordService aPerformanceAccordService;

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping(params = "getDataList")
	public void getDataList(APerformanceAccordQueryParam param, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			param.setDeleted(0);
			Page<APerformanceAccord> page = aPerformanceAccordService.query(param);
			result.setDataList(page.getResults());
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}
	
	/**
	 * 删除a_performance_accord
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public void doDel(APerformanceAccord aPerformanceAccord, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			aPerformanceAccordService.delete(aPerformanceAccord.getId());
			result.setMsg("绩效业务考评细则删除成功！");
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

	/**
	 * 新增
	 * @param aPerformanceAccord
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public void save(APerformanceAccord aPerformanceAccord, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			aPerformanceAccord.setDeleted(0);
			aPerformanceAccordService.create(aPerformanceAccord);
			result.setMsg("添加成功！");
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

	@RequestMapping(params = "doUpdate")
	public void doUpdate(APerformanceAccord aPerformanceAccord, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			aPerformanceAccordService.update(aPerformanceAccord);
			result.setMsg("月度实际工时更新成功！");
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}
	/**
	 * 批量删除a_performance_accord
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public void doBatchDel(String ids,HttpServletRequest request, HttpServletResponse response){
		 AppResult result = new AppResult();
		 try {
			 for(String id:ids.split(",")){
				 aPerformanceAccordService.delete(id);
			 }
			 result.setMsg("绩效业务考评细则批量删除成功！");
			 result.writer(request,response);
		 } catch (IllegalArgumentException e) {
			 result.setMsg(e.getMessage());
			 result.setSuccess(false);
			 log.error("系统内部异常", e);
		 }
	}



}
