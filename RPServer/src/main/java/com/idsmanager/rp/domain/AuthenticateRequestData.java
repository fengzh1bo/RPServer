package com.idsmanager.rp.domain;

import java.util.List;


public class AuthenticateRequestData {
	private String type;
	private   List<AuthenticateRequest> authenticateRequests;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<AuthenticateRequest> getAuthenticateRequests() {
		return authenticateRequests;
	}
	public void setAuthenticateRequests(
			List<AuthenticateRequest> authenticateRequests) {
		this.authenticateRequests = authenticateRequests;
	}
	
}
