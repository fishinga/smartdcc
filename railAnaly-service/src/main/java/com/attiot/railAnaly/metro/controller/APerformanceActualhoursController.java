package com.attiot.railAnaly.metro.controller;

import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSUser;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.entity.APerformanceAccord;
import com.attiot.railAnaly.metro.entity.APerformanceActualhours;
import com.attiot.railAnaly.metro.param.APerformanceAccordQueryParam;
import com.attiot.railAnaly.metro.param.APerformanceActualhoursQueryParam;
import com.attiot.railAnaly.metro.service.APerformanceActualhoursService;
import com.attiot.railAnaly.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Title: aPerformanceActualhoursController
 * @Description: 月度实际工时
 * @author adu
 * @date 2019-02-25 16:39:43
 * @version V1.0
 *
 */
@Slf4j
@Controller
@RequestMapping("metro/aPerformanceActualhoursController")
public class APerformanceActualhoursController{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(APerformanceActualhours.class);

	@Autowired
	private APerformanceActualhoursService aPerformanceActualhoursService;

	/***
	* easyui AJAX请求数据
	* @param param
	* @param request
	* @param response
	*/
	@RequestMapping(params = "getDataList")
	public void getDataList(APerformanceActualhoursQueryParam param, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			Page<APerformanceActualhours> page = aPerformanceActualhoursService.query(param);
			result.setDataList(page.getResults());
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

	/**
	 * 删除月度实际工时
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public void doDel(APerformanceActualhours aPerformanceActualhours, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			aPerformanceActualhoursService.delete(aPerformanceActualhours.getId());
			result.setMsg("月度实际工时删除成功！");
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

	/**
	 * 新增
	 * @param aPerformanceActualhours
     * @param request
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public void save(APerformanceActualhours aPerformanceActualhours, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			APerformanceActualhours oldAPerformanceData = new APerformanceActualhours();
			APerformanceActualhoursQueryParam queryParam = new APerformanceActualhoursQueryParam();
			queryParam.setYearmonth(aPerformanceActualhours.getYearmonth());
			queryParam.setUserId(aPerformanceActualhours.getUserId());
			oldAPerformanceData = aPerformanceActualhoursService.getByParam(queryParam);
			if(null!= oldAPerformanceData && null != oldAPerformanceData.getId()){
				result.setMsg("该月份的工时已添加，请勿重复添加！");
				result.setSuccess(false);
			}else {
				aPerformanceActualhoursService.create(aPerformanceActualhours);
				result.setMsg("添加成功！");
			}
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

	@RequestMapping(params = "doUpdate")
	public void doUpdate(APerformanceActualhours aPerformanceActualhours, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			if(!aPerformanceActualhours.getYearmonth().equals(DateUtil.getCurrentMonth())){
				result.setMsg("只可更新本月月度实际工时！");
				result.setSuccess(false);
			}else {
				aPerformanceActualhoursService.update(aPerformanceActualhours);
				result.setMsg("月度实际工时更新成功！");
			}
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

	/**
	 * 批量删除
	 *
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	public void doBatchDel(String ids,HttpServletRequest request, HttpServletResponse response){
		AppResult result = new AppResult();
		try {
			for(String id:ids.split(",")){
				aPerformanceActualhoursService.delete(id);
			}
			result.setMsg("月度工时批量删除成功！");
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}
}
