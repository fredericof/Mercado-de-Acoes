package com.javaee.mercado.mercadoacoes.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.mercado.mercadoacoes.config.RabbitMQConfig;
import com.javaee.mercado.mercadoacoes.domain.Message;

@Service
public class MessageService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendMessage(Message message) {
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_MESSAGES, message);
	}
}
