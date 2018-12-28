/*package com.javaee.mercado.mercadoacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	public Boolean sendMail(String text, String to, String from) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText(text);
		message.setTo(to);
		message.setFrom(from);

		try {
			mailSender.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
*/