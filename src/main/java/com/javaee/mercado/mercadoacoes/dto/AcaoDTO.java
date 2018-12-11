package com.javaee.mercado.mercadoacoes.dto;

import java.io.Serializable;
import java.util.Date;

import com.javaee.mercado.mercadoacoes.domain.Comprador;
import com.javaee.mercado.mercadoacoes.domain.Empresa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AcaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date dataCompra;

	private Double valorInicial;

	private Double valorAtual;

	private Comprador comprador;

	private Empresa empresa;

	public AcaoDTO(Integer id, Date dataCompra, Double valorInicial, Double valorAtual, Comprador comprador,
			Empresa empresa) {
		super();
		this.id = id;
		this.dataCompra = dataCompra;
		this.valorInicial = valorInicial;
		this.valorAtual = valorAtual;
		this.comprador = comprador;
		this.empresa = empresa;
	}

}
