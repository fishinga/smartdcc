package com.attiot.railAnaly.foundation.service;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class JPushService {

    @Value("${jpush.app.key}")
    private String APP_KEY;
    @Value("${jpush.master.secret}")
    private String MASTER_SECRET;
    @Value("${jpush.apns.production}")
    private boolean apnsProduction;

    private ExecutorService executorService;
    private volatile JPushClient jpushClient;

    public JPushService() {
        this.executorService = Executors.newFixedThreadPool(100, (r) -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("PushService Thread-" + t.getId());
            return t;
        });
    }

    /**
     * 异步推送。
     * registrationId  极光椎送ID
     * content 内容
     */
    public void pushAsync(String registrationId, String content) {

        executorService.submit(() -> {
            try {
                buildPayload(registrationId, content);
            } catch (IllegalArgumentException e) {
                log.error("推送失败", e);
            }
        });
    }

    /**
     * 推送消息到安卓平台。
     */
    public void pushAndroid(String registrationId, String alert, String title) {
        PushPayload payload = buildAndroidPayload(registrationId, alert, title);
        doPush(payload);
    }

    /**
     * 推送消息到IOS平台。
     */
    public void pushIos(String registrationId, String alert) {
        PushPayload payload = buildIosPayload(registrationId, alert);
        doPush(payload);
    }

    /**
     * 构建推送对象：android and ios平台。
     * @return
     */
    public PushPayload buildPayload(String registrationId, String alert) {

        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.registrationId(registrationId))
                .setNotification(Notification.alert(alert))
                .build();
    }

    /**
     * 构建推送对象：所有平台，推送目标是别名为 "alias1"，通知内容为 ALERT。
     * @return
     */
    public PushPayload buildAndroidPayload(String registrationId, String alert, String title) {

        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationId))
                .setNotification(Notification.android(alert, title, null))
                .build();
    }
    /**
     *
     * @return
     */
    public PushPayload buildIosPayload(String registrationId, String alert) {

        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationId))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
                                .setBadge(5)
                                .setSound("happy")
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(apnsProduction)
                        .build())
                .build();
    }

    /**
     * For push, all you need do is to build PushPayload object.
     * @param payload
     */
    public void doPush(PushPayload payload) {

        JPushClient jpushClient = getJPushClient();
        try {
            PushResult result = jpushClient.sendPush(payload);
            log.info("Got result - " + result);

        } catch (APIConnectionException e) {
            log.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            log.error("Should review the error, and fix the request", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
        }
    }

    /**
     * 获取JPush客户端。
     * @return
     */
    private JPushClient getJPushClient() {
        if (jpushClient == null) {
            synchronized (this) {
                if (jpushClient == null) {
                    try {
                        jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
                    } catch (IllegalArgumentException e) {
                        log.error("系统内部异常", e);
                    }
                }
            }
        }
        return jpushClient;
    }
}
