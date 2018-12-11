package com.javaee.mercado.mercadoacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.mercado.mercadoacoes.domain.Acao;
import com.javaee.mercado.mercadoacoes.repositories.AcaoRepository;

@Service
public class AcaoService {
	
	@Autowired
	private AcaoRepository repo;
	
	public Acao insert(Acao obj) {
		obj.setId(null);
		return repo.save(obj);
	}

}
