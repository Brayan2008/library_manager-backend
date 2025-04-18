package com.books.lybrary.libros_microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.books.lybrary.libros_microservice.model.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro,Integer> {
    
}
