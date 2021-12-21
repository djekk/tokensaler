package com.syqu.shop.service;

import com.syqu.shop.object.Customer;

public interface CustomerService {

    void login(String username, String password);
    Customer findByEmail(String email);
    Customer findById(long id);
    
    void save(Customer customer);
	void update(Customer customer);
		
	Customer registerNewUserAccount(Customer customerForm) throws UserAlreadyExistException;
}
