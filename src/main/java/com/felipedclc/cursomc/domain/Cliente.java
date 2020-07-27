package com.felipedclc.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.felipedclc.cursomc.domain.enums.Perfil;
import com.felipedclc.cursomc.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L; 

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@Column(unique = true) // NÃO PODERÁ HAVER REPETIÇAO (EMAIL)
	private String email;
	private String cpfOuCnpj;
	private Integer tipo; // TIPO CLIENTE
	
	private String senha;
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL) // COMPORTAMENTO EM CASCATA CASO DE (PUT, DELETE..)
	private List<Endereco> enderecos = new ArrayList<>();
	
	@ElementCollection // TABELA AUXILIAR 
	@CollectionTable(name = "TELEFONE")
	private Set<String> telefones = new HashSet<>(); // CONJUNTO DE TELEFONES (SET - NÃO ACEITA REPETIÇÃO)
	
	@ElementCollection(fetch=FetchType.EAGER) // GARANTE QUE O CLIENTE DO BANCO DE DADOS VENHA COM OS PERFIS
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>(); // CONJUNTO DE TELEFONES (SET - NÃO ACEITA REPETIÇÃO)
	
	@JsonIgnore // NÃO ACESSA OS PEDIDOS SERIALIZADOS (EVITA REPETIÇÃO CICLICA)
	@OneToMany(mappedBy = "cliente") 	
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Cliente() { // ADICIONANDO O PERFIL AO CLIENTE NA INSTANCIAÇÃO 
		addPerfil(Perfil.CLIENTE);
	}

	public Cliente(Integer id, String name, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = (tipo == null) ? null : tipo.getCod(); // SE O TIPO = NULL ATRIBUIR(?) NULL, CASO CONTRARIO(:) ATRIBUIR getCod
		this.senha = senha;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo); // PEGA O COGIDO BASEADO COM O INTEIRO DESTA CLASSE 
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod(); // ARMAZENA O CODIGO 
	}
	
	public String getSenha() {
		return senha;
	}

	@JsonIgnore
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Perfil> getPerfis(){ // RETORNA OS PERFIS DO CLIENTE
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet()); 
	}

	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}
	
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
