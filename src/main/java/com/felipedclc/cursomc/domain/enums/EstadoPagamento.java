package com.felipedclc.cursomc.domain.enums;

public enum EstadoPagamento {

	APROVADO(1, "Pagamento aprovado"), // COLOCAÇÃO MANUAL DE CODIGOS (BOA PRÁTICA)
	REPROVADO(2, "Pagamento reprovado"),
	PENDENTE(3, "Pagamento pendente");
	
	private int cod; // CONSTRUTOR PARA GUARDAR OS CODIGOS 
	private String descricao;
	
	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EstadoPagamento toEnum(Integer cod) { // BUSCANDO O CODIGO DO CLIENTE
		
		if(cod == null) {
			return null;
		}
		for(EstadoPagamento x : EstadoPagamento.values()) { // PERCORRE TODOS OS VALORES DO ENUM
			if(cod.equals(x.getCod())) { // SE X = COD BUSCADO
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
