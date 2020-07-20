package com.felipedclc.cursomc.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.felipedclc.cursomc.domain.Cliente;

@Repository // CLASSE REPOSITÓRIO (ACESSA O BANCO DE DADOS).    TIPO,  ID
public interface ClienteRepository extends JpaRepository<Cliente, Integer> { 

	@Transactional // AUMENTA A VELOCIDADE DA TRANSAÇÃO DE DADOS
	Cliente findByEmail (String email); // FindBy(Email) - SPRING DETECTA E FAZ A BUSCA (STRING)
}
