package com.javaee.mercado.mercadoacoes.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

	@GetMapping
	public ResponseEntity<List<Acao>> getAll() {
		return ResponseEntity.ok().body(service.getAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Acao> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Acao obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@Transactional
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

	@Transactional
	@PutMapping({ "/{idAcao}/comprador/{idComprador}/comprar" })
	@ResponseStatus(HttpStatus.OK)
	public String compraAcao(@PathVariable Integer idAcao, @PathVariable Integer idComprador)
			throws ObjectNotFoundException {
		return service.compraAcaoMessage(idAcao, idComprador);
	}

	@Transactional
	@PutMapping({ "/{idAcao}/valor/{valor}/vender" })
	@ResponseStatus(HttpStatus.OK)
	public String vendeAcao(@PathVariable Integer idAcao, @PathVariable Double valor) throws ObjectNotFoundException {
		return service.vendeAcaoMessage(idAcao, valor);
	}

}
