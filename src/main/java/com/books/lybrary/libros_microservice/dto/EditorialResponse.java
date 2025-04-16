package com.books.lybrary.libros_microservice.dto;

import java.util.List;

import com.books.lybrary.libros_microservice.model.Editorial;

public record EditorialResponse(long id, String nombre_editorial, List<LibroResponse> libros) {

     /**
     * Metodo para convertir una editorial en una editorialDTO, para evitar 
     * la recursividad
     * @param ed Editorial
     * @return EditorialResponse (con el nombre de la editorial en null)
     */
    public static EditorialResponse convertirEditorial(Editorial ed) {
        return new EditorialResponse(ed.getId_editorial()
                                    ,ed.getNombre_editorial()
                                    ,ed.getLista_Libros().stream()
                                                        .map(LibroResponse::toBookResponse)
                                                        .toList());
    }

}
