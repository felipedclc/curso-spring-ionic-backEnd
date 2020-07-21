package com.felipedclc.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.felipedclc.cursomc.domain.Categoria;
import com.felipedclc.cursomc.domain.Produto;

@Repository // CLASSE REPOSITÓRIO (ACESSA O BANCO DE DADOS). TIPO, ID
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	// COMANDO JPQL REFERENTE A CONSULTA (BUSCA UM NOME(PRODUTO) OU PARTE(LIKE) DE UM NOME NA LISTA DE CATEGORIAS)
	//@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.name LIKE %:name% AND cat IN :categorias")
	//Page<Produto> search(@Param("name") String name, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	Page<Produto> findDistinctByNameContainingAndCategoriasIn(String name, List<Categoria> categorias, Pageable pageRequest);
	// UTILIZANDO CONSULTAS AUTOMATICAS POR NOME NO REPOSITÓRIO DO SPRING (PADRÃO DE NOMES)
}
