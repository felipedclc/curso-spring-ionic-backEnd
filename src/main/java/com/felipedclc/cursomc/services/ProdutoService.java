package com.felipedclc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.felipedclc.cursomc.domain.Categoria;
import com.felipedclc.cursomc.domain.Produto;
import com.felipedclc.cursomc.repositories.CategoriaRepository;
import com.felipedclc.cursomc.repositories.ProdutoRepository;
import com.felipedclc.cursomc.services.exceptions.ObjectNotFoundException;

@Service // CLASSE QUE BUSCA OS DADOS DO REPOSITÓRIO E PASSA PARA O CONTROLADOR REST
public class ProdutoService {

	@Autowired 
	private ProdutoRepository repo;
	
	@Autowired 
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	// BUSCA PAGINADA POR NOME E PELA LISTA DE ID
	public Page<Produto> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy); 
		List<Categoria> categorias = categoriaRepository.findAllById(ids); //BUSCANDO AS CATEGORIAS NO BANCO DE DADOS A PARTIR DOS ids
		return repo.findDistinctByNameContainingAndCategoriasIn(name, categorias, pageRequest);
	}
}
