package com.javaee.mercado.mercadoacoes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaee.mercado.mercadoacoes.domain.Comprador;

public interface CompradorRepository extends JpaRepository<Comprador, Integer> {

}
