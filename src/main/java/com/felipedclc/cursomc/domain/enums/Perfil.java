package com.felipedclc.cursomc.domain.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"), // ROLE_ (EXIGENCIA DO FRAMEWORK
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int cod; // CONSTRUTOR PARA GUARDAR OS CODIGOS 
	private String descricao;
	
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Perfil toEnum(Integer cod) { // BUSCANDO O CODIGO DO CLIENTE
		
		if(cod == null) {
			return null;
		}
		for(Perfil x : Perfil.values()) { // PERCORRE TODOS OS VALORES DO ENUM
			if(cod.equals(x.getCod())) { // SE X = COD BUSCADO
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
