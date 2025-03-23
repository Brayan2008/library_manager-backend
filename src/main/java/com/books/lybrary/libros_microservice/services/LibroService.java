package com.books.lybrary.libros_microservice.services;

import java.util.List;

import com.books.lybrary.libros_microservice.model.Libro;

public interface LibroService {
    public List<Libro> getLibros();
    public Libro getLibroById(int id);
    public Libro saveLibro(Libro libro);
    public Libro updateLibro(Libro libro);
    public void deleteLibro(int id);
}
