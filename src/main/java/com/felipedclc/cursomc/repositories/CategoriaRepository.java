package com.felipedclc.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felipedclc.cursomc.domain.Categoria;

@Repository // CLASSE REPOSITÓRIO (ACESSA O BANCO DE DADOS).    TIPO,  ID
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> { 

}
