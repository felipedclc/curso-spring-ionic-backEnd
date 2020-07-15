package com.felipedclc.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable // ANOTAÇÃO PARA CLASSE SUBTIPO
public class ItemPedidoPK implements Serializable { // CHAVE PRIMARIA DA CLASSE COMPOSTA - ItemPedido
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "pedido_id") 
	private Pedido pedido;
	                             // CLASSE VE O PRODUTO E O PEDIDO 
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
	
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
}
