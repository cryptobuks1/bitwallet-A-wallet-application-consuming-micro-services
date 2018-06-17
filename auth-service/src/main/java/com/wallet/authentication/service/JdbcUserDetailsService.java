package com.wallet.authentication.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.wallet.authentication.model.User;
import com.wallet.authentication.repository.UserRepository;

@Configuration
public class JdbcUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	/**
	 * Finds the user based on username and returns it.
	 *
	 * @param username 	The username identifying the user whose data is required.
	 * @return {@link User}	A fully populated user record.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUser(username);
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(
													user.getUsername(), 
													user.getPassword(), 
													Arrays.asList(authority));
		return userDetails;
	}
}