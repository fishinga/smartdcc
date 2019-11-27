package com.attiot.railAnaly.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/30.
 */
@Slf4j
public class HttpClientRunnable implements Runnable{
    private HttpClient httpclient;
    private HttpPost httppost;
    private String userId;
    private String msgType;
    private String msg;
    public HttpClientRunnable(HttpClient httpclient,HttpPost httppost,String userId,String msgType,String msg) {
        this.httpclient = httpclient;
        this.httppost = httppost;
        this.userId = userId;
        this.msgType = msgType;
        this.msg = msg;
    }
    public void run() {
        String strResult = "";
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("userId", userId));
            nameValuePairs.add(new BasicNameValuePair("msgType", msgType));
            nameValuePairs.add(new BasicNameValuePair("msg", msg));
            httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == 200) {
					/*读返回数据*/
                strResult = EntityUtils.toString(response
                        .getEntity());
            } else {
                String err = response.getStatusLine().getStatusCode() + "";
                strResult += "发送失败:" + err;
            }
        } catch (ClientProtocolException e) {
            log.error("系统内部异常", e);
        } catch (IOException e) {
            log.error("系统内部异常", e);
        }
    }
}
