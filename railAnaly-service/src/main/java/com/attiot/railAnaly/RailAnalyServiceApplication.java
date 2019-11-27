package com.attiot.railAnaly;

import com.attiot.railAnaly.foundation.SpringContextUtil;
import com.attiot.railAnaly.foundation.filters.HTTPBasicAuthorize;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 分布式服务启动类。
 * @author dengsc
 * @date 2017-09-05
 * @version V1.0
 */
@EnableTransactionManagement
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.attiot.railAnaly","plugins"})
public class RailAnalyServiceApplication {

	public static void main(String[] args) {
		ApplicationContext railAnalyServiceApplication = new SpringApplicationBuilder(RailAnalyServiceApplication.class)
				.web(true)
				.run(args);
		SpringContextUtil.setApplicationContext(railAnalyServiceApplication);
	}
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		HTTPBasicAuthorize httpBasicFilter = new HTTPBasicAuthorize();
		registrationBean.setFilter(httpBasicFilter);
		List<String> urlPatterns = new ArrayList();
		urlPatterns.add("/inspect/*");
		/*urlPatterns.add("/cust/*");
		urlPatterns.add("/Phone/*");
		urlPatterns.add("/order/*");
		urlPatterns.add("/csc/*");
		urlPatterns.add("/notice/*");
		urlPatterns.add("/loc/*");
		urlPatterns.add("/statistics/*");
		urlPatterns.add("/cooprate/*");
		urlPatterns.add("/Wx/UnUserBind");
		urlPatterns.add("/Wx/PrePay"); */
		//urlPatterns.add("/file/*");
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}


}
