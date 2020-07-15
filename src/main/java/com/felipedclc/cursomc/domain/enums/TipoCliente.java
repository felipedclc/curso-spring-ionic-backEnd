package com.felipedclc.cursomc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa física"), // COLOCAÇÃO MANUAL DE CODIGOS (BOA PRÁTICA)
	PESSOAJURIDICA(2, "Pessoa jurídica");
	
	private int cod; // CONSTRUTOR PARA GUARDAR OS CODIGOS 
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoCliente toEnum(Integer cod) { // BUSCANDO O CODIGO DO CLIENTE
		
		if(cod == null) {
			return null;
		}
		for(TipoCliente x : TipoCliente.values()) { // PERCORRE TODOS OS VALORES DO ENUM
			if(cod.equals(x.getCod())) { // SE X = COD BUSCADO
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
