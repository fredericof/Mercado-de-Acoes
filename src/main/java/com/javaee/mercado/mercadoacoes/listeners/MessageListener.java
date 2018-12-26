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

	public static final String EMAIL_SISTEMA = "fredericofbh@gmail.com";
	public static final String EMAIL = "fredericomgbh@gmail.com";

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
			compraAcao(message);
			logger.info("Ação comprada");
		} else if (message.getSubject().equals("VendeAcao")) {
			logger.info("Message Received");
			vendeAcao(message);
			logger.info("Ação Vendida");
		}

		logger.info("Message Received");
		logger.info("Sebject:" + message.getSubject());
		logger.info("Body:" + message.getBody());
	}

	public void compraAcao(Message message) throws NumberFormatException, ObjectNotFoundException {

		String compradorAntigo = "";
		String[] dados = message.getBody().split(";");

		Comprador comprador = new Comprador();
		comprador.setId(new Integer(dados[0]));

		Acao acao = acaoService.find(Integer.valueOf(dados[1]));
		if (acao.getComprador() != null) {
			compradorAntigo = acao.getComprador().getNome();
		}
		acao.setComprador(comprador);

		acao = acaoRepository.save(acao);
		mailService.sendMail("Ação comprada com sucesso. Vendedor: " + compradorAntigo, EMAIL, EMAIL_SISTEMA);

		if (!compradorAntigo.isEmpty()) {
			mailService.sendMail("Ação vendida com sucesso. Comprador: " + acao.getComprador().getNome(), EMAIL,
					EMAIL_SISTEMA);
		}

	}

	public void vendeAcao(Message message) throws NumberFormatException, ObjectNotFoundException {

		String[] dados = message.getBody().split(";");

		Acao acao = acaoService.find(Integer.valueOf(dados[0]));
		acao.setComprador(null);
		acaoRepository.save(acao);
		mailService.sendMail("Ação vendida com sucesso ", EMAIL, EMAIL_SISTEMA);

	}

}
