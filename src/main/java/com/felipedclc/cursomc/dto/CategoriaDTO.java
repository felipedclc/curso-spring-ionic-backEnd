package com.felipedclc.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.felipedclc.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable { // CATEGORIA QUE FILTRA OS ITENS QUE DEVEM SER EXIBIDOS 
	private static final long serialVersionUID = 1L; 

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório") // REQUISITOS PARA A VALIDAÇÃO DE DADOS
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
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
