package com.javaee.mercado.mercadoacoes.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaee.mercado.mercadoacoes.config.RabbitMQConfig;
import com.javaee.mercado.mercadoacoes.domain.Message;
import com.javaee.mercado.mercadoacoes.service.AcaoService;

import javassist.tools.rmi.ObjectNotFoundException;

@Component
public class MessageListener {

	@Autowired
	AcaoService acaoService;

	static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

	@RabbitListener(queues = RabbitMQConfig.QUEUE_MESSAGES)
	public void processMessage(Message message) throws ObjectNotFoundException {

		switch (message.getTipoNegociacao()) {
			case "CompraAcao":
				logger.info("Message Received");
				acaoService.compraAcao(message);
				logger.info("Ação comprada");
				break;
	
			case "VendeAcao":
				logger.info("Message Received");
				acaoService.vendeAcao(message);
				logger.info("Ação Vendida");
				break;
	
			default:
				break;
		}

		logger.info("Message Received");
	}

}
