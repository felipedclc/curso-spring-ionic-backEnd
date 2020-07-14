package com.felipedclc.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felipedclc.cursomc.domain.Cidade;

@Repository // CLASSE REPOSITÓRIO (ACESSA O BANCO DE DADOS). TIPO, ID
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
