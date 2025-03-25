package com.books.lybrary.libros_microservice.services;

import java.util.List;

import com.books.lybrary.libros_microservice.dto.AutorRequest;
import com.books.lybrary.libros_microservice.dto.AutorResponse;
import com.books.lybrary.libros_microservice.model.Autor;
import com.books.lybrary.libros_microservice.model.Libro;

public interface AutorService {
    public List<AutorResponse> getAutores();
    public AutorResponse getAutorbyID(int id);
    public Libro saveAutor(AutorRequest libro);
    public Libro updateAutor(Autor libro);
    public void deleteAutor(int id);
}
