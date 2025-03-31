package com.books.lybrary.libros_microservice.dto;

import java.time.LocalDate;

import com.books.lybrary.libros_microservice.model.Libro;

public record LibroResponse(String nombre_libro, int codigo_libro, long isb, LocalDate fecha, String nombre_editorial) {

    /** Por ahora solo lo utiliza para generar la lista de libros en EditorialService</a> */
    public static LibroResponse toBookResponse(Libro lib) {
            return new LibroResponse(lib.getTitulo_libro(), lib.getId_libro(), lib.getIsbn_libro(), lib.getFecha_publicacion_libro(), null); 
    }
}
