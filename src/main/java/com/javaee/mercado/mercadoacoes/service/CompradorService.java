package com.javaee.mercado.mercadoacoes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.javaee.mercado.mercadoacoes.domain.Comprador;
import com.javaee.mercado.mercadoacoes.dto.CompradorDTO;
import com.javaee.mercado.mercadoacoes.repositories.CompradorRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class CompradorService {

	@Autowired
	private CompradorRepository repo;

	public Comprador find(Integer id) throws ObjectNotFoundException {
		Optional<Comprador> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Comprador.class.getName()));
	}

	public Comprador insert(Comprador obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Comprador compraAcao(Integer id, CompradorDTO obj) throws ObjectNotFoundException {
		Comprador comprador = find(id);
		comprador.setAcoes(obj.getAcoes());
		return repo.save(comprador);
	}

	public Comprador fromDTO(CompradorDTO objDTO) {
		return new Comprador(objDTO.getId(), objDTO.getNome());
	}

}
