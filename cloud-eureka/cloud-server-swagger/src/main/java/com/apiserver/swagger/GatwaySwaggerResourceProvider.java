package com.apiserver.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarvis
 * @date 2017/3/12
 */
@Component
@Primary
@EnableAutoConfiguration
public class GatwaySwaggerResourceProvider implements SwaggerResourcesProvider {

    @Autowired
    private SwaggerServicesConfig swaggerServicesConfig;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        swaggerServicesConfig.getServices().forEach(service -> {
            resources.add(swaggerResource(service.getName(),service.getUrl(), service.getVersion()));
        });

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}
