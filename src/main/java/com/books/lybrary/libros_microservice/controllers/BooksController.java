package com.books.lybrary.libros_microservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.books.lybrary.libros_microservice.dto.LibroRequest;
import com.books.lybrary.libros_microservice.dto.LibroResponse;
import com.books.lybrary.libros_microservice.services.LibroService;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private LibroService libroservice;

    @GetMapping
    public ResponseEntity<List<LibroResponse>> getBookName() {
        return ResponseEntity.ok(libroservice.getLibros());
    }

    @GetMapping("/find")
    public ResponseEntity<LibroResponse> getBookbyID(@RequestParam int id) {
        return libroservice.getLibroById(id) ==null ? ResponseEntity.notFound().build():ResponseEntity.ok(libroservice.getLibroById(id));
    }

    @PostMapping
    public ResponseEntity<LibroResponse> postLibro(@RequestBody LibroRequest libro) {        
        libroservice.saveLibro(libro);

        URI url = ServletUriComponentsBuilder.fromCurrentRequest().
                buildAndExpand(libro.codigo_libro()).
                toUri();
        
                return ResponseEntity.created(url).build();
    }


    }
    
    



