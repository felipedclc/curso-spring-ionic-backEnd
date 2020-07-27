package com.felipedclc.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.felipedclc.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
