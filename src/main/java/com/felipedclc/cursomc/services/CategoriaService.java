package com.felipedclc.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipedclc.cursomc.domain.Categoria;
import com.felipedclc.cursomc.repositories.CategoriaRepository;
import com.felipedclc.cursomc.services.exceptions.ObjectNotFoundException;

@Service // CLASSE QUE BUSCA OS DADOS DO REPOSITÓRIO E PASSA PARA O CONTROLADOR REST 
public class CategoriaService {

	@Autowired // INJEÇÃO DE DEPENDÊNCIA AUTOMATICA
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		 Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		} 
}