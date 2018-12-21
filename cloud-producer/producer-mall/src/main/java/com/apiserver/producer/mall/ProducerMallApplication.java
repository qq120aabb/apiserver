package com.apiserver.producer.mall;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author jarvis
 * @date 2018-10-15
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.apiserver.producer.mall.mapper")
//@EnableApolloConfig
public class ProducerMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerMallApplication.class, args);
	}

	@Configuration
	public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().permitAll()
					.and().csrf().disable();
		}
	}
}
