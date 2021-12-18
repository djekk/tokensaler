package com.syqu.shop.service;

import com.syqu.shop.object.Customer;

public interface CustomerService {
    void save(Customer user);
    void login(String username, String password);
    Customer findByEmail(String email);
    Customer findById(long id);
}
