package com.wallet.authentication.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientRepositoryImpl.class);
	
	private JdbcTemplate jdbcTemplate;
    
	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

		ClientDetails clientDetails = null;
    	String query = "SELECT name, resourceList, password, scope, authorizedGrantTypes, accessTokenValidity, " 
    				 + "refreshTokenValidity, autoApprove " 
    				 + "FROM toauthclient where name=?";
    	try {
    		clientDetails = jdbcTemplate.queryForObject(query, new Object[]{clientId},
	    		new RowMapper<ClientDetails>() {
		            public ClientDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	return getClientDetailsFromSQLResultSet(rs);
		            }
	        });
    	}
    	catch(Exception e) {
    		LOGGER.error("Error occured while retreiving:{}", e);
    	}
    	return clientDetails;
		
	}
	
	@Override
	public List<ClientDetails> loadClients() {
		List<ClientDetails> clients = null;
    	String query = "SELECT id, name, resourceList, password, scope, authorizedGrantTypes, accessTokenValidity, " 
    				 + "refreshTokenValidity, autoApprove " 
    				 + "FROM toauthclient";
    	try {
    		clients = jdbcTemplate.query(query,
	    		new RowMapper<ClientDetails>() {
		            public ClientDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	return getClientDetailsFromSQLResultSet(rs);
		            }
	        });
    	}
    	catch(Exception e) {
    		LOGGER.error("Error occured while retreiving:{}", e);
    	}
    	return clients;
	}
	
	private ClientDetails getClientDetailsFromSQLResultSet(ResultSet rs) throws SQLException {
		BaseClientDetails details = new BaseClientDetails();
    	details.setClientId(rs.getString("name"));
    	details.setClientSecret(rs.getString("password"));
    	details.setAuthorizedGrantTypes(getStringTokens(rs.getString("authorizedGrantTypes")));
    	details.setScope(getStringTokens(rs.getString("scope")));
    	
//TODO    	
//    	client.setAccessTokenValidity(Integer.valueOf(rs.getInt("accessTokenValidity")));
//    	client.setRefreshTokenValidity(Integer.valueOf(rs.getInt("refreshTokenValidity")));
//    	client.setAutoApprove(rs.getBoolean("autoApprove"));
    	return details;
	}
	
	private Collection<String> getStringTokens(String input) {
		Collection<String> tokens = new HashSet<>();
		StringTokenizer stringTokenizer = new StringTokenizer(input);
        while (stringTokenizer.hasMoreTokens()) {
            tokens.add(stringTokenizer.nextToken(","));
        }
        return tokens;
	}
}
