package com.javaee.mercado.mercadoacoes.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.javaee.mercado.mercadoacoes.domain.Acao;
import com.javaee.mercado.mercadoacoes.domain.Empresa;
import com.javaee.mercado.mercadoacoes.domain.Message;
import com.javaee.mercado.mercadoacoes.dto.AcaoDTO;
import com.javaee.mercado.mercadoacoes.repositories.AcaoRepository;
import com.javaee.mercado.mercadoacoes.repositories.EmpresaRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class AcaoService {

	@Autowired
	private MessageService messageService;

	@Autowired
	private AcaoRepository repo;

	@Autowired
	private EmpresaRepository empresaRepository;

	public Acao find(Integer id) throws ObjectNotFoundException {
		Optional<Acao> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Acao.class.getName()));
	}

	public Acao insert(Acao obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Acao fromDTO(AcaoDTO objDTO) {
		return new Acao(objDTO.getId(), new Date(), objDTO.getValorInicial(), objDTO.getValorAtual(),
				objDTO.getComprador(), objDTO.getEmpresa());
	}

	public Acao inserirAcaoEmpresa(Acao obj) {
		Empresa empresa = empresaRepository.findById(obj.getEmpresa().getId()).get();

		if (empresa.getAcoes().size() >= empresa.getNumMaxAcoes()) {
			return null;
		}

		return insert(obj);
	}

	public Boolean compraAcao(Integer id, AcaoDTO obj) throws ObjectNotFoundException {

		Message message = new Message();
		message.setSubject("CompraAcao");
		message.setBody(obj.getComprador().getId() + ";" + id);

		return sendMessageToQueue(message);
	}

	public Boolean vendeAcao(Integer id) throws ObjectNotFoundException {

		Message message = new Message();
		message.setSubject("VendeAcao");
		message.setBody(id.toString());

		return sendMessageToQueue(message);
	}

	public Boolean sendMessageToQueue(@RequestBody Message message) {
		messageService.sendMessage(message);
		return true;
	}

}
