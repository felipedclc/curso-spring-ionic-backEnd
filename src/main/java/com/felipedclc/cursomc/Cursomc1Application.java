package com.felipedclc.cursomc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.felipedclc.cursomc.services.S3Service;

@SpringBootApplication
public class Cursomc1Application implements CommandLineRunner {  
	
	@Autowired
	private S3Service s3Service;
	
	public static void main(String[] args) {
		SpringApplication.run(Cursomc1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\Users\\felip\\Desktop\\Felipe2\\Screenshots\\fotos\\palmito.jpg");
	}
}
