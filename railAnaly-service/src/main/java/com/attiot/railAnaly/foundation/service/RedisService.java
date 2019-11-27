package com.attiot.railAnaly.foundation.service;

import org.redisson.Config;
import org.redisson.ReadMode;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class RedisService {

    private RedissonClient redissonClient;
    @Value("${redis.cluster.host}")
    private String host;
    @Value("${redis.cluster.password}")
    private String password;

    public RedissonClient getRedissonClient() {
        if (redissonClient == null || redissonClient.isShutdown()) {
            synchronized (this) {
                if (redissonClient == null || redissonClient.isShutdown()) {
                    Config config = new Config();
                    
                    String[] split = host.split(",");
                    if (split.length == 1) {
                        config.useSingleServer()
                        		.setTimeout(10000)
                                .setAddress(host)
                                .setConnectionPoolSize(500)
                                .setConnectTimeout(10000)
                                .setRetryAttempts(10)
                                .setRetryInterval(1000)
                                .setPassword(password);
                    } else {
                        config.useClusterServers()
                                .addNodeAddress(split)
                                .setPassword(password)
                                .setReadMode(ReadMode.MASTER);
                    }
                    redissonClient = Redisson.create(config);
                }
            }
        }
        return redissonClient;
    }

    @PreDestroy
    public void destroy() {
        if (redissonClient != null) {
            redissonClient.shutdown();
        }
    }

}
