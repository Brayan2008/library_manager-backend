package com.books.lybrary.libros_microservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.books.lybrary.libros_microservice.dto.LibroRequest;
import com.books.lybrary.libros_microservice.dto.LibroResponse;
import com.books.lybrary.libros_microservice.model.Libro;
import com.books.lybrary.libros_microservice.services.LibroService;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private LibroService libroservice;

    @GetMapping
    public ResponseEntity<List<LibroResponse>> getBookName() {
        return ResponseEntity.ok(libroservice.getLibros());
    }

    @GetMapping({"/find","/id"})
    public ResponseEntity<LibroResponse> getBookbyID(@RequestParam @PathVariable int id) {
        return libroservice.getLibroById(id) ==null ? ResponseEntity.notFound().build():ResponseEntity.ok(libroservice.getLibroById(id));
    }

    @PostMapping
    public ResponseEntity<LibroResponse> postLibro(@RequestBody LibroRequest libro){         
        libroservice.saveLibro(libro);

        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(libro.codigo_libro()).
                toUri();

        return ResponseEntity.created(url).build();
    }

    @PutMapping("modificar/{id}")
    public ResponseEntity<Libro> putLibro(@PathVariable int id, @RequestBody LibroRequest entity) {
        System.out.println("Se cambia todo");
        return libroservice.updateLibro(entity)!=null ? ResponseEntity.noContent().build():ResponseEntity.badRequest().build();
    }

    @PatchMapping("modificar/{id}")
    public ResponseEntity<Libro> patchLibro(@PathVariable int id, @RequestBody LibroRequest entity) {
        System.out.println("Se cambia algo");
        return libroservice.patchLibro(entity)!=null ? ResponseEntity.noContent().build():ResponseEntity.badRequest().build();
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable int id) {
        return libroservice.deleteLibro(id) ? ResponseEntity.noContent().build():ResponseEntity.badRequest().build();    
    }
    
    
    
} 
    
    



