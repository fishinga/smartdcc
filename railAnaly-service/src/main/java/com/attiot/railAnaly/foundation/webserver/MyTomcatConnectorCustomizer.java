package com.attiot.railAnaly.foundation.webserver;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;

/**
 * Created by dengsc on 2017/10/25.
 */
public class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {

    private int maxConnections;
    private int maxThreads;
    private int connectionTimeout;
    public MyTomcatConnectorCustomizer(int maxConnections, int maxThreads, int connectionTimeout) {
        this.maxConnections = maxConnections;
        this.maxThreads = maxThreads;
        this.connectionTimeout = connectionTimeout;
    }
    public void customize(Connector connector) {
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        //设置最大连接数
        protocol.setMaxConnections(maxConnections);
        //设置最大线程数
        protocol.setMaxThreads(maxThreads);
        protocol.setConnectionTimeout(connectionTimeout);
    }
}
