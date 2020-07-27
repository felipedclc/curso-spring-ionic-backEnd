package com.felipedclc.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.felipedclc.cursomc.services.DBService;
import com.felipedclc.cursomc.services.EmailService;
import com.felipedclc.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig { // CONFIGURAÇÕES ESPECÍFICA DO PROFILE TEST (application.properties)

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() { // INSTANCIA AUTOMATICA DO MockEmailService A PARTIR DA INTERFACE EmailService 
		return new MockEmailService();
	}
}
