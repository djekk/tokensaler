package com.syqu.shop.service.impl;

import com.syqu.shop.service.VerificationTokenService;
import com.syqu.shop.object.Customer;
import com.syqu.shop.object.VerificationToken;
import com.syqu.shop.repository.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
 //   private static final Logger logger = LoggerFactory.getLogger(VerificationTokenServiceImpl.class);

    private final VerificationTokenRepository tokenRepository;

    @Autowired
    public VerificationTokenServiceImpl(
    		VerificationTokenRepository tokenRepository) {

        this.tokenRepository = tokenRepository;
    }

    @Override
    public Customer getCustomer(String token) {
    	Customer customer = tokenRepository.findByToken(token).getCustomer();
        return customer;
    }
    
    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }
 
    @Override
    public void createVerificationToken(Customer customer, String token) {
        VerificationToken newToken = new VerificationToken();
        newToken.setToken(token);
        newToken.setCustomer(customer);
        tokenRepository.save(newToken);
    }	
}
