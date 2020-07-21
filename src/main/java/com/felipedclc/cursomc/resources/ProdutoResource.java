package com.felipedclc.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.felipedclc.cursomc.domain.Produto;
import com.felipedclc.cursomc.dto.ProdutoDTO;
import com.felipedclc.cursomc.resources.utils.URL;
import com.felipedclc.cursomc.services.ProdutoService;

@RestController // CONTROLADOR REST (ACESSA A CAMADA DE SERVIÇOS)
@RequestMapping(value= "/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;

	@RequestMapping(value= "/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) { // ESSE id RECEBE O "/{id}"
		  // Response Entity: ARMAZENA RESPOSTAS HTTP PARA UM SERVIÇO REST (PROTOCOLO HTTP) 
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.GET) // END POINT DE BUSCA POR PAGINAS
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "name", defaultValue = "0") String name,
			@RequestParam(value = "categorias", defaultValue = "0") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page, // RequestParam (PARAMETRO OPCIONAL)
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, // 24 É MULTIPLO DE 2, 3 E 4
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy, // ORDENADO POR NOME
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) { // ASCENDENTE
		String nameDecoded = URL.decodeParam(name); // NOME DECODIFICADO(PRODUTO) - ESPAÇOS VAZIOS NA BUSCA
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nameDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj)); // PAGE NÃO NESSECITA A COVERSAO (STREAM/COLLECT)
		return ResponseEntity.ok().body(listDTO);
	}
}
