package com.books.lybrary.libros_microservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.books.lybrary.libros_microservice.model.Libro;
import com.books.lybrary.libros_microservice.services.LibroService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private LibroService libroservice;

    @GetMapping
    public List<Libro> getBookName(@RequestParam int id) {
        return libroservice.getLibros();
    }


}
