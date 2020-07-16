package com.felipedclc.cursomc.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // GERANDO O ID DO PEDIDO (PAGAMENTO)
	private Integer id;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "endereco_de_entrega_id") // CHAVE ESTRANGEIRA
	private Endereco enderecoEntrega;
	 
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido") // SALVA O PEDIDO E O PAGAMENTO (JPA) 
	private Pagamento pagamento;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id") // CHAVE ESTRANGEIRA
	private Cliente cliente;

	@OneToMany(mappedBy = "id.pedido") // id (ItemPedidoPK) QUE POSSUI A REFERÊNCIA PARA O PEDIDO 
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Pedido() {
		
	}

	public Pedido(Integer id, Date date, Endereco enderecoEntrega, Cliente cliente) {
		super();
		this.id = id;
		this.date = date;
		this.enderecoEntrega = enderecoEntrega;
		this.cliente = cliente;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
