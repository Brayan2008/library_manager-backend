package com.books.lybrary.libros_microservice.services.interfaces;


import java.util.List;

import com.books.lybrary.libros_microservice.dto.AutorRequest;
import com.books.lybrary.libros_microservice.dto.AutorResponse;


public interface AutorService {
    public List<AutorResponse> getAutor();
    public AutorResponse getAutorbyID(int id);
    public AutorResponse saveAutor(AutorRequest libro);
    public AutorResponse updateAutor(int id, AutorRequest libro);
    public AutorResponse patchAutor(int id, AutorRequest libro);
    public boolean deleteAutor(int id);

}
