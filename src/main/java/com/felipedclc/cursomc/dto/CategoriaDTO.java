package com.felipedclc.cursomc.dto;

import java.io.Serializable;

import com.felipedclc.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable { // CATEGORIA QUE FILTRA OS ITENS QUE DEVEM SER EXIBIDOS 
	private static final long serialVersionUID = 1L; 

	private Integer id;
	private String name;
	
	public CategoriaDTO() {
		
	}
	
	public CategoriaDTO (Categoria obj) { // CONSTRUTOR INSTANCIA O DTO ATRAVEZ DE UM OBJETO CATEGORIA 
		id = obj.getId();
		name = obj.getName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
