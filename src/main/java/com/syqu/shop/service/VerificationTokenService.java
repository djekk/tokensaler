package com.syqu.shop.service;

import com.syqu.shop.object.Customer;
import com.syqu.shop.object.VerificationToken;

public interface VerificationTokenService {
   
	Customer getCustomer(String token);

	void createVerificationToken(Customer customer, String token);

	VerificationToken getVerificationToken(String token);
}
