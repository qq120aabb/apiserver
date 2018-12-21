package com.apiserver.swagger;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author jarvis
 * @date 2017/3/12
 */
@Component
@EnableAutoConfiguration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="documentation.swagger")
//@EnableApolloConfig
public class SwaggerServicesConfig {


    List<SwaggerServices> swagger;

    public List<SwaggerServices> getServices() {

        return swagger;
    }

    public void setServices(List<SwaggerServices> swaggerResources) {

        this.swagger = swaggerResources;
    }


}
