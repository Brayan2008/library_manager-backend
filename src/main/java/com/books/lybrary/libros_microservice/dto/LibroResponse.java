package com.books.lybrary.libros_microservice.dto;

import java.time.LocalDate;

import com.books.lybrary.libros_microservice.model.Libro;

public record LibroResponse(String nombre_libro, int codigo_libro, long isb, LocalDate fecha, String nombre_editorial) {

    /** Por ahora solo lo utiliza para generar la lista de libros en Editorial */
    public static LibroResponse toBookResponse(Libro lib) {
        return new LibroResponse(lib.getTitulo_libro(), 
                                lib.getId_libro(), 
                                lib.getIsbn_libro(), 
                                lib.getFecha_publicacion_libro(), 
                                null); 
    }
    
    /**
     * Transforma un libro a LibroResponse. Es ulizado en 
     * {@link com.books.lybrary.libros_microservice.services.LibroServiceImpl LibroServiceImpl} 
     * para obtener un Libro en formato {@code DTO}
     * @param libro El libro a transformar
     * @return Devuelve un {@link LibroResponse}
     */
    public static LibroResponse convertToLibroResponse(Libro libro){
        return new LibroResponse(libro.getTitulo_libro(), 
                                libro.getId_libro(), 
                                libro.getIsbn_libro(), 
                                libro.getFecha_publicacion_libro(),
                                (libro.getEditorial_id()!=null) ? libro.getEditorial_id().getNombre_editorial():null);
    }
}
