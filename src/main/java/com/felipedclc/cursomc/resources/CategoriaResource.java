package com.felipedclc.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.felipedclc.cursomc.domain.Categoria;
import com.felipedclc.cursomc.dto.CategoriaDTO;
import com.felipedclc.cursomc.services.CategoriaService;

@RestController // CONTROLADOR REST (ACESSA A CAMADA DE SERVIÇOS)
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { // ESSE id RECEBE O "/{id}"
		// Response Entity: ARMAZENA RESPOSTAS HTTP PARA UM SERVIÇO REST (PROTOCOLO
		// HTTP)
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) { // @RequestBody CONVERTE O JSON PARA O OBJETO JAVA
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build(); // CRIANDO O CODIGO URI 201
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT) // METODO BUSCA O ID E EDITA
	public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody Categoria obj) {
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) { // PATH VARIABLE ASSOCIA O ID(STRING) COM O ID(URL)
		service.delete(id);
		return ResponseEntity.noContent().build(); // RESPOSTA 204
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).
				collect(Collectors.toList()); // CONVERTENDO UMA LISTA(list) PARA OUTRA LISTA(listDTO) 
		return ResponseEntity.ok().body(listDTO);
	}
}
