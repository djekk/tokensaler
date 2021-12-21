package com.syqu.shop.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailSender {

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
			e.printStackTrace();
		}	
	}
}
