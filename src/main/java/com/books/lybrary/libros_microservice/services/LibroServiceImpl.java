package com.books.lybrary.libros_microservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.lybrary.libros_microservice.dto.LibroRequest;
import com.books.lybrary.libros_microservice.dto.LibroResponse;
import com.books.lybrary.libros_microservice.model.Editorial;
import com.books.lybrary.libros_microservice.model.Libro;
import com.books.lybrary.libros_microservice.repository.LibroRepositorio;

@Service
public class LibroServiceImpl implements LibroService {
    
    @Autowired
    private LibroRepositorio libro_acces;

    @Override
    public List<LibroResponse> getLibros() {

        System.out.println("\n\n\n Se uso esta clasE: " + this.getClass().toString()+ "\n\n\n");
        if (libro_acces.findAll().isEmpty()) {
            return null;
        }
        System.out.println("\n" + libro_acces.findAll().toString() + "\n");
        return libro_acces.findAll()
                .stream()
                .map(libro->new LibroResponse(libro.getTitulo_libro(),
                        libro.getId_libro(),
                        libro.getIsbn_libro(), 
                        libro.getFecha_publicacion_libro(),
                        (libro.getEditorial_id()!=null)?libro.getEditorial_id().getNombre_editorial():null)) //Si la editorial (id) es nula, se asigna null
                .toList();
    }

    @Override
    public LibroResponse getLibroById(int id) {
        var libro_pre = libro_acces.findById(id);
        if (libro_pre.isEmpty()) {
            return null;
        }
        var libro = libro_pre.get();
        return new LibroResponse(libro.getTitulo_libro(),
                    libro.getId_libro(), 
                    libro.getIsbn_libro(),
                    libro.getFecha_publicacion_libro(),
                    (libro.getEditorial_id()!=null)?libro.getEditorial_id().getNombre_editorial():null);
    }

    @Override
    public Libro saveLibro(LibroRequest libro) {
        Libro post = new Libro();
        Editorial editorial = new Editorial();

        post.setTitulo_libro(libro.nombre_libro());
        post.setIsbn_libro(libro.isb());
        post.setFecha_publicacion_libro(libro.fecha());
        editorial.setId_editorial(libro.id_editorial());
        editorial.setNombre_editorial(libro.nombre_editorial());
        post.setEditorial_id(editorial);
        
        return libro_acces.save(post);
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
