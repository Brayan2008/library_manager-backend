package com.books.lybrary.libros_microservice.services;

import java.util.List;

import com.books.lybrary.libros_microservice.dto.LibroRequest;
import com.books.lybrary.libros_microservice.dto.LibroResponse;
import com.books.lybrary.libros_microservice.model.Libro;

public interface LibroService {
    public List<LibroResponse> getLibros();
    public LibroResponse getLibroById(int id);
    public Libro saveLibro(LibroRequest libro);
    public Libro updateLibro(LibroRequest libro);
    public void deleteLibro(int id);
}
