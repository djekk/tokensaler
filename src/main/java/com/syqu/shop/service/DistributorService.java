package com.syqu.shop.service;

import java.util.List;

import com.syqu.shop.domain.Distributor;

public interface DistributorService {
    void save(Distributor user);
 //   void login(String username, String password);
    Distributor findByUsername(String username);
    Distributor findByEmail(String email);
    Distributor findById(long id);
    List<Distributor> findAll();
}
