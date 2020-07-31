package com.felipedclc.cursomc.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.felipedclc.cursomc.services.exceptions.FileException;

@Service
public class ImageService { // SERVIÇO RESPONSAVEL POR FORNECER FUNCIONALIDADES DE IMAGEM

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) { // CONVERTENDO AS IMAGENS PARA FORMATO UNICO
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename()); // PEGANDO A EXTENSÃO DO ARQUIVO
		if (!"png".equals(ext) && !"jpg".equals(ext)) { // SE A EXTENSAÕ N FOR NEM png NEM jpg
			throw new FileException("Somente imagens PNG e JPG são permitidas");
		}

		try {
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream()); // LENDO O ARQUIVO
			if ("png".equals(ext)) {
				img = pngToJpg(img); // CONVERTENDO png PARA jpg
			}
			return img;
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

	public BufferedImage pngToJpg(BufferedImage img) { // METODO PARA CONVERTER png PARA jpg (CODIGO PADRÃO)
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		return jpgImage;
	}

	public InputStream getInputStream(BufferedImage img, String extension) { // RETORNA UM INPUT STREAM A PARTIR DO BUFFERED IMAGE
		try { 																		// (CODIGO PADRÃO)
			ByteArrayOutputStream os = new ByteArrayOutputStream();			 // UPLOAD NO S3 SÓ RECEBE INPUT STREAM 	
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}
}
