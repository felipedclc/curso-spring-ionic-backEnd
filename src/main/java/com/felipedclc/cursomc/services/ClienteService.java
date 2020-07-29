package com.felipedclc.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.felipedclc.cursomc.domain.Cidade;
import com.felipedclc.cursomc.domain.Cliente;
import com.felipedclc.cursomc.domain.Endereco;
import com.felipedclc.cursomc.domain.enums.Perfil;
import com.felipedclc.cursomc.domain.enums.TipoCliente;
import com.felipedclc.cursomc.dto.ClienteDTO;
import com.felipedclc.cursomc.dto.ClienteNewDTO;
import com.felipedclc.cursomc.repositories.ClienteRepository;
import com.felipedclc.cursomc.repositories.EnderecoRepository;
import com.felipedclc.cursomc.resources.exception.DataIntegrityException;
import com.felipedclc.cursomc.security.UserSS;
import com.felipedclc.cursomc.services.exceptions.AuthorizationException;
import com.felipedclc.cursomc.services.exceptions.ObjectNotFoundException;

@Service // CLASSE QUE BUSCA OS DADOS DO REPOSITÓRIO E PASSA PARA O CONTROLADOR REST 
public class ClienteService {

	@Autowired 
	private ClienteRepository repo;
	
	@Autowired 
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if(user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) { // hasRole TESTA SE O PERFIL É ADMIN
			throw new AuthorizationException("Acesso negado");
		}
		
		 Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj =  repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj); // METODO SAVE SERVE PARA ATUALIZAR E INSERIR
	}

	private void updateData(Cliente newObj, Cliente obj) { // ATUALIZANDO OS DADOS DO NOVO OBJETO COM BASE NO OBJ
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
		}
	}

	public List <Cliente> findAll() {
		return repo.findAll();
	}
	
	// (Page) CONSULTA - LIMITA O NUMERO DE CATEGORIAS (PAGINAS)
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDTO) { // CONVERTE A CATEGORIA PARA DTO
		return new Cliente(objDTO.getId(), objDTO.getName(), objDTO.getEmail(), null, null, null); 
	}

	public Cliente fromDTO(@Valid ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getName(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo())
				,pe.encode(objDTO.getSenha()));
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end); // CLIENTE OBTENDO OS ENDEREÇOS RELACIONADOS 
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}
}
