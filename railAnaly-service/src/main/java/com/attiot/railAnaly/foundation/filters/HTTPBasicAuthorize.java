package com.attiot.railAnaly.foundation.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

import com.attiot.railAnaly.base.entity.TSUser;
import com.attiot.railAnaly.base.entity.TSUserOrg;
import com.attiot.railAnaly.base.service.TSUserOrgService;
import com.attiot.railAnaly.base.service.TSUserService;
import com.attiot.railAnaly.common.constant.CommonConst;
import com.attiot.railAnaly.foundation.Session;
import com.attiot.railAnaly.foundation.SessionManager;
import com.attiot.railAnaly.foundation.SpringContextUtil;
import com.attiot.railAnaly.foundation.exception.ErrorInfo;

/**
 * Created by admin on 2017/9/8.
 */
@Slf4j
public class HTTPBasicAuthorize implements Filter {

	@Override
	public void destroy() {
		log.info("destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			boolean authorized = checkHTTPBasicAuthorize(request);
			if (!authorized) {
				JsonFactory factory = new JsonFactory();
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				JsonGenerator generator = factory.createJsonGenerator(httpResponse.getOutputStream());
				try {
					httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					generator.writeStartObject();
					generator.writeStringField("message", ErrorInfo.NEEL_LOGIN.getMessage());
					generator.writeStringField("code", ErrorInfo.NEEL_LOGIN.getCode());
					generator.writeEndObject();
				} finally {
					generator.flush();
					generator.close();
				}
				return;
			} else {
				chain.doFilter(request, response);
			}
		} finally {
			// SessionManager.clearThreadLocal();
			log.info("finally");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("init");
	}

	private boolean checkHTTPBasicAuthorize(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String accessToken = httpRequest.getParameter("access_token");
		if (StringUtils.isBlank(accessToken)) {
			String authorization = httpRequest.getHeader("Authorization");
			if (StringUtils.isNotBlank(authorization)) {
				if (authorization.startsWith("OAuth2")) {
					accessToken = authorization.replaceAll("OAuth2", "").trim();
				}
			}
		}

		String wxOpenId = null;
		if (accessToken == null) {
			String openId = httpRequest.getHeader("wxopenid");
			if (StringUtils.isNotBlank(openId)) {
				if (openId.startsWith("OpenId")) {
					wxOpenId = openId.replaceAll("OpenId", "").trim();
				}
			}
			if (wxOpenId == null) {
				return false;
			}
		}
		/*UserOnlineService userOnlineService = (UserOnlineService) SpringContextUtil.getBean(UserOnlineService.class);
		UserOnlineEntity userOnline = null;
		String userId = null;
		Integer userType = null;
		if (accessToken != null) {
			userOnline = userOnlineService.getByAccessToken(accessToken);
			if (userOnline == null) {
				return false;
			}
			userId = userOnline.getUserId();
			userType = userOnline.getUserType();
		} else {
			userOnline = new UserOnlineEntity();
			userOnline.setCreateTime(System.currentTimeMillis());
			userType = CommonConst.USER_TYPE_1;
		}
		if (userOnline != null) {
			Session session = new Session();
			session.setUserId(userId);
			session.setUserType(userType);
			session.setAccessToken(accessToken);

			TSUserService tsUserService = (TSUserService) SpringContextUtil.getBean(TSUserService.class);
			TSUserOrgService userOrgService = (TSUserOrgService) SpringContextUtil.getBean(TSUserOrgService.class);
			TSUserOrg tsUserOrg = userOrgService.getByUserId(userId);
			TSUser tsUser = tsUserService.getById(userId);
			session.setUserName(tsUser.getUsername());
			session.setRealName(tsUser.getRealname());
			if (tsUserOrg != null) {
				session.setDepartId(tsUserOrg.getOrgId());
			}

			// TODO 待完善

			session.setClientId(userOnline.getClientId());
			session.setClientType(userOnline.getClientType());
			SessionManager.boundUserOnline(session);
		}*/
		return true;
	}

}
