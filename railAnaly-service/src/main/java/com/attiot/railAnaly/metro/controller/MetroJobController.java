package com.attiot.railAnaly.metro.controller;

import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.metro.entity.MetroJob;
import com.attiot.railAnaly.metro.param.MetroJobQueryParam;
import com.attiot.railAnaly.metro.service.MetroJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 获取车辆作业接口
 * @author
 * @date 2017-09-05
 * @version V1.0
 * @Copyright:
 */
@RestController
@Slf4j
@RequestMapping(value = "metro/metroJob")
public class MetroJobController {
	@Autowired
	private MetroJobService metroJobService;


	@RequestMapping(value = "/getJob")
	public void getJob(HttpServletRequest request , HttpServletResponse response){
		AppResult result = new AppResult();
		try {
			MetroJobQueryParam param = new MetroJobQueryParam();
			Page<MetroJob> page = metroJobService.query(param);
			result.setDataList(page.getResults());
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}


}
