package com.javaee.mercado.mercadoacoes.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.javaee.mercado.mercadoacoes.domain.Acao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompradorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String nome;
	
	private String email;

	private List<Acao> acoes = new ArrayList<Acao>();

	public CompradorDTO(Integer id, String nome, String email) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
	}

}
