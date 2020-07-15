package com.felipedclc.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felipedclc.cursomc.domain.Pagamento;

@Repository // CLASSE REPOSITÓRIO (ACESSA O BANCO DE DADOS). TIPO, ID
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
