package com.javaee.mercado.mercadoacoes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaee.mercado.mercadoacoes.domain.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

}
