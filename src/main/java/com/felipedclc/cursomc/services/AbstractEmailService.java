package com.felipedclc.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.felipedclc.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	 // PEGA O TAMPLATE HTML, PREENCHE OS DADOS DO PEDIDO RETORNA UM STRING DE PEDIDO 
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context(); // OBJETO CONTEXT ACESSA O TEMPLATE
		context.setVariable("pedido", obj); // ENVIANDO OS DADOS DO PEDIDO (está "pedido" no template) - obj tem o apelido de "pedido"
		return templateEngine.process("email/confirmacaoPedido", context); // PROCESSANDO O TEMPLATE, framework busca nos templates
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try { // TENTA MANDAR O HTML
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		} 
		catch (MessagingException e) { // SE DER ALGUM PROBLEMA MANDA O TEXTO PLANO 
			sendOrderConfirmationEmail(obj);
		} 	
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimiMessage = javaMailSender.createMimeMessage(); // INSTANCIANDO O MIMIMESSAGE
		MimeMessageHelper mmh = new MimeMessageHelper(mimiMessage, true); // ATRIBUINDO VALORES A MENSAGEM
		mmh.setTo(obj.getCliente().getEmail()); // ENDEREÇO DO ENVIO 
		mmh.setFrom(sender); // REMETENE DO EMAIL
		mmh.setSubject("Pedido confirmado! Código" + obj.getId()); // ASSUNTO DO EMAIL
		mmh.setSentDate(new Date(System.currentTimeMillis())); // MOMENTO DO ENVIO
		mmh.setText(htmlFromTemplatePedido(obj), true); // CORPO DO EMAIL (EMAIL HTML PROCESSADO NO thymeleaf
		return mimiMessage;		
	}
}
