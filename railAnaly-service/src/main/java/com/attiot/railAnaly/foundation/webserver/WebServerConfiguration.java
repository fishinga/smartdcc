package com.attiot.railAnaly.foundation.webserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dengsc on 2017/10/25.
 */
@Configuration
public class WebServerConfiguration {
    @Value("${web.server.max.connections}")
    private int maxConnections;
    @Value("${web.server.max.threads}")
    private int maxThreads;
    @Value("${web.server.max.connection.timeout}")
    private int connectionTimeout;
    @Bean
    public EmbeddedServletContainerFactory createEmbeddedServletContainerFactory() {
        TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
        tomcatFactory.addConnectorCustomizers(new MyTomcatConnectorCustomizer(maxConnections, maxThreads, connectionTimeout));
        return tomcatFactory;
    }
}
