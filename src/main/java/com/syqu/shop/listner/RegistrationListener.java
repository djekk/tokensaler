package com.syqu.shop.listner;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.syqu.shop.event.OnRegistrationCompleteEvent;
import com.syqu.shop.mail.MailSender;
import com.syqu.shop.object.Customer;
import com.syqu.shop.service.CustomerService;

@Component
public class RegistrationListener implements  ApplicationListener<OnRegistrationCompleteEvent> {
 
    @Autowired
    private CustomerService service;
 
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Customer user = event.getCustomer();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        
        String confirmationUrl = event.getAppUrl() + "/regitrationConfirm?token=" + token;
        String body = "You registered successfully.Please activate account."
        		+ "\r\n" + confirmationUrl;
                
        System.out.println(confirmationUrl);
        
        MailSender.sendSimpleEmail(user.getEmail(), 
        		"Registration Confirmation", 
        		body);
    }
}