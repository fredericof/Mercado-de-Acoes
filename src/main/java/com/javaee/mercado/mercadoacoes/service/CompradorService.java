package com.javaee.mercado.mercadoacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.mercado.mercadoacoes.domain.Comprador;
import com.javaee.mercado.mercadoacoes.repositories.CompradorRepository;

@Service
public class CompradorService {
	
	@Autowired
	private CompradorRepository repo;
	
	public Comprador insert(Comprador obj) {
		obj.setId(null);
		return repo.save(obj);
	}

}
