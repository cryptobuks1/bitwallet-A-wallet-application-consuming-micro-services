package com.wallet.authentication.model;

public final class Client {
	private Integer id;
	
	private String name;					// client_id;
	
	private String resourceList;			// resource_ids;
	
	private String password;				// client_secret;
	
	private String scope;					// scope
	
	private String authorizedGrantTypes;	// authorized_grant_types
	
	private String webServerRedirectUri;	// web_server_redirect_uri
	
	private String authorities;				// authorities
	
	private Integer accessTokenValidity;	// access_token_validity
	
	private Integer refreshTokenValidity;	// refresh_token_validity
	
	private String additionalInformation;	// additional_information
	
	private boolean autoApprove;			// autoapprove
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getResourceList() {
		return resourceList;
	}
	
	public void setResourceList(String resourceList) {
		this.resourceList = resourceList;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}
	
	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}
	
	public String getWebServerRedirectUri() {
		return webServerRedirectUri;
	}
	
	public void setWebServerRedirectUri(String webServerRedirectUri) {
		this.webServerRedirectUri = webServerRedirectUri;
	}
	
	public String getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	
	public Integer getAccessTokenValidity() {
		return accessTokenValidity;
	}
	
	public void setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}
	
	public Integer getRefreshTokenValidity() {
		return refreshTokenValidity;
	}
	
	public void setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}
	
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	
	public boolean isAutoApprove() {
		return autoApprove;
	}
	
	public void setAutoApprove(boolean autoApprove) {
		this.autoApprove = autoApprove;
	}

	
}
