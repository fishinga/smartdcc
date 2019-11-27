package com.attiot.railAnaly.foundation;

import com.attiot.railAnaly.foundation.exception.AppException;
import com.attiot.railAnaly.foundation.exception.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Created by dengshch
 */
@Slf4j
public class ParamVerifyUtil {
    /**
     * 判断数字是否为-1
     *
     * @param param
     * @param param
     * @return
     */
    public static boolean verifyNotBlank(long... param) {
        for (long s : param) {
            if (s == -1) {
            	throw new AppException(ErrorInfo.PARAM_MISS);
            }
        }
        return false;
    }
    /**
     * 判断数字是否为-1
     *
     * @param param
     * @param param
     * @return
     */
    public static boolean verifyNotBlank(Double... param) {
        for (Double s : param) {
            if (s == null || Integer.valueOf(s.toString()) == -1) {
                throw new AppException(ErrorInfo.PARAM_MISS);
            }
        }
        return false;
    }
    /**
     * 判断字符参数是否为空或空串
     *
     * @param param
     * @param param
     * @return
     */
    public static void verifyNotBlank(String... param) {
        for (String s : param) {
            if (StringUtils.isBlank(s)) {
                log.error(Arrays.asList(param).toString());
            	throw new AppException(ErrorInfo.PARAM_MISS);
            }
        }
    }
    /**
     * 是否为电话
     *
     * @param phone
     * @return
     */
    public static boolean isMobilePhone(String phone) {
        if (phone == null) {
            return false;
        }
        return phone.matches("^(0?(1[34587])\\d{9})$|^((0(10|2[0-9]|[3-9]\\d{2}))(([19]\\d{2,4})|([1-9]\\d{6,7})))$|^((400|800)\\d{7})$");
    }
}
