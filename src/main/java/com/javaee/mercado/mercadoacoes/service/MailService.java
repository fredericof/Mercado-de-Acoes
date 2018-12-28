package com.javaee.mercado.mercadoacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	private static final String EMAIL_SISTEMA = "fredericofbh@gmail.com";

	public Boolean sendMail(String text, String subject, String to) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject(subject);
		message.setText(text);
		message.setTo(to);
		message.setFrom(EMAIL_SISTEMA);

		try {
			mailSender.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}