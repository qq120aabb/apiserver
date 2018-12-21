package com.apiserver.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jarvis
 * @date 2018-10-15
 */
@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableSwagger2
@EnableEurekaClient
public class CloudServerSwaggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudServerSwaggerApplication.class, args);
	}
}
