package com.wallet.authentication.config;

import java.security.KeyPair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.wallet.authentication.service.JdbcClientDetailsService;
import com.wallet.authentication.service.JdbcUserDetailsService;;

/**
 * The Class defines the authorization server that would authenticate the user
 * and define the client that seeks authorization on the resource owner's
 * behalf.
 * 
 * @author Afshar Ahmed
 */
@Configuration
@EnableAuthorizationServer
public class OAuthServerConfiguration extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JdbcUserDetailsService jdbcUserDetailsService;
	
	@Autowired
	private JdbcClientDetailsService jdbcClientDetailsService;
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		//TODO Remove all hardcodings
		//Keypair is the alias name -> afsharkeystore.jks / password / authkey
		KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("afsharkeystore.jks"), "password".toCharArray()).getKeyPair("authkey");
		converter.setKeyPair(keyPair);
		return converter;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");//or permitAll()
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(jdbcClientDetailsService);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.authenticationManager(authenticationManager)
			.accessTokenConverter(jwtAccessTokenConverter())
			.userDetailsService(jdbcUserDetailsService);
	}

	/**
	 * Setup {@link AuthenticationManagerBuilder} with initial configuration.
	 * 
	 * Higher priority, since lesser ordered value indicate higher priority.
	 * {@link Ordered#LOWEST_PRECEDENCE} has value as {@link Integer#MAX_VALUE}
	 * 
	 * @author Afshar Ahmed
	 */
	@Configuration
	@Order(Ordered.LOWEST_PRECEDENCE - 30)
	protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {
		@Autowired
		private JdbcUserDetailsService jdbcUserDetailsService;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(jdbcUserDetailsService);
		}
	}
}
