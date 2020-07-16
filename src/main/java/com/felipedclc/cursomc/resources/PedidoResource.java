package com.felipedclc.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.felipedclc.cursomc.domain.Pedido;
import com.felipedclc.cursomc.services.PedidoService;

@RestController // CONTROLADOR REST (ACESSA A CAMADA DE SERVIÇOS)
@RequestMapping(value= "/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;

	@RequestMapping(value= "/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) { // ESSE id RECEBE O "/{id}"
		  // Response Entity: ARMAZENA RESPOSTAS HTTP PARA UM SERVIÇO REST (PROTOCOLO HTTP) 
		Pedido obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
}
