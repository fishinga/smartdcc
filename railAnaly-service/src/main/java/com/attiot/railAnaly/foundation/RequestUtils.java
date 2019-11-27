package com.attiot.railAnaly.foundation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 功能/模块：<br/>
 * 类描述：<br/>
 * 修订历史：：<br/>
 * 日期  作者  参考  描述：<br/>
 *
 * @author dengsc
 * @version 1.0 17-10-18
 * @see
 */
public class RequestUtils extends ServletRequestUtils {

    /**
     *  从request对象里获取IP。
      * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
