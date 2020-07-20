package com.felipedclc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.felipedclc.cursomc.domain.Categoria;
import com.felipedclc.cursomc.dto.CategoriaDTO;
import com.felipedclc.cursomc.repositories.CategoriaRepository;
import com.felipedclc.cursomc.resources.exception.DataIntegrityException;
import com.felipedclc.cursomc.services.exceptions.ObjectNotFoundException;

@Service // CLASSE QUE BUSCA OS DADOS DO REPOSITÓRIO E PASSA PARA O CONTROLADOR REST 
public class CategoriaService {

	@Autowired // INJEÇÃO DE DEPENDÊNCIA AUTOMATICA
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		 Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId()); // FIND JA POSSUI UMA EXCEÇÃO CASO NÃO ENCONTRE O ID
		updateData(newObj, obj);
		return repo.save(newObj); // METODO SAVE SERVE PARA ATUALIZAR E INSERIR
	}

	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setName(obj.getName());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}

	public List <Categoria> findAll() {
		return repo.findAll();
	}
	
	// (Page) CONSULTA - LIMITA O NUMERO DE CATEGORIAS (PAGINAS)
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO objDTO) { // CONVERTE A CATEGORIA PARA DTO
		return new Categoria(objDTO.getId(), objDTO.getName());
	}
}
