package com.felipedclc.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.felipedclc.cursomc.domain.Cliente;

@Repository // CLASSE REPOSITÓRIO (ACESSA O BANCO DE DADOS).    TIPO,  ID
public interface ClienteRepository extends JpaRepository<Cliente, Integer> { 

	@Transactional(readOnly=true) // APENAS PARA LEITURA - CARREGA MENOS O SISTEMA
	Cliente findByEmail (String email); // FindBy(Email) - SPRING DETECTA (formato) E FAZ A BUSCA (STRING)
}
