package com.syqu.shop.service;

import com.syqu.shop.object.Distributor;

public interface DistributorService {
    Distributor findByUsername(String username);
    Distributor findByEmail(String email);
    Distributor findById(long id);

    void save(Distributor distributor);
}
