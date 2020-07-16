package com.felipedclc.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipedclc.cursomc.domain.Pedido;
import com.felipedclc.cursomc.repositories.PedidoRepository;
import com.felipedclc.cursomc.services.exceptions.ObjectNotFoundException;

@Service // CLASSE QUE BUSCA OS DADOS DO REPOSITÓRIO E PASSA PARA O CONTROLADOR REST 
public class PedidoService {

	@Autowired // INJEÇÃO DE DEPENDÊNCIA AUTOMATICA
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		 Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		} 
}
