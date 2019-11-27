package com.attiot.railAnaly.jpush.controller;

import com.attiot.railAnaly.jpush.service.JPushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangbf on 2018/8/20.
 */
@RestController
@Slf4j
@RequestMapping(value = "jpushController")
public class JPushController {
    @Autowired
    private JPushService jpushService;

    @RequestMapping(value = "sendMsgToApp")
    public void sendMsgToApp(HttpServletRequest request, HttpServletResponse response){
        String userId = request.getParameter("userId");
        String msgType = request.getParameter("msgType");
        String msg = request.getParameter("msg");
        jpushService.sendMessage(msgType,userId,msg);
    }
}
