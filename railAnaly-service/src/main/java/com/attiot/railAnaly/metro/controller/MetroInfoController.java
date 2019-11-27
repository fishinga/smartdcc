package com.attiot.railAnaly.metro.controller;

import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.entity.MetroInfo;
import com.attiot.railAnaly.metro.param.MetroInfoQueryParam;
import com.attiot.railAnaly.metro.service.MetroInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 获取车辆信息接口
 * @author
 * @date 2017-09-05
 * @version V1.0
 * @Copyright:
 */
@RestController
@Slf4j
@RequestMapping(value = "metro/metroInfoController")
public class MetroInfoController {

	@Autowired
	private MetroInfoService metroInfoService;

	/***
	 * 获取车辆基本信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getMetro")
	public void getMetro(HttpServletRequest request , HttpServletResponse response){
		AppResult result = new AppResult();
		String type = request.getParameter("type");
		if("0".equals(type)){
			try {
				MetroInfoQueryParam param = new MetroInfoQueryParam();
				Page<MetroInfo> page =  metroInfoService.query(param);
				result.setDataList(page.getResults());
				result.writer(request,response);
			} catch (IllegalArgumentException e) {
				result.setMsg(e.getMessage());
				result.setSuccess(false);
				log.error("系统内部异常", e);
			}
		}else if("1".equals(type)){
			String userId = request.getParameter("accessToken");
			try {
				List<Map<String,Object>> list = metroInfoService.getPointList(userId);
				List<Map<String,Object>> list1 = new ArrayList<>();
				for(int i=0;i<list.size();i++){
					String pointType = (String) list.get(i).get("point_type");
					String pointPleaseId =  (String) list.get(i).get("id");
					List<Map<String,Object>> list2 = metroInfoService.getMetroInfo(pointPleaseId,pointType);
					if(list2.size() >0 ){
						list1.add(list2.get(0));
					}
				}
				result.setDataList(list1);
			} catch (IllegalArgumentException e) {
				result.setMsg(e.getMessage());
				result.setSuccess(false);
				log.error("系统内部异常", e);
			}
			result.writer(request,response);
		}
	}





}
