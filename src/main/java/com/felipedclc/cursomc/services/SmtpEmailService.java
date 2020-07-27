package com.felipedclc.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService{

	@Autowired
	private MailSender mailsender; //framework instancia todos os dados do email que estão no application-dev (PARA ENVIO DO EMAIL)
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class); // MOSTRA O EMAIL NO LOG DO SERVIDOR  
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email...");
		mailsender.send(msg); // ENVIANDO O EMAIL
		LOG.info("Email enviado");
	}
}
