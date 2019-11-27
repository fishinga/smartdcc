package com.attiot.railAnaly.foundation;

import com.attiot.railAnaly.common.util.JacksonUtil;
import com.attiot.railAnaly.common.util.StringUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by admin on 2017/11/7.
 */
@ControllerAdvice
@Slf4j
public class GlobalResponseHandler implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 接口返回NULL或空字符串时，统一返回空的JSON串。
        if (o == null) {
            return Maps.newHashMap();
        } else if ((o instanceof String) &&(StringUtil.isEmpty((String)o))) {
            return JacksonUtil.fromObject(Maps.newHashMap());
        }
        return o;
    }
}
