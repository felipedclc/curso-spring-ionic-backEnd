package com.felipedclc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.felipedclc.cursomc.domain.Cliente;
import com.felipedclc.cursomc.dto.ClienteDTO;
import com.felipedclc.cursomc.repositories.ClienteRepository;
import com.felipedclc.cursomc.resources.exception.DataIntegrityException;
import com.felipedclc.cursomc.services.exceptions.ObjectNotFoundException;

@Service // CLASSE QUE BUSCA OS DADOS DO REPOSITÓRIO E PASSA PARA O CONTROLADOR REST 
public class ClienteService {

	@Autowired // INJEÇÃO DE DEPENDÊNCIA AUTOMATICA
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		 Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
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
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
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
		return new Cliente(objDTO.getId(), objDTO.getName(), objDTO.getEmail(), null, null); 
	}
}
