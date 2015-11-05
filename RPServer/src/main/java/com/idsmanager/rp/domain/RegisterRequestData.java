package com.idsmanager.rp.domain;

import java.util.List;


public class RegisterRequestData {
	   private  List<AuthenticateRequest> authenticateRequests;
	    private  List<RegisterRequest> registerRequests;
		public List<AuthenticateRequest> getAuthenticateRequests() {
			return authenticateRequests;
		}
		public void setAuthenticateRequests(
				List<AuthenticateRequest> authenticateRequests) {
			this.authenticateRequests = authenticateRequests;
		}
		public List<RegisterRequest> getRegisterRequests() {
			return registerRequests;
		}
		public void setRegisterRequests(List<RegisterRequest> registerRequests) {
			this.registerRequests = registerRequests;
		}
	    
}
