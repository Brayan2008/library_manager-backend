package com.books.lybrary.libros_microservice.services.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.books.lybrary.libros_microservice.dto.LibroRequest;
import com.books.lybrary.libros_microservice.dto.LibroResponse;
import com.books.lybrary.libros_microservice.model.Libro;

public interface LibroService {
    public List<LibroResponse> getLibros();
    public LibroResponse getLibroById(int id);
    public Libro getLibroComplete(int id);
    public Libro saveLibro(LibroRequest libro, MultipartFile file);
    public Libro updateLibro(int id, LibroRequest libro);
    public Libro patchLibro(int id, LibroRequest libro);
    public boolean deleteLibro(int id);
    public Libro agregarImagen(MultipartFile file, Libro pre);
}
