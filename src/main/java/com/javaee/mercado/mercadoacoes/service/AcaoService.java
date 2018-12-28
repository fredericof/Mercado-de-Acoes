package com.javaee.mercado.mercadoacoes.service;

import java.util.Date;
import java.util.List;
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

	@Autowired
	private CompradorService compradorService;

	@Autowired
	private MailService mailService;

	public List<Acao> getAll() {
		List<Acao> obj = repo.findAll();
		return obj;
	}

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

	public String compraAcaoMessage(Integer idAcao, Integer idComprador) throws ObjectNotFoundException {

		Message message = new Message();
		message.setTipoNegociacao("CompraAcao");
		message.setIdComprador(idComprador.toString());
		message.setIdAcao(idAcao.toString());

		sendMessageToQueue(message);

		return "Pedido de compra da ação foi registrado!";
	}

	public String vendeAcaoMessage(Integer idAcao, Double valor) throws ObjectNotFoundException {

		Message message = new Message();
		message.setTipoNegociacao("VendeAcao");
		message.setIdAcao(idAcao.toString());
		message.setValor(valor.toString());

		sendMessageToQueue(message);

		return "Pedido de venda da ação foi registrado!";
	}

	public void compraAcao(Message message) throws NumberFormatException, ObjectNotFoundException {

		Comprador comprador = compradorService.find(Integer.valueOf(message.getIdComprador()));
		Acao acao = this.find(Integer.valueOf(message.getIdAcao()));

		// Envia email para o vendedor
		if (acao.getComprador() != null) {
			StringBuilder builder = new StringBuilder();
			builder.append(acao.getComprador().getNome() + ", sua ação foi comprada!");
			builder.append(" Identificador da ação: " + acao.getId() + ";");
			builder.append(" Valor da venda: R$ " + acao.getValorAtual() + ";");
			builder.append(" Nome do comprador: " + comprador.getNome());

			mailService.sendMail(builder.toString(), "Pedido de compra da ação registrado!",
					acao.getComprador().getEmail());
			System.out.println(builder.toString());
		}

		// Envia email para o comprador
		StringBuilder builder = new StringBuilder();
		builder.append(comprador.getNome() + ", registramos o seu pedido de compra da ação!");
		if (acao.getComprador() != null) {
			builder.append(" Nome do vendedor: " + acao.getComprador().getNome() + ";");
		}
		builder.append(" Identificador da ação: " + acao.getId() + ";");
		builder.append(" Valor da compra: R$ " + acao.getValorAtual());

		acao.setComprador(new Comprador(Integer.valueOf(message.getIdComprador())));
		acao = repo.save(acao);

		mailService.sendMail(builder.toString(), "Pedido de compra da ação registrado!", acao.getComprador().getEmail());
		System.out.println(builder.toString());

	}

	public void vendeAcao(Message message) throws NumberFormatException, ObjectNotFoundException {

		StringBuilder builder = new StringBuilder();

		Acao acao = this.find(Integer.valueOf(message.getIdAcao()));
		String emailCompradorAntigo = acao.getComprador().getEmail();
		
		acao.setComprador(null);
		acao.setValorAtual(Double.valueOf(message.getValor()));
		repo.save(acao);

		builder.append(" A venda da sua ação foi registrada em nosso sistema.");
		builder.append(" Valor inicial: R$ " + acao.getValorInicial() + ";");
		builder.append(" Valor vendido: R$ " + acao.getValorAtual() + ";");
		builder.append(" Data registrada da venda: " + new Date());
		
		mailService.sendMail(builder.toString(), "Pedido de venda da ação registrado!", emailCompradorAntigo);
		System.out.println(builder.toString());

	}

	public void sendMessageToQueue(@RequestBody Message message) {
		messageService.sendMessage(message);
	}

}
