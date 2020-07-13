package com.felipedclc.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.felipedclc.cursomc.domain.Categoria;
import com.felipedclc.cursomc.repositories.CategoriaRepository;

@SpringBootApplication
public class Cursomc1Application implements CommandLineRunner { // PERMITE IMPLEMENTAR UM METODO AUXILIAR QUANDO A APLICAÇÃO INICIAR 

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(Cursomc1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
	}

}
