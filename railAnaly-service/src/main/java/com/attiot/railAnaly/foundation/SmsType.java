package com.attiot.railAnaly.foundation;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by dengsc on 17-9-5.
 */
@Getter
public enum SmsType {

    REGISTER("1", "您（$phone）正在申请注册【xxx】，验证码：$code，本验证码将在10分钟后失效，请及时输入。"),
    FORGOT_PWD("2", "您（$phone）正在通过【xxx】找回密码，验证码：$code，本验证码将在10分钟后失效，请及时输入。"),
	MODIFY_PWD("3", "您（$phone）正在通过【xxx】修改密码，验证码：$code，本验证码将在10分钟后失效，请及时输入。");

    private String code;
    private String message;

    SmsType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage(Map<String, String> map) {
        String result = message;
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                result = result.replace(key, StringUtils.trimToEmpty(value));
            }
        }
        return result;
    }

    public static SmsType get(String code) {
        SmsType[] enumArr = SmsType.class.getEnumConstants() ;
        for(SmsType item:enumArr) {
            if  (item.getCode().equalsIgnoreCase(code)) {
                return item;
            }
        }
        return null;
    }
}
