package com.javaee.mercado.mercadoacoes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.mercado.mercadoacoes.domain.Empresa;
import com.javaee.mercado.mercadoacoes.dto.EmpresaDTO;
import com.javaee.mercado.mercadoacoes.repositories.EmpresaRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository repo;
	
	public List<Empresa> getAll() {
		List<Empresa> obj = repo.findAll();
		return obj;
	}

	public Empresa find(Integer id) throws ObjectNotFoundException {
		Optional<Empresa> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Empresa.class.getName()));
	}

	public Empresa insert(Empresa obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Empresa fromDTO(EmpresaDTO objDTO) {
		return new Empresa(objDTO.getId(), objDTO.getNome(), objDTO.getNumMaxAcoes());
	}

}
