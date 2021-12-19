package com.syqu.shop.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MyMailSender {

	public MyMailSender() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
    private static JavaMailSender javaMailSender;
	
	public static void sendEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("djekk28@gmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        try {
			javaMailSender.send(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}
