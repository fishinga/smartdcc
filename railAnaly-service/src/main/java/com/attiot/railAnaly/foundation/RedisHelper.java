package com.attiot.railAnaly.foundation;

import com.attiot.railAnaly.common.util.StringUtil;
import com.attiot.railAnaly.foundation.service.RedisService;
import org.redisson.RedissonClient;
import org.redisson.core.RAtomicLong;
import org.redisson.core.RBucket;
import org.redisson.core.RList;
import org.redisson.core.RSet;

import java.util.List;
import java.util.Set;

/**
 * Created by dengsc
 */
public class RedisHelper {

    public static RedissonClient getRedisService() {
        RedisService redisService = (RedisService)SpringContextUtil.getBean(RedisService.class);
        return redisService.getRedissonClient();
    }
    /**
     * 添加字符串对象到REDIS
     * @return
     */
    public static void addString(RedisObject redisObject, String key, String value) {
        RedissonClient client = getRedisService();
        RBucket<String> rBucket= client.getBucket(redisObject.getKey(key));
        //同步放置
        if (redisObject.getTimeOut() == -1) {
        	rBucket.set(value);
        } else {
        	rBucket.set(value, redisObject.getTimeOut(), redisObject.getTimeUnit());
        }
    }

    /**
     * 获取REIDS的字符串对象。
     * @param redisObject
     * @param keySuffix
     * @return
     */
    public static String getString(RedisObject redisObject, String keySuffix){
        RedissonClient client = getRedisService();
        RBucket<String> rBucket= client.getBucket(redisObject.getKey(keySuffix));
        return rBucket.get();
    }

    /**
     * 添加实体对象到REDIS
     * @return
     */
    public static void addObject(RedisObject redisObject, String key, Object obj) {
        RedissonClient client = getRedisService();
        RBucket<Object> rBucket= client.getBucket(redisObject.getKey(key));
        // 同步放置
        if (redisObject.getTimeOut() == -1) {
            rBucket.set(obj);
        } else {
            rBucket.set(obj, redisObject.getTimeOut(), redisObject.getTimeUnit());
        }
    }
    /**
     * 获取REIDS的实体对象。
     * @param redisObject
     * @param keySuffix
     * @return
     */
    public static Object getObject(RedisObject redisObject, String keySuffix) {
        RedissonClient client = getRedisService();
        RBucket<Object> rBucket= client.getBucket(redisObject.getKey(keySuffix));
        return rBucket.get();
    }
    /**
     * 自增原子变量。
     * @param redisObject redisObject
     * @param key key
     * @return Long
     */
    public static Long getAtomicLong(RedisObject redisObject, String key){
        RedissonClient client = getRedisService();
        RAtomicLong atomicLong = client.getAtomicLong(redisObject.getKey(key));
        atomicLong.expire(redisObject.getTimeOut(), redisObject.getTimeUnit());
        if (!atomicLong.isExists()) {
            atomicLong.set(0);
        }
        return atomicLong.incrementAndGet();
    }

    /**
     * 添加用户在线对象。
     * @param redisObject
     * @param key
     * @param location
     */
    /*public static void addUserOnlineEntity(RedisObject redisObject, String key, UserOnlineEntity location) {

        RedissonClient client = getRedisService();
        RBucket<UserOnlineEntity> bucket = client.getBucket(redisObject.getKey(key));
        if (redisObject.getTimeOut() == -1) {
            bucket.set(location);
        } else {
            bucket.set(location, redisObject.getTimeOut(), redisObject.getTimeUnit());
        }
    }*/

    /**
     * 删除用户在线对象。
     * @param redisObject
     * @param key
     */
    public static void delUserOnlineEntity(RedisObject redisObject, String key) {
        if (StringUtil.isEmpty(key)) {
            return ;
        }
        RedissonClient client = getRedisService();
        /*RBucket<UserOnlineEntity> bucket = client.getBucket(redisObject.getKey(key));
        bucket.delete();*/
    }
    /**
     * 获取REIDS的用户在线对象。
     * @param redisObject
     * @param key
     * @return
     */
    /*public static UserOnlineEntity getUserOnlineEntity(RedisObject redisObject, String key) {
        RedissonClient client = getRedisService();
        RBucket<UserOnlineEntity> rBucket= client.getBucket(redisObject.getKey(key));
        return rBucket.get();
    }*/
    /**
     * 添加用户在线定位信息。
     * @param redisObject
     * @param key
     * @param location
     */
    /*public static void addUserOnlineLocation(RedisObject redisObject, String key, UserOnlineLocation location) {

        RedissonClient client = getRedisService();
        RBucket<UserOnlineLocation> bucket = client.getBucket(redisObject.getKey(key));
        bucket.set(location, redisObject.getTimeOut(), redisObject.getTimeUnit());
    }*/
    /**
     * 获取REIDS的用户在线定位信息。
     * @param redisObject
     * @param key
     * @return
     */
   /* public static UserOnlineLocation getUserOnlineLocation(RedisObject redisObject, String key) {
        RedissonClient client = getRedisService();
        RBucket<UserOnlineLocation> rBucket= client.getBucket(redisObject.getKey(key));
        return rBucket.get();
    }*/
    /**
     * 添加列表对象到REDIS
     * @param redisObject
     * @param key
     * @param list
     * @param <T>
     */
    public static <T> void addList(RedisObject redisObject, String key, List<T> list) {
        RedissonClient client = getRedisService();
        RList<T> rList = client.getList(redisObject.getKey(key));
        rList.expire(redisObject.getTimeOut(), redisObject.getTimeUnit());
        rList.addAll(list);
    }

    /**
     * 获取列表对象
     * @param redisObject
     * @param key
     * @param <T>
     * @return
     */
    public static <T> List<T> getList(RedisObject redisObject, String key) {
        RedissonClient client = getRedisService();
        RList<T> rList = client.getList(redisObject.getKey(key));
        if (rList.isExists()) {
            return rList.isEmpty() ? null : rList;
        }
        return null;
    }
    /**
     * 添加列表对象到REDIS
     * @param redisObject
     * @param key
     * @param value
     */
    public static <T> void addUserOnlineSet(RedisObject redisObject, String key, T value) {
        RedissonClient client = getRedisService();
        RSet<T> set = client.getSet(redisObject.getKey(key));
        set.add(value);
    }

    /**
     * 获取列表对象
     * @param redisObject
     * @param key
     * @param <T>
     * @return
     */
    public static <T> Set<T> getUserOnlineSet(RedisObject redisObject, String key) {
        RedissonClient client = getRedisService();
        RSet<T> rSet = client.getSet(redisObject.getKey(key));
        if (rSet.isExists()) {
            return rSet.isEmpty() ? null : rSet;
        }
        return null;
    }

}
