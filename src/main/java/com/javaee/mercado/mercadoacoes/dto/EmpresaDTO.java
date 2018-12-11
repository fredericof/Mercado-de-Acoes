package com.javaee.mercado.mercadoacoes.dto;

import java.io.Serializable;

import com.javaee.mercado.mercadoacoes.domain.Empresa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpresaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String nome;

	private Integer qtdAcoes;

	public EmpresaDTO(Empresa obj) {
		id = obj.getId();
		nome = obj.getNome();
		qtdAcoes = obj.getQtdAcoes();
	}

}
