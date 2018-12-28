package com.javaee.mercado.mercadoacoes.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.javaee.mercado.mercadoacoes.domain.Acao;
import com.javaee.mercado.mercadoacoes.domain.Comprador;
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

	/*@Autowired
	MailService mailService;*/

	public static final String EMAIL_SISTEMA = "fredericofbh@gmail.com";
	public static final String EMAIL = "fredericomgbh@gmail.com";

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

	public Boolean compraAcaoMessage(Integer idAcao, Integer idComprador) throws ObjectNotFoundException {

		Message message = new Message();
		message.setTipoNegociacao("CompraAcao");
		message.setIdComprador(idComprador.toString());
		message.setIdAcao(idAcao.toString());

		return sendMessageToQueue(message);
	}

	public Boolean vendeAcaoMessage(Integer idAcao, Double valor) throws ObjectNotFoundException {

		Message message = new Message();
		message.setTipoNegociacao("VendeAcao");
		message.setIdAcao(idAcao.toString());
		message.setValor(valor.toString());

		return sendMessageToQueue(message);
	}

	public void compraAcao(Message message) throws NumberFormatException, ObjectNotFoundException {

		/*String compradorAntigo = "";*/

		Acao acao = this.find(Integer.valueOf(message.getIdAcao()));
		/*if (acao.getComprador() != null) {
			compradorAntigo = acao.getComprador().getNome();
		}*/
		acao.setComprador(new Comprador(Integer.valueOf(message.getIdComprador())));

		acao = repo.save(acao);
		/*mailService.sendMail("Ação comprada com sucesso. Vendedor: " + compradorAntigo, EMAIL, EMAIL_SISTEMA);

		if (!compradorAntigo.isEmpty()) {
			mailService.sendMail("Ação vendida com sucesso. Comprador: " + acao.getComprador().getNome(), EMAIL,
					EMAIL_SISTEMA);
		}*/

	}

	public void vendeAcao(Message message) throws NumberFormatException, ObjectNotFoundException {

		StringBuilder builder = new StringBuilder();

		Acao acao = this.find(Integer.valueOf(message.getIdAcao()));
		acao.setComprador(null);
		acao.setValorAtual(Double.valueOf(message.getValor()));
		repo.save(acao);

		builder.append(" A venda da sua ação foi registrada em nosso sistema. ");
		builder.append(" Valor inicial: " + acao.getValorInicial());
		builder.append(" Valor vendido: " + acao.getValorAtual());
		builder.append(" Data registrada da venda: " + new Date());
		
		System.out.println(builder.toString());

		/* mailService.sendMail(builder.toString(), EMAIL, EMAIL_SISTEMA); */

	}

	public Boolean sendMessageToQueue(@RequestBody Message message) {
		messageService.sendMessage(message);
		return true;
	}

}
