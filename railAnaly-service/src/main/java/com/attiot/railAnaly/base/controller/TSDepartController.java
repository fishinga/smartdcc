package com.attiot.railAnaly.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSType;
import com.attiot.railAnaly.base.service.TSTypeService;
import com.attiot.railAnaly.base.service.TSUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attiot.railAnaly.base.entity.TSDepart;
import com.attiot.railAnaly.base.param.TSDepartQueryParam;
import com.attiot.railAnaly.base.service.TSDepartService;
import com.attiot.railAnaly.foundation.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
　　 *@author suks
 　　* @version 2017年12月13日
 　　*/
@Slf4j
@RestController
public class TSDepartController {
	@Autowired
	private TSDepartService tSDepartService;
	@Autowired
	private TSUserService tsUserService;
	@Autowired
	private TSTypeService tsTypeService;
	/**
	 * 列表：获取部门列表
	 */
	@RequestMapping(value="/Phone/PhoneSysGroup/GetList")
	public void departList(HttpServletRequest request, HttpServletResponse response) {
		AppResult ar = new AppResult();

		try {
			TSDepartQueryParam queryParam = new TSDepartQueryParam();
			Page<TSDepart> resultPage = tSDepartService.query(queryParam);
			ar.setDataList(resultPage.getResults());
			ar.writer(request, response);
		} catch (IllegalArgumentException e) {
			ar.setRtn(0);
			ar.setMsg(e.getMessage());
			log.error("系统内部异常", e);
		}
	}


	/**
	 * 列表：获取工作班组列表+班组员工列表
	 */
	@RequestMapping(value="/GetGangListAndStaffList")
	public void GetGangListAndStaffList(HttpServletRequest request, HttpServletResponse response) {
		AppResult ar = new AppResult();
		try {
			TSDepartQueryParam queryParam = new TSDepartQueryParam();
			queryParam.setOrgType("6");
			Page<TSDepart> tsDepartPage = tSDepartService.query(queryParam);
			List<TSDepart> tsDepartList = tsDepartPage.getResults();
			List<Map<String,String>> userList = tsUserService.getUserDepartList();
			List<TSType> postList = tsTypeService.queryListByCode("postType");
			Map postMap = new HashMap();
			for(TSType type:postList) {
				postMap.put(type.getTypecode(),type.getTypename());
			}
			List resultList = Lists.newArrayList();
			for(TSDepart tsDepart:tsDepartList){
				Map resultMap = Maps.newHashMap();
				resultMap.put("departId",tsDepart.getId());
				resultMap.put("departName",tsDepart.getDepartname());
				resultMap.put("staffList",getListByDepart(tsDepart,userList,postMap));
				resultList.add(resultMap);
			}
			ar.setDataList(resultList);
			ar.writer(request, response);
		} catch (IllegalArgumentException e) {
			ar.setRtn(0);
			ar.setMsg(e.getMessage());
			log.error("系统内部异常", e);
		}
	}

	private Object getListByDepart(TSDepart tsDepart, List<Map<String, String>> userList,Map postMap) {
		JSONArray jsonArray = new JSONArray();
		for(Map<String, String> rusultMap:userList){
			if(rusultMap.get("departId").equals(tsDepart.getId())){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("userId",rusultMap.get("userId"));
				jsonObject.put("userCode",rusultMap.get("userCode"));
				jsonObject.put("userName",rusultMap.get("userName"));
				jsonObject.put("positionCode",rusultMap.get("positionCode"));
				jsonObject.put("positionName",rusultMap.get("positionCode"));
				jsonObject.put("positionCode", (null != rusultMap.get("positionCode"))?rusultMap.get("positionCode"):"");
				jsonObject.put("positionName", (null != rusultMap.get("positionCode"))?postMap.get(rusultMap.get("positionCode")):"");
				jsonObject.put("departId",rusultMap.get("departId"));
				jsonObject.put("departName",tsDepart.getDepartname());
				jsonArray.add(jsonObject);
			}
		}
		return jsonArray;
	}
}
