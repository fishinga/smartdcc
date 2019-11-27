package com.attiot.railAnaly.foundation;

import com.attiot.railAnaly.foundation.exception.AppException;
import com.attiot.railAnaly.foundation.exception.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by dengsc on 2017/9/13.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map appErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex){
        String requestPath = getRequestPath(request);
        ErrorInfo errorInfo = ErrorInfo.EMPTY;
        if (ex instanceof AppException) {
            errorInfo = ((AppException) ex).getErrorInfo();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.error("APP请求出错：" + requestPath, ex);
        } else {
            errorInfo = ErrorInfo.SERVER_ERROR;
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("APP请求异常：" + requestPath, ex);
        }

        return errorInfo.getMap();
    }

    /**
     * 获得请求路径
     *
     * @param request
     * @return
     */
    public static String getRequestPath(HttpServletRequest request) {
        String requestPath = request.getRequestURI() + "?" + request.getQueryString();
        if (requestPath.indexOf('&') > -1) {// 去掉其他参数
            requestPath = requestPath.substring(0, requestPath.indexOf('&'));
        }
        requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
        return requestPath;
    }
}
