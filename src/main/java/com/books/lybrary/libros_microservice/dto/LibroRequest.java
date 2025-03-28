package com.books.lybrary.libros_microservice.dto;

import java.time.LocalDate;

import com.books.lybrary.libros_microservice.model.Editorial;

public record LibroRequest(String nombre_libro, int codigo_libro, long isb, LocalDate fecha, Editorial editorial){
    
}
