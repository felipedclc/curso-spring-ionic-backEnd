package com.felipedclc.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipedclc.cursomc.domain.Cliente;
import com.felipedclc.cursomc.repositories.ClienteRepository;
import com.felipedclc.cursomc.services.exceptions.ObjectNotFoundException;

@Service // CLASSE QUE BUSCA OS DADOS DO REPOSITÓRIO E PASSA PARA O CONTROLADOR REST 
public class ClienteService {

	@Autowired // INJEÇÃO DE DEPENDÊNCIA AUTOMATICA
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		 Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		} 
}
