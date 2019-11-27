package com.attiot.railAnaly.base.controller;

import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.base.entity.TSDepart;
import com.attiot.railAnaly.base.entity.TSLog;
import com.attiot.railAnaly.base.entity.TSUserOrg;
import com.attiot.railAnaly.base.param.TSUserOrgQueryParam;
import com.attiot.railAnaly.base.service.*;
import com.attiot.railAnaly.common.ConstantValue;
import com.attiot.railAnaly.foundation.ParamVerifyUtil;
import com.attiot.railAnaly.foundation.SmsType;
import com.attiot.railAnaly.foundation.exception.AppException;
import com.attiot.railAnaly.foundation.exception.ErrorInfo;
import com.attiot.railAnaly.foundation.service.SmsService;
import com.attiot.railAnaly.jpush.service.JPushService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 登录注册接口
 * @author
 * @date 2017-09-05
 * @version V1.0
 * @Copyright:
 */
@RestController
@Slf4j
public class LoginController {

	@Autowired
	private TSUserService tsUserService;	

	@Autowired
	private TSBaseUserService tsUserBaseService;
	@Autowired
	private SmsService smsService;

	@Autowired
	private TSUserOrgService userOrgService;
	@Autowired
	private TSDepartService departService;

	@Autowired
	private TSLogService logService;


	@Value("${spring.profiles.active}")
	private String activeProfile;

	@RequestMapping(value = "/register")
	public Object register(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		String phone = ServletRequestUtils.getStringParameter(request, "phone", null);
		String code = ServletRequestUtils.getStringParameter(request, "code", null);
		Integer codeIndex = ServletRequestUtils.getIntParameter(request, "codeIndex", -1);
		String cityCode = ServletRequestUtils.getStringParameter(request, "cityCode", null);
		String password = ServletRequestUtils.getStringParameter(request, "password", null);
		String configpassword = ServletRequestUtils.getStringParameter(request, "configpassword", null);
		String registrationId = ServletRequestUtils.getStringParameter(request, "registrationId", null);
		String clientId = ServletRequestUtils.getStringParameter(request, "clientId", null);
		long time = ServletRequestUtils.getLongParameter(request, "time", -1);
		String secret = ServletRequestUtils.getStringParameter(request, "secret", null);
		String RecommendPhone = ServletRequestUtils.getStringParameter(request, "recommendPhone", null);

		// 手机号码的长度等于11 并且纯数字组成
		if (phone.length() != 11 || !StringUtils.isNumeric(phone)) {
			throw new AppException(ErrorInfo.PARAM_ERROR);
		}
		if (!configpassword.equals(password)) {
			throw new AppException(ErrorInfo.REGISTER_PASSWORD_ERROR);
		}
		if (!activeProfile.equalsIgnoreCase("dev") && !smsService.valid(phone, SmsType.REGISTER, code, codeIndex)) {
			throw new AppException(ErrorInfo.VERITY_CODE_ERROR);
		}

		log.info("app user register:" + phone);
		return result;
	}

	/**
	 * 客户登录接口。
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Login",method = RequestMethod.POST)
	public Object customerLogin(HttpServletRequest request) {
		AppResult ar = new AppResult();
		try {
			Map<String, Object> result = new HashMap<>();

			String jobNumber = ServletRequestUtils.getStringParameter(request, "jobNumber", null);
			String pwd = ServletRequestUtils.getStringParameter(request, "pwd", null);

			ParamVerifyUtil.verifyNotBlank(jobNumber);
			ParamVerifyUtil.verifyNotBlank(pwd);
			String userId = null;
			//查询用户信息
			TSBaseUser tsBaseUser = tsUserBaseService.checkUserExits(jobNumber, pwd);
			TSUserOrgQueryParam param = new TSUserOrgQueryParam();
			param.setUserId(tsBaseUser.getId());
			TSUserOrg userOrg = userOrgService.getByParam(param);
			TSDepart tsDepart = departService.getById(userOrg.getOrgId());
			if (tsBaseUser == null) {
				throw new AppException(ErrorInfo.USER_NAME_NOT_EXIST);
			}
			if (tsBaseUser.isLocked()) {
				throw new AppException(ErrorInfo.USER_LOCKED);
			}

			// 登录日志
			TSLog tsLog = new TSLog();
			tsLog.setLogcontent(tsBaseUser.getUsername() + "登录成功！");
			tsLog.setLoglevel(1);
			tsLog.setOperatetype(1);
			tsLog.setOperatetime(new Date());
			tsLog.setUserid(tsBaseUser.getId());
			logService.insert(tsLog);


			result.put("DataModel", tsBaseUser);
			result.put("_sys_group", tsDepart);
			log.info("app user login:" + tsBaseUser.getUsername());
			ar.setDataModel(result);
		}catch(IllegalArgumentException e) {
			ar.setMsg(e.getMessage());
			ar.setRtn(0);
		}

		return ar;
	}

	/**
	 * 重置密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/forgot-pwd", method = RequestMethod.POST)
	public Object forgotPassword(HttpServletRequest request, HttpServletResponse response) {

		Integer userType = ServletRequestUtils.getIntParameter(request, "userType", -1);
		String phone = ServletRequestUtils.getStringParameter(request, "phone", null);
		String code = ServletRequestUtils.getStringParameter(request, "code", null);
		Integer codeIndex = ServletRequestUtils.getIntParameter(request, "codeIndex", -1);
		String password = ServletRequestUtils.getStringParameter(request, "password", null);

		ParamVerifyUtil.verifyNotBlank(userType, codeIndex);
		ParamVerifyUtil.verifyNotBlank(phone, code, password);
		return Maps.newHashMap();
	}
	
}
