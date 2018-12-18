package com.javaee.mercado.mercadoacoes.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	private String nome;
	
	private Integer numMaxAcoes;
		
	@OneToMany(mappedBy = "empresa")
	private List<Acao> acoes = new ArrayList<Acao>();

	public Empresa(Integer id, String nome, Integer numMaxAcoes) {
		super();
		this.id = id;
		this.nome = nome;
		this.numMaxAcoes = numMaxAcoes;
	}
	
}
