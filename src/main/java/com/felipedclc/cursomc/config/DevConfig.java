package com.felipedclc.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.felipedclc.cursomc.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig { // CONFIGURAÇÕES ESPECÍFICA DO PROFILE DEV (application.properties)

	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}") // PEGANDO O VALOR DA CHAVE
	private String strategy; // ESTRATEGIA DE GERAÇÃO DO BD
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if("create" != strategy) { 
			return false; 
		}
		
		dbService.instantiateTestDatabase();
		return true;
	}
}
