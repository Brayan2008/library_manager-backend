package com.books.lybrary.libros_microservice.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.books.lybrary.libros_microservice.dto.AutorRequest;
import com.books.lybrary.libros_microservice.dto.AutorResponse;
import com.books.lybrary.libros_microservice.services.interfaces.AutorService;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("autores")
public class AutorController {
    
    @Autowired
    private AutorService autorService;

    @GetMapping
    public ResponseEntity<List<AutorResponse>> getAutores() {
        return ResponseEntity.ok(autorService.getAutor());
    }

    @GetMapping("/find")
    public ResponseEntity<AutorResponse> getAutorById(@RequestParam int id) {
        return autorService.getAutorbyID(id) == null ? ResponseEntity.notFound().build():ResponseEntity.ok(autorService.getAutorbyID(id));
    }

    @PostMapping
    public ResponseEntity<AutorResponse> postAutor(@RequestBody AutorRequest autor) {
        if(autorService.saveAutor(autor) == null) return ResponseEntity.badRequest().build();

        URI uri = ServletUriComponentsBuilder
                 .fromCurrentRequest()
                 .path("/{id}")
                 .buildAndExpand(autor.cod_autor())
                 .toUri();
        
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<AutorResponse> putAutor(@PathVariable int id, @RequestBody AutorRequest autor) {
        return autorService.updateAutor(id, autor) == null ? ResponseEntity.notFound().build():ResponseEntity.noContent().build();
    }
    
    @PatchMapping("update/{id}")
    public ResponseEntity<AutorResponse> patchAutor(@PathVariable int id, @RequestBody AutorRequest autor) {
        return autorService.patchAutor(id, autor) == null ? ResponseEntity.badRequest().build():ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/id")
    public ResponseEntity<?> deleteAutor(@PathVariable int id) {
        return autorService.deleteAutor(id) ? ResponseEntity.notFound().build():ResponseEntity.noContent().build();
    }
    
    
    
}
