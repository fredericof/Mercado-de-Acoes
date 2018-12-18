package com.javaee.mercado.mercadoacoes.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Acao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	private Date dataCompra;

	private Double valorInicial;

	private Double valorAtual;

	@JsonIgnoreProperties("acoes")
	@ManyToOne
	@JoinColumn(name = "comprador_id")
	private Comprador comprador;

	@JsonIgnoreProperties("acoes")
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;

	public Acao(Integer id, Date dataCompra, Double valorInicial, Double valorAtual, Comprador comprador,
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
