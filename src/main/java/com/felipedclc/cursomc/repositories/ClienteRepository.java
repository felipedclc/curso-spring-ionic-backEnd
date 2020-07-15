package com.felipedclc.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felipedclc.cursomc.domain.Cliente;

@Repository // CLASSE REPOSITÓRIO (ACESSA O BANCO DE DADOS).    TIPO,  ID
public interface ClienteRepository extends JpaRepository<Cliente, Integer> { 

}
