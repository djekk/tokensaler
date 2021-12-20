package com.syqu.shop.service;

import com.syqu.shop.object.Customer;
import com.syqu.shop.object.VerificationToken;

public interface CustomerService {

    void login(String username, String password);
    Customer findByEmail(String email);
    Customer findById(long id);
    
    void save(Customer user);
	void saveDistributor(Customer customer);
	void saveRegisteredUser(Customer user);
	
	
	Customer registerNewUserAccount(Customer userDto) throws UserAlreadyExistException;

	Customer getCustomer(String verificationToken);


	void createVerificationToken(Customer user, String token);

	VerificationToken getVerificationToken(String VerificationToken);
}
