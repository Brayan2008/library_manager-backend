package com.books.lybrary.libros_microservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class LibrosMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrosMicroserviceApplication.class, args);
		precargar();
	}

	public static void precargar() {
		String carpeta = System.getProperty("user.dir") + "\\images\\";
		File a = new File(carpeta);
		
		if (!a.exists()) {
			a.mkdirs();
			Path destino =  Paths.get(carpeta + "default.png");
			Resource recurso = new ClassPathResource("static\\default.png");
			try {
				Files.copy(recurso.getInputStream(),destino,StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				Logger.getLogger(LibrosMicroserviceApplication.class.getName()).severe(e + " : No se pudo cargar la imagen por default");
			}
		}
	}

}
