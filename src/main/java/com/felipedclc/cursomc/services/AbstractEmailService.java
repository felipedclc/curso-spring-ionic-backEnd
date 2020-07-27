package com.felipedclc.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.felipedclc.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj); 
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		// ATRIBUTOS BÁSICOS DE UM EMAIL
		sm.setTo(obj.getCliente().getEmail()); 
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId()); // TITULO DO EMAIL 
		sm.setSentDate(new Date(System.currentTimeMillis()));  
		sm.setText(obj.toString()); // CORPO DO EMAIL (toString Pedido)
		return sm;
	}
	
	
}
