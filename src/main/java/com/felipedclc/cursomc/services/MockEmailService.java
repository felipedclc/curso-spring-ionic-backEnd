package com.felipedclc.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class); // MOSTRA O EMAIL NO LOG DO SERVIDOR  
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email...");
		LOG.info((msg.toString())); // PASSANDO msg PARA STRING
		LOG.info("Email enviado");
		
	}

	
}
