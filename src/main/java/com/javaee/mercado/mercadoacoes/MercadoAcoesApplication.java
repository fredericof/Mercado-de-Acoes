package com.javaee.mercado.mercadoacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.javaee.mercado.mercadoacoes.repositories.AcaoRepository;
import com.javaee.mercado.mercadoacoes.repositories.CompradorRepository;
import com.javaee.mercado.mercadoacoes.repositories.EmpresaRepository;

@SpringBootApplication
public class MercadoAcoesApplication implements CommandLineRunner {

	@Autowired
	private AcaoRepository acaoRepository;

	@Autowired
	private CompradorRepository compradorRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	public static void main(String[] args) {
		SpringApplication.run(MercadoAcoesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/* Carga nas tabelas para teste */

		/*
		 * // Empresa Empresa emp1 = new Empresa(null, "Coca Cola", 300);
		 * empresaRepository.saveAll(Arrays.asList(emp1));
		 * 
		 * // Comprador Comprador comp1 = new Comprador(null, "João");
		 * compradorRepository.saveAll(Arrays.asList(comp1));
		 * 
		 * // Acao Acao acao1 = new Acao(null, new Date(), 100.00, 500.00, comp1, emp1);
		 * acaoRepository.saveAll(Arrays.asList(acao1));
		 */

	}
}
