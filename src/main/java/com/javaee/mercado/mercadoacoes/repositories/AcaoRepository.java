package com.javaee.mercado.mercadoacoes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaee.mercado.mercadoacoes.domain.Acao;

@Repository
public interface AcaoRepository extends JpaRepository<Acao, Integer> {

}
