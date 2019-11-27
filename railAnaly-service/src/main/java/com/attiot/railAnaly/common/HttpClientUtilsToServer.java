package com.attiot.railAnaly.common;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class HttpClientUtilsToServer {
	private static final Logger logger = Logger.getLogger(HttpClientUtilsToServer.class);
	private static String sendpath ;
	private static HttpClient httpclient ;
	private static HttpPost httppost;
	private static HttpClientUtilsToServer instance;
	private String server_run_time = "";
	private ExecutorService threadPool;
	public static HttpClientUtilsToServer getInstance() {
		if(null == instance) {
			instance = new HttpClientUtilsToServer();
		}
		return instance;
	}
	private HttpClientUtilsToServer() {
		sendpath = "/webSocketController.do?sendMessageFromApp";
		httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		server_run_time = Calendar.getInstance().getTimeInMillis()+"";
		threadPool = Executors.newFixedThreadPool(10);
	}

	public  String doGet(String serverpath,String userId,String msgType,String msg) {
		if(null == httppost) {
			httppost = new HttpPost(serverpath+sendpath);
		}
//		System.out.println("HttpClientUtilsToServer init time:"+server_run_time);
		threadPool.execute(new HttpClientRunnable( httpclient, httppost, userId, msgType, msg));
		return "";
	}

	private static String getStringFromJson(JSONObject adata) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for(Object key:adata.keySet()){
			sb.append("\""+key+"\":\""+adata.get(key)+"\",");
		}
		String rtn = sb.toString().substring(0, sb.toString().length()-1)+"}";
		return rtn;
	}


//	public static void main(String args[]) {
//		try {
//			System.out.println("Done.");
//		}catch(Exception e) {
//			log.error("系统内部异常", e);
//		}
//	}
}
