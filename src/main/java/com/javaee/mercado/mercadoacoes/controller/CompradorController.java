package com.javaee.mercado.mercadoacoes.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javaee.mercado.mercadoacoes.domain.Comprador;
import com.javaee.mercado.mercadoacoes.dto.CompradorDTO;
import com.javaee.mercado.mercadoacoes.service.CompradorService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(CompradorController.BASE_URL)
public class CompradorController {

	public static final String BASE_URL = "/api/v1/compradores";

	@Autowired
	private CompradorService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Comprador> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Comprador obj = service.find(id);

		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody CompradorDTO objDTO) {
		Comprador obj = service.fromDTO(objDTO);

		obj = service.insert(obj);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public Comprador compraAcao(@PathVariable Integer id, @RequestBody CompradorDTO objDTO) throws ObjectNotFoundException {
		return service.compraAcao(id, objDTO);
	}

}
