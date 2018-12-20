package com.javaee.mercado.mercadoacoes.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaee.mercado.mercadoacoes.config.RabbitMQConfig;
import com.javaee.mercado.mercadoacoes.domain.Acao;
import com.javaee.mercado.mercadoacoes.domain.Comprador;
import com.javaee.mercado.mercadoacoes.domain.Message;
import com.javaee.mercado.mercadoacoes.repositories.AcaoRepository;
import com.javaee.mercado.mercadoacoes.service.AcaoService;
import com.javaee.mercado.mercadoacoes.service.MailService;

import javassist.tools.rmi.ObjectNotFoundException;

@Component
public class MessageListener {

	@Autowired
	private AcaoRepository acaoRepository;

	@Autowired
	AcaoService acaoService;

	@Autowired
	MailService mailService;

	static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

	@RabbitListener(queues = RabbitMQConfig.QUEUE_MESSAGES)
	public void processMessage(Message message) throws ObjectNotFoundException {

		if (message.getSubject().equals("CompraAcao")) {
			logger.info("Message Received");

			String[] dados = message.getBody().split(";");

			Comprador comprador = new Comprador();
			comprador.setId(new Integer(dados[0]));

			Acao acao = acaoService.find(new Integer(dados[1]));
			acao.setComprador(comprador);

			acao = acaoRepository.save(acao);
			mailService.sendMail("Ação comprada com sucesso pelo Comprador: " + acao.getComprador().getNome(),
					"fredericomgbh@gmail.com", "fredericofbh@gmail.com");
		}

		logger.info("Message Received");
		logger.info("Sebject:" + message.getSubject());
		logger.info("Body:" + message.getBody());
	}
}
