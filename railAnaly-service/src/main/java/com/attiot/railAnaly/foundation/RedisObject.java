package com.attiot.railAnaly.foundation;

import java.util.concurrent.TimeUnit;

import com.attiot.railAnaly.common.constant.RailAnalyConstant;

/**
 * redis缓存抽象类，缓存在redis的对象必须实现该类
 * 默认对象的缓存时间为10分钟
 * Created by dengsc
 */
public enum RedisObject {

    USER_SESSION("u_session:", 30, TimeUnit.MINUTES),
    ATOMIC_LONG("atomic_long:", 30, TimeUnit.MINUTES),
    SMS_CODE("sms_code:", 5, TimeUnit.MINUTES),
    SMS_CODE_REPEAT_TIMES("sc_rt:", RailAnalyConstant.ONE_DAY_IN_SEC, TimeUnit.SECONDS),
	USER_ONLINE_ENTITY("uo:", -1, TimeUnit.SECONDS),
	// Save the corresponding relationship between user ID and TOKEN
	USER_ID_TOKEN_MAPING("uid_token:", -1, TimeUnit.SECONDS),
	USER_ONLINE_LOCATION("uol:", 10, TimeUnit.MINUTES);

    private String prefix;
    // -1 表示不带生存时间、永不过期。
    private Long timeout;
    private TimeUnit timeUnit;

    RedisObject(String prefix, long timeout, TimeUnit timeUnit) {
        this.prefix = prefix;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }
    /**
     * 获取缓存在redis中的键值
     *
     * @return
     */
    public String getKey(Object keySuffix) {
        return prefix + keySuffix.toString();
    }

    /**
     * 获取缓存时间
     *
     * @return 默认10分钟
     */
    public Long getTimeOut() {
        return timeout;
    }

    /**
     * 获取缓存时间的单位
     *
     * @return 默认单位为分钟
     */
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

}
