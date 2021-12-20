package com.syqu.shop.service.impl;

import com.syqu.shop.service.CustomerService;
import com.syqu.shop.service.UserAlreadyExistException;
import com.syqu.shop.object.Customer;
import com.syqu.shop.object.VerificationToken;
import com.syqu.shop.repository.CustomerRepository;
import com.syqu.shop.repository.VerificationTokenRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final VerificationTokenRepository tokenRepository;

    @Autowired
    public CustomerServiceImpl(
    		CustomerRepository userRepository, 
    		BCryptPasswordEncoder bCryptPasswordEncoder, 
    		UserDetailsService userDetailsService, 
    		AuthenticationManager authenticationManager,
    		VerificationTokenRepository tokenRepository) {
        this.customerRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void save(Customer customer) {
    	customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
    	String sp = customer.getPassword();
    	customer.setPasswordConfirm(sp); 
        customerRepository.save(customer);
    }
    
    @Override
    public void saveDistributor(Customer customer) { 
        customerRepository.save(customer);
    }

    @Override
    public void login(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(token);

        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
            logger.debug(String.format("Customer  %s logged in successfully!", email));
        }else{
            logger.error(String.format("Error with %s authentication!", email));
        }
    }
   
    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer findById(long id) {
        return customerRepository.findById(id);
    }
    
    @Override
    public Customer registerNewUserAccount(Customer userDto) throws UserAlreadyExistException {
      
        Customer user = new Customer();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPasswordConfirm(userDto.getPasswordConfirm());
        user.setDistributor(userDto.getDistributor());
    
        return customerRepository.save(user);
    }

    private boolean emailExist(String email) {
        return customerRepository.findByEmail(email) != null;
    }
    
    @Override
    public Customer getCustomer(String verificationToken) {
    	Customer user = tokenRepository.findByToken(verificationToken).getCustomer();
        return user;
    }
    
    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
    
    @Override
    public void saveRegisteredUser(Customer user) {
    	customerRepository.save(user);
    }
    
    @Override
    public void createVerificationToken(Customer user, String token) {
        VerificationToken myToken = new VerificationToken();
        myToken.setToken(token);
        myToken.setCustomer(user);
        tokenRepository.save(myToken);
    }
}
