package com.syqu.shop.service.impl;

import com.syqu.shop.object.Customer;
import com.syqu.shop.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final CustomerRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(CustomerRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email){
        Customer user = userRepository.findByEmail(email);
        
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        
        if (user != null) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            if (Objects.equals(email, "admin@example.com")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }else {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            logger.debug(String.format("Customer with e-mail: %s and password: %s created.", user.getEmail(), user.getPassword()));
            
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), 
                    user.getPassword(), 
                    user.isEnabled(), 
                    accountNonExpired, 
                    credentialsNonExpired, 
                    accountNonLocked, 
                    authorities);
        }else{
            throw new UsernameNotFoundException("Email " + email + " not found!");
        }
    }


}
