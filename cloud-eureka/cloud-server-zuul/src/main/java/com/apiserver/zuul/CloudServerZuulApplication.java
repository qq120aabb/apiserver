package com.apiserver.zuul;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jarvis
 * @date 2018-10-15
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableFeignClients
//@EnableApolloConfig
public class CloudServerZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudServerZuulApplication.class, args);
    }
}
