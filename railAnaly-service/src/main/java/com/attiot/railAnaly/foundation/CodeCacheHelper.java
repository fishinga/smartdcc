package com.attiot.railAnaly.foundation;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 功能/模块：随机码生成，验证工具类<br/>
 * 类描述：<br/>
 * 修订历史：：<br/>
 * 日期  作者  参考  描述：<br/>
 *
 * @author dengsc
 * @version 1.0 19-9-19
 * @see
 */
public class CodeCacheHelper {

    public static CodeCache random(RedisObject redisObject, String phone, SmsType type) {
        return random(redisObject, phone, type, 0);
    }

    private static CodeCache random(RedisObject redisObject, String phone, SmsType type, int num) {
        if (num > 500) {
            return null;
        }
        int codeIndex = NumberUtils.toInt(RandomStringUtils.randomNumeric(3));
        String redisKeySuffix = populateCodeKeySuffix(phone, type, codeIndex);
        if (RedisHelper.getString(redisObject, redisKeySuffix) != null) {
            return random(redisObject, phone, type, ++num);
        } else {
            CodeCache cache = new CodeCache();
            String random = RandomStringUtils.randomNumeric(6);
            cache.setCode(random);
            cache.setCodeIndex(codeIndex);
            RedisHelper.addString(RedisObject.SMS_CODE, redisKeySuffix, random);
            return cache;
        }
    }

    /**
     * 验证缓存的code与输入的是否一致。
     * @param redisObject
     * @param redisKeySuffix
     * @param random
     * @return
     */
    public static boolean valid(RedisObject redisObject, String redisKeySuffix, String random) {
        return StringUtils.equals(RedisHelper.getString(redisObject, redisKeySuffix), random);
    }

    public static void addRepeatInfo(String redisKeySuffix, Object obj) {
        RedisHelper.addObject(RedisObject.SMS_CODE_REPEAT_TIMES, redisKeySuffix, obj);
    }

    public static Object getRepeatInfo(String redisKeySuffix) {
        return RedisHelper.getObject(RedisObject.SMS_CODE_REPEAT_TIMES, redisKeySuffix);
    }
    /**
     * 短信验证码保存在REDIS里的KEY的后缀。phone +　type.getCode()
     * @param phone
     * @param type
     * @return
     */
    public static String populateCodeKeySuffix(String phone, SmsType type, int codeIndex) {
        return phone + type.getCode() + codeIndex;
    }


}
