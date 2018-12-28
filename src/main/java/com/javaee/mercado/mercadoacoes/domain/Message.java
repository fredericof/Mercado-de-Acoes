package com.javaee.mercado.mercadoacoes.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idComprador;
	private String idAcao;
	private String tipoNegociacao;
	private String valor;

}
