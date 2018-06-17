package com.wallet.authentication.repository;

import java.util.List;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

public interface ClientRepository {
	
	List<ClientDetails> loadClients();
	
	ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException;
}
