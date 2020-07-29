package com.felipedclc.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.felipedclc.cursomc.domain.Cliente;
import com.felipedclc.cursomc.domain.Pedido;

public interface EmailService {

	 // ENVIO DE EMAIL COM TEXTO PLANO
	void sendOrderConfirmationEmail(Pedido pedido);
	void sendEmail(SimpleMailMessage msg);
	
	 // ENVIO DE EMAIL COM HTML
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	void sendHtmlEmail(MimeMessage msg);
	
	 // ENVIO DE NOVA SENHA
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
