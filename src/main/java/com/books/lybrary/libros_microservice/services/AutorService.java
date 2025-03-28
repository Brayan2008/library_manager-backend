package com.books.lybrary.libros_microservice.services;


import com.books.lybrary.libros_microservice.dto.AutorRequest;
import com.books.lybrary.libros_microservice.dto.AutorResponse;
import com.books.lybrary.libros_microservice.model.Autor;

public interface AutorService {

    public AutorResponse getAutorbyID(int id);
    public AutorRequest saveAutor(AutorRequest libro);
    public AutorRequest updateAutor(Autor libro);
    public void deleteAutor(int id);

}
