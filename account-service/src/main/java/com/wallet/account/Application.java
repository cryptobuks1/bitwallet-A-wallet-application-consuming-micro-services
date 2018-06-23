package com.wallet.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/*
@EnableCircuitBreaker
 */
@EnableDiscoveryClient  //TODO isThisNeeded?
@EnableEurekaClient		//
@EnableResourceServer	//
@EnableJpaRepositories	//
@SpringBootApplication
@EnableOAuth2Client
public class Application {

	/**
	 * URL uses the logical name of account-service - upper or lower case,
	 * doesn't matter.
	 */
	public static final String ACCOUNTS_SERVICE_URL = "http://ACCOUNTS-SERVICE";
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
