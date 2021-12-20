package com.syqu.shop.listner;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.syqu.shop.event.OnRegistrationCompleteEvent;
import com.syqu.shop.object.Customer;
import com.syqu.shop.service.CustomerService;

@Component
public class RegistrationListener implements  ApplicationListener<OnRegistrationCompleteEvent> {
 
    @Autowired
    private CustomerService service;
 
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Customer user = event.getCustomer();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/regitrationConfirm?token=" + token;
        
        System.out.println(confirmationUrl);
        
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("You registered successfully. We will send you a confirmation message to your email account." + "\r\n" + confirmationUrl);
        mailSender.send(email);
    }
}