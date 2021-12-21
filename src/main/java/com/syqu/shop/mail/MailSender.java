package com.syqu.shop.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender {
    
	private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    
	@Autowired
    private static JavaMailSender javaMailSender;
	    
	public static void sendSimpleEmail(String recipientAddress,
			String subject,
			String body) {
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(recipientAddress);
		msg.setSubject(subject);
		msg.setText(body);
	    try {
			javaMailSender.send(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(String.valueOf(e.toString()));
		}	
	}
}
