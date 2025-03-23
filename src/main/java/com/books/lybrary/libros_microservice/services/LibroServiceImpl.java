package com.books.lybrary.libros_microservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.lybrary.libros_microservice.model.Libro;
import com.books.lybrary.libros_microservice.repository.LibroRepositorio;

@Service
public class LibroServiceImpl implements LibroService {
    
    @Autowired
    private LibroRepositorio libro_acces;

    @Override
    public List<Libro> getLibros() {
        System.out.println("\n\n\n Se uso esta clasE: " + this.getClass().toString()+ "\n\n\n");
        if (libro_acces.findAll().isEmpty()) {
            return null;
        }
        System.out.println("\n" + libro_acces.findAll().toString() + "\n");
        return libro_acces.findAll();
    }

    @Override
    public Libro getLibroById(int id) {
        return libro_acces.findById(id).get();
    }

    @Override
    public Libro saveLibro(Libro libro) {
        return libro_acces.save(libro);
    }

    @Override
    public Libro updateLibro(Libro libro) {
        return libro_acces.save(libro);
    }

    @Override
    public void deleteLibro(int id) {
        libro_acces.deleteById(id);
    }

}
