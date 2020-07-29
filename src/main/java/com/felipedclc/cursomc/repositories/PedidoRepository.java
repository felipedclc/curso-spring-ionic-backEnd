package com.felipedclc.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.felipedclc.cursomc.domain.Cliente;
import com.felipedclc.cursomc.domain.Pedido;

@Repository // CLASSE REPOSITÓRIO (ACESSA O BANCO DE DADOS). TIPO, ID
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	@Transactional(readOnly=true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest); // BUSCANDO PEDIDOS POR CLIENTE(AUTOMATICO)
}
