package com.syqu.shop.object;

import org.springframework.stereotype.Component;

@Component
public class LoginDistributorPost {
	 
	    private String username;
		private String password;
	    private String distributor;
	    
	    public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getDistributor() {
			return distributor;
		}

		public void setDistributor(String distributor) {
			this.distributor = distributor;
		}
}
