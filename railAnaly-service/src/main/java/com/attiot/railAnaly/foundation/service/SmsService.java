package com.attiot.railAnaly.foundation.service;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.attiot.railAnaly.common.constant.RailAnalyConstant;
import com.attiot.railAnaly.common.util.JacksonUtil;
import com.attiot.railAnaly.foundation.CodeCache;
import com.attiot.railAnaly.foundation.CodeCacheHelper;
import com.attiot.railAnaly.foundation.ParamVerifyUtil;
import com.attiot.railAnaly.foundation.RedisObject;
import com.attiot.railAnaly.foundation.SmsType;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by dengsc on 17-9-5.
 */
@Service
@Slf4j
public class SmsService {

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    static final String accessKeyId = "LTAIV9C8SGKjeRse";
    static final String accessKeySecret = "YdDrqZHAFkSqIL34lQBuqwHVgFWhFf";

    private static final long IP_INTERVAL_TIME = 60000; // 时间间隔60秒
    private static final long IP_INTERVAL_COUNT = 10; //单循环内次数限制10次
    private static final long IP_DAY_COUNT = 40; // 24小时内次数限制40次

    private static final long PHONE_INTERVAL_TIME = 60000; // 时间间隔60秒
    private static final long PHONE_INTERVAL_COUNT = 1; // 单循环内次数限制1次
    private static final long PHONE_DAY_COUNT = 20; // 24小时内次数限制20次

    @Value("${sms.signName}")
    private String signName;

    @Value("${sms.verification-code.templateCode}")
    private String verificationCodeTemplateCode;

    private volatile IAcsClient acsClient;

    private IAcsClient getAcsClient() {
        if (acsClient == null) {
            synchronized (this) {
                if (acsClient == null) {
                    try {
                        //可自助调整超时时间
                        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
                        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
                        //初始化acsClient,暂不支持region化
                        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
                        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
                        acsClient = new DefaultAcsClient(profile);
                    } catch (ClientException e) {
                        log.error("系统内部异常", e);
                    }
                }
            }
        }
        return acsClient;
    }

//    /**
//     * 发送验证码。
//     * @param phone
//     * @param code
//     * @return
//     */
//    public void sendCode(String phone, String code) {
//        Map paramMap = Maps.newHashMap();
//        paramMap.put("code", code);
//        String templateParam = JacksonUtil.fromObject(paramMap);
//        sendSms(phone, signName, verificationCodeTemplateCode, templateParam);
//    }

//    /**
//     * 发送短信通知。
//     * @param phone
//     * @param code
//     * @return
//     */
//    public void sendNotice(String phone, String noticeContent) {
////        Map paramMap = Maps.newHashMap();
////        paramMap.put("notice", noticeContent);
////        String templateParam = JacksonUtil.fromObject(paramMap);
////        sendSms(phone, signName, verificationCodeTemplateCode, templateParam);
//    }
    
    public SendSmsResponse sendSms(String phone, String signName, String templateCode, String templateParam) {

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        request.setTemplateParam(templateParam);
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = getAcsClient().getAcsResponse(request);
        } catch (ClientException e) {
            log.error("系统内部异常", e);
        }

        return sendSmsResponse;
    }

//    /**
//     * 随机生成验证码。
//     * @param phone
//     * @param smsType
//     * @return
//     */
//    public CodeCache random(String phone, SmsType smsType) {
//        CodeCache codeCache = CodeCacheHelper.random(RedisObject.SMS_CODE, phone, smsType);
//        return codeCache;
//    }

    /**
     * 验证短信验证码是否匹配。
     * @param phone
     * @param type
     * @param code
     * @return
     */
    public boolean valid(String phone, SmsType type, String code, int codeIndex) {
        ParamVerifyUtil.verifyNotBlank(phone, code);
        String redisKeySuffix = CodeCacheHelper.populateCodeKeySuffix(phone, type, codeIndex);
        return CodeCacheHelper.valid(RedisObject.SMS_CODE, redisKeySuffix, code);
    }

    public boolean validPhone(String phone) {
        return valid(phone, PHONE_INTERVAL_TIME, PHONE_INTERVAL_COUNT, PHONE_DAY_COUNT);
    }

    public boolean validIp(String ip) {
        return valid(ip, IP_INTERVAL_TIME, IP_INTERVAL_COUNT, IP_DAY_COUNT);
    }

    public boolean valid(String phone, String ip) {
        return validPhone(phone) && validIp(ip);
    }


    private boolean valid(String value, long intervalOverTime, long intervalTimeOvers, long dayTimeOvers) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        String keySuffix = populateCodeRTRedisKeySuffix(value);
        RepeatInfo repeatInfo = (RepeatInfo)CodeCacheHelper.getRepeatInfo(keySuffix);
        if (repeatInfo != null) {
            if (validRepeatCount(repeatInfo, intervalOverTime, intervalTimeOvers, dayTimeOvers)) {
                return false;
            }
        } else {
            long time = System.currentTimeMillis();
            repeatInfo = new RepeatInfo();
            repeatInfo.setKey(value);
            repeatInfo.setIntervalCount(1);
            repeatInfo.setDayCount(1);
            repeatInfo.setCreateTime(time);
            CodeCacheHelper.addRepeatInfo(keySuffix, repeatInfo);
        }
        return true;
    }


    /**
     * 验证是否合法
     *
     * @param repeatInfo
     * @return true 禁止发送短信
     */
    private boolean validRepeatCount(RepeatInfo repeatInfo, long intervalTime, long intervalCountLimit, long dayCountLimit) {
        String key = repeatInfo.getKey();
        long currentTimeMillis = System.currentTimeMillis();
        long createTime = repeatInfo.getCreateTime();
        long intervalCount = repeatInfo.getIntervalCount();
        long dayCount = repeatInfo.getDayCount();
        // 发送次数大于单次循环允许的最大值时。
        if (intervalCount >= intervalCountLimit) {
            if (currentTimeMillis <= createTime + intervalTime) {
                return true;
            } else {
                repeatInfo.resetIntervalCount();
            }
        }
        // 发送次数大于24小时内允许的最大值时。
        if (dayCount >= dayCountLimit) {
            if (currentTimeMillis <= createTime + RailAnalyConstant.ONE_DAY_IN_SEC * 1000 ) {
                return true;
            } else {
                repeatInfo.resetDayCount();
            }
        }
        repeatInfo.incrementCount();
        String keySuffix = populateCodeRTRedisKeySuffix(key);
        CodeCacheHelper.addRepeatInfo(keySuffix, repeatInfo);
        return false;
    }

    @Setter
    @Getter
    private static class RepeatInfo implements Serializable {
        private static final long serialVersionUID = -5293192659955913490L;
        private String key;
        private long createTime;
        private long intervalCount;
        private long dayCount;

        public void incrementCount() {
            intervalCount++;
            dayCount++;
        }
        public void resetIntervalCount() {
            intervalCount = 0;
        }
        public void resetDayCount() {
            dayCount = intervalCount;
        }
    }

    private static String populateCodeRTRedisKeySuffix(String key) {
        return key;
    }

}
