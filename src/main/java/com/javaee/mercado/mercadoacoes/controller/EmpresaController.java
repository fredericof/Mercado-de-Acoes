package com.javaee.mercado.mercadoacoes.controller;

import java.net.URI;

import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javaee.mercado.mercadoacoes.domain.Empresa;
import com.javaee.mercado.mercadoacoes.dto.EmpresaDTO;
import com.javaee.mercado.mercadoacoes.service.EmpresaService;

@RestController
@RequestMapping(EmpresaController.BASE_URL)
public class EmpresaController {

	public static final String BASE_URL = "/api/v1/empresas";

	@Autowired
	private EmpresaService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Empresa> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Empresa obj = service.find(id);

		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody EmpresaDTO objDTO) {
		Empresa obj = service.fromDTO(objDTO);
		obj = service.insert(obj);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

}
