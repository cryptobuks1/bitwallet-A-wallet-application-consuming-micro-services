package com.wallet.authentication.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class JdbcUserDetailsService implements UserDetailsService {

	private List<UserDetailsService> userDetailsServices = new LinkedList<>();

	public JdbcUserDetailsService() {
		// Default constructor
	}

	/**
	 * Add the default user detail service or any other user detail service so
	 * that we can validate the user.
	 *
	 * @param userDetailsServices
	 */
	public void addService(UserDetailsService userDetailsService) {
		userDetailsServices.add(userDetailsService);
	}

	/**
	 * Locates the user based on the username. Case insensitive for now :-( 
	 * so the <code>UserDetails</code> returned may have a username of different case.
	 *
	 * @param username 	The username identifying the user whose data is required.
	 *
	 * @return 	A fully populated user record (never <code>null</code>)
	 *
	 * @throws UsernameNotFoundException if the user could not be found or the user has no
	 * GrantedAuthority
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		if (userDetailsServices != null) {
			for (UserDetailsService userDetailsService : userDetailsServices) {
				try {
					final UserDetails details = userDetailsService.loadUserByUsername(userName);
					if (details != null) {
						return details;
					}
				} catch (UsernameNotFoundException exception) {
					assert exception != null;
				} catch (Exception exception) {
					throw exception;
				}
			}
		}

		throw new UsernameNotFoundException("Unknown user");
	}
}
