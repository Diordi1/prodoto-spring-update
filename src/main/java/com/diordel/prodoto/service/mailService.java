package com.diordel.prodoto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class mailService {
	@Autowired
	JavaMailSender jms;
	public boolean checkSend(String to,String Subject,String body) throws MessagingException {
		MimeMessage mm=jms.createMimeMessage();
		MimeMessageHelper mmh=new MimeMessageHelper(mm);
		mmh.setTo(to);
		mmh.setText(body);
		mmh.setSubject(Subject);
		mmh.setFrom("tlesmashers@gmail.com");
		jms.send(mm);
		return true;
		
	}
}
