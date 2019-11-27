package com.attiot.railAnaly.metro.controller;

import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSType;
import com.attiot.railAnaly.base.service.TSTypeService;
import com.attiot.railAnaly.common.constant.CommonConst;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.entity.APerformanceData;
import com.attiot.railAnaly.metro.param.APerformanceDataQueryParam;
import com.attiot.railAnaly.metro.service.APerformanceDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: Controller  
 * @Description: a_performance_data
 * @author onlineGenerator
 * @date 2019-02-14 14:14:18
 * @version V1.0   
 *
 */
@RestController
@Slf4j
@RequestMapping(value = "metro/aPerformanceDataController")
public class APerformanceDataController{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(APerformanceDataController.class);

	@Autowired
	private APerformanceDataService aPerformanceDataService;
	@Autowired
	private TSTypeService tsTypeService;
	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param
	 */

	@RequestMapping(params = "getDataList")
	public void getDataList(APerformanceDataQueryParam param,HttpServletRequest request , HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			Page<APerformanceData> page = aPerformanceDataService.query(param);
			List<APerformanceData> performanceDataList = page.getResults();
			List<APerformanceData> returnList = new ArrayList<>();
			if (null != performanceDataList && performanceDataList.size() > 0) {
				List<TSType> resultList = tsTypeService.queryListByCode("postType");
				List<TSType> teamTypeList = tsTypeService.queryMutiListByCode("teamType");
				List<TSType> evalTypeList = tsTypeService.queryListByCode("evalType");
				Map postMap = new HashMap();
				Map teamTypeMap = new HashMap();
				Map evalTypeMap = new HashMap();
				for(TSType type:resultList) {
					postMap.put(type.getTypecode(),type.getTypename());
				}
				for(TSType type:teamTypeList) {
					teamTypeMap.put(type.getTypecode(),type.getTypename());
				}
				for(TSType type:evalTypeList) {
					evalTypeMap.put(type.getTypecode(),type.getTypename());
				}
				for(APerformanceData aPerformanceData:performanceDataList){
					aPerformanceData.setPositionName((null != aPerformanceData.getPositionCode())?(String)postMap.get(aPerformanceData.getPositionCode()):"");
					aPerformanceData.setDepartTypeName((null != aPerformanceData.getDepartType())?(String)teamTypeMap.get(aPerformanceData.getDepartType()):"");
					if(aPerformanceData.getPerfType().equals(CommonConst.perfType_10)){
						aPerformanceData.setEvalTypeName((String)evalTypeMap.get(aPerformanceData.getTeamScore()));
					}
					returnList.add(aPerformanceData);
				}
			}
			result.setDataList(returnList);
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

	/**
	 * 删除a_performance_data
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	public void doDel(APerformanceData aPerformanceData, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			aPerformanceDataService.delete(aPerformanceData.getId());
			result.setMsg("删除成功！");
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

	/**
	 * 批量删除a_performance_data
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	public void doBatchDel(String ids,HttpServletRequest request, HttpServletResponse response){
		 AppResult result = new AppResult();
		 try {
			 for(String id:ids.split(",")){
				 aPerformanceDataService.delete(id);
			 }
			 result.setMsg("批量删除成功！");
			 result.writer(request,response);
		 } catch (IllegalArgumentException e) {
			 result.setMsg(e.getMessage());
			 result.setSuccess(false);
			 log.error("系统内部异常", e);
		 }
	}


	/**
	 * 添加a_performance_data
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	public void doAdd(APerformanceData aPerformanceData, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			aPerformanceDataService.create(aPerformanceData);
			result.setMsg("添加成功！");
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

	/**
	 * 更新a_performance_data
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	public void doUpdate(APerformanceData aPerformanceData, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			aPerformanceDataService.update(aPerformanceData);
			result.setMsg("更新成功！");
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}
}
