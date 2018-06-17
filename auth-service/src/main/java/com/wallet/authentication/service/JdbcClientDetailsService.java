package com.wallet.authentication.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;

import com.wallet.authentication.repository.ClientRepository;

@Configuration
public class JdbcClientDetailsService implements ClientDetailsService, ClientRegistrationService {
	
	private static final Log LOGGER = LogFactory.getLog(JdbcClientDetailsService.class);
	
	@Autowired
	private ClientRepository clientRepository;

	@Override
	public List<ClientDetails> listClientDetails() {
		List<ClientDetails> details = clientRepository.loadClients();
		for (ClientDetails clientDetails : details) {
			LOGGER.info(clientDetails.toString());
		}
		return details;
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		ClientDetails details = null;
		try {
			details = clientRepository.loadClientByClientId(clientId);
		}
		catch (EmptyResultDataAccessException e) {
			throw new NoSuchClientException("No client with requested id: " + clientId);
		}

		return details;
	}
	
	@Override
	public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
		
	}

	@Override
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
		
	}

	@Override
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
		
	}

	@Override
	public void removeClientDetails(String clientId) throws NoSuchClientException {
		
	}
}
