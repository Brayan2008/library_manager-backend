package com.books.lybrary.libros_microservice.dto;

import com.books.lybrary.libros_microservice.model.Autor;

public record AutorResponse(String nombre_autor, String nombre_nation, int cod_autor) {

    public static AutorResponse convertirAutor(Autor autor) {
        return new AutorResponse(autor.getNombre_autor(), autor.getNation_id() != null ? autor.getNation_id().getName_nation() : null, autor.getCod_autor());
    }
}
