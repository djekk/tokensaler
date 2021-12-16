package com.syqu.shop.service;

import com.syqu.shop.domain.Customer;

public interface CustomerService {
    void save(Customer user);
    void login(String username, String password);
    Customer findByUsername(String username);
    Customer findByEmail(String email);
    Customer findById(long id);
}
