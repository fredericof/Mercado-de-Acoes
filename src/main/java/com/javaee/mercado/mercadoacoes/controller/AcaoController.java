package com.javaee.mercado.mercadoacoes.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javaee.mercado.mercadoacoes.domain.Acao;
import com.javaee.mercado.mercadoacoes.dto.AcaoDTO;
import com.javaee.mercado.mercadoacoes.service.AcaoService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(AcaoController.BASE_URL)
public class AcaoController {

	public static final String BASE_URL = "/api/v1/acoes";
	
	@Autowired
	private AcaoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Acao> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Acao obj = service.find(id);

		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody AcaoDTO objDTO) {
		Acao obj = service.fromDTO(objDTO);
		obj = service.inserirAcaoEmpresa(obj);
		
		if (obj == null) {
			return ResponseEntity.status(HttpStatus.OK).body("Não é permitido emitir mais ações");
		}

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
	
}