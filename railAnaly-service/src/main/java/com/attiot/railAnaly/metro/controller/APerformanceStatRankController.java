package com.attiot.railAnaly.metro.controller;

import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSType;
import com.attiot.railAnaly.base.service.TSTypeService;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.entity.APerformanceStatRank;
import com.attiot.railAnaly.metro.param.APerformanceStatRankQueryParam;
import com.attiot.railAnaly.metro.service.APerformanceStatRankService;
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
 * @Title: aPerformanceStatRankController
 * @Description: 年度班组名次汇总
 * @author adu
 * @date 2019-03-11 10:33:44
 * @version V1.0
 *
 */
@RestController
@Slf4j
@RequestMapping("metro/aPerformanceStatRankController")
public class APerformanceStatRankController{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(APerformanceStatRank.class);

	@Autowired
	private APerformanceStatRankService aPerformanceStatRankService;
	@Autowired
	private TSTypeService tsTypeService;

	/***
	* easyui AJAX请求数据
	* @param param
	* @param request
	* @param response
	*/
	@RequestMapping(params = "getDataList")
	public void getDataList(APerformanceStatRankQueryParam param, HttpServletRequest request, HttpServletResponse response) {
		AppResult result = new AppResult();
		try {
			Page<APerformanceStatRank> page =  aPerformanceStatRankService.queryRank(param);
			List<APerformanceStatRank> performanceStatRankList = page.getResults();
			if(performanceStatRankList.size()>0){
				List<TSType> teamTypeList = tsTypeService.queryMutiListByCode("teamType");
				Map teamTypeMap = new HashMap();
				for(TSType type:teamTypeList) {
					teamTypeMap.put(type.getTypecode(),type.getTypename());
				}
				for(APerformanceStatRank aPerformanceStatRank:performanceStatRankList){
					aPerformanceStatRank.setDepartTypeName((null != aPerformanceStatRank.getDepartType())?(String)teamTypeMap.get(aPerformanceStatRank.getDepartType()):"");
					if(aPerformanceStatRank.getDepartName() == null || aPerformanceStatRank.getDepartName().length()<1){
						aPerformanceStatRank.setDepartName(aPerformanceStatRank.getDepartId());
					}
				}
			}
			result.setDataList(performanceStatRankList);
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

}
