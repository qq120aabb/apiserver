package com.apiserver.consumer.console.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jarvis
 * @date 2018-11-9
 */
@Configuration
public class HystrixServlet {

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.setName("HystrixServlet");
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        return registrationBean;
    }

}
