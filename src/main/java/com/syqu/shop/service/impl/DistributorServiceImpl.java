package com.syqu.shop.service.impl;

import com.syqu.shop.service.DistributorService;
import com.syqu.shop.object.Distributor;
import com.syqu.shop.repository.DistributorRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistributorServiceImpl implements DistributorService {
    private static final Logger logger = LoggerFactory.getLogger(DistributorServiceImpl.class);
    private final DistributorRepository distributorRepository;

    @Autowired
    public DistributorServiceImpl(DistributorRepository distributorRepository) {
        this.distributorRepository = distributorRepository;
    }

    @Override
    public void save(Distributor Distributor) {
        distributorRepository.save(Distributor);
    }

    @Override
    public Distributor findByUsername(String username) {
        return distributorRepository.findByUsername(username);
    }

    @Override
    public Distributor findByEmail(String email) {
        return distributorRepository.findByEmail(email);
    }

    @Override
    public Distributor findById(long id) {
        return distributorRepository.findById(id);
    }
}
