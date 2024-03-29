package com.wallet.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * The Main Spring Boot Application class that starts the authorization
 * server.</br>
 * </br>
 * 
 * Note that the server is also a Eureka client so as to register with the
 * Eureka server and be auto-discovered by other Eureka clients.
 *
 * @author Afshar Ahmed
 */
@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
@SessionAttributes("authorizationRequest")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
