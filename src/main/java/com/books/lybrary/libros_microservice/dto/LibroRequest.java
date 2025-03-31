package com.books.lybrary.libros_microservice.dto;

import java.time.LocalDate;

public record LibroRequest(String nombre_libro, int codigo_libro, long isb, LocalDate fecha, long editorial){
    
}
