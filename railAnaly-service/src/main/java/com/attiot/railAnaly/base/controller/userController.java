package com.attiot.railAnaly.base.controller;

import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.service.TSBaseUserService;
import com.attiot.railAnaly.base.service.TSUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description: 获取用户接口
 * @author
 * @date 2017-09-05
 * @version V1.0
 * @Copyright:
 */
@RestController
@Slf4j
public class userController {

	@Autowired
	private TSUserService tsUserService;	

	@Autowired
	private TSBaseUserService tsUserBaseService;

	@RequestMapping(value = "Phone/PhonePersonal/GetList")
	public AppResult getList(){
		AppResult ar = new AppResult();
		List<Map<String ,Object>> list = null;
		try {
			list = tsUserService.getList();
			ar.setDataList(list);
		} catch (IllegalArgumentException e) {
			ar.setRtn(0);
			ar.setMsg(e.getMessage());
			log.error("系统内部异常", e);
		}
		return ar;
	}
}
