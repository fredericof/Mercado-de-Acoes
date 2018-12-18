package com.javaee.mercado.mercadoacoes.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.mercado.mercadoacoes.domain.Acao;
import com.javaee.mercado.mercadoacoes.domain.Empresa;
import com.javaee.mercado.mercadoacoes.dto.AcaoDTO;
import com.javaee.mercado.mercadoacoes.repositories.AcaoRepository;
import com.javaee.mercado.mercadoacoes.repositories.EmpresaRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class AcaoService {

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

	public Acao compraAcao(Integer id, AcaoDTO obj) throws ObjectNotFoundException {
		Acao acao = find(id);
		acao.setComprador(obj.getComprador());
		return repo.save(acao);
	}

}
