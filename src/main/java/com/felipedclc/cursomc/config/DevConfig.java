package com.felipedclc.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.felipedclc.cursomc.services.DBService;
import com.felipedclc.cursomc.services.EmailService;
import com.felipedclc.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig { // CONFIGURAÇÕES ESPECÍFICA DO PROFILE DEV (application.properties)

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		
		
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() { // QUANDO RODAR O application.properties=dev, SERÁ INSTANCIADO O EmailService
		return new SmtpEmailService();
	}
}
