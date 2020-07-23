package com.felipedclc.cursomc;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.felipedclc.cursomc.services.DBService;

@SpringBootApplication
public class Cursomc1Application implements CommandLineRunner {  
	
	public static void main(String[] args) {
		SpringApplication.run(Cursomc1Application.class, args);
	}
	
	@Autowired
	private DBService dbService;
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() throws ParseException {

		if (!"create".equals(strategy)) {
			return false;
		}

		dbService.instantiateTestDatabase();
		return true;
	}
	@Override
	public void run(String... args) throws Exception {
	}
}
