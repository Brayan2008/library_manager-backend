package com.books.lybrary.libros_microservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.lybrary.libros_microservice.dto.LibroRequest;
import com.books.lybrary.libros_microservice.dto.LibroResponse;
import com.books.lybrary.libros_microservice.model.Libro;
import com.books.lybrary.libros_microservice.repository.LibroRepositorio;


@Service
public class LibroServiceImpl implements LibroService{
    
    @Autowired
    private LibroRepositorio libro_acces;
    
    @Autowired 
    private EditorialService editorialService;

    @Override
    public List<LibroResponse> getLibros() {
        if (libro_acces.findAll().isEmpty()) {return null;}

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
        if (libro_acces.findById(libro.codigo_libro()).isPresent()) {
            return null;
        }
        
        Libro pre = new Libro();
        pre.setId_libro(libro.codigo_libro());
        pre.setTitulo_libro(libro.nombre_libro());
        pre.setIsbn_libro(libro.isb());
        pre.setFecha_publicacion_libro(libro.fecha());
        editorialService.saveEditorial(libro.editorial());
        pre.setEditorial_id(libro.editorial());
        
        return libro_acces.save(pre);//Lo guardamos
    
    }
    
    @Override
    public Libro updateLibro(int id, LibroRequest libro) {
        var a = libro_acces.findById(id);
        if (!a.isPresent()) {
            return null;
        }

        Libro pre = a.get();
        try {            
            pre.setTitulo_libro(libro.nombre_libro());
            pre.setIsbn_libro(libro.isb());
            pre.setFecha_publicacion_libro(libro.fecha());
            editorialService.saveEditorial(libro.editorial()); 
            pre.setEditorial_id(libro.editorial());
        } catch (Exception e) {
            System.out.println(e + "\n No se insertaron todos los campos\n");
            return null;
        }
        
        return libro_acces.save(pre);//Lo guardamos
    }

    @Override
    public Libro patchLibro(int id, LibroRequest libro) {
        var a = libro_acces.findById(id);
        //Si no existe el libro 
        if (a.isEmpty()) {
            return null;
        }
        //Obtiene el libro
        Libro pre = a.get();

        //Busca los campos que se quieren modificar
        if (libro.nombre_libro()!=null)  {
            pre.setTitulo_libro(libro.nombre_libro());
        }
        if (libro.isb() != 0) {
            pre.setIsbn_libro(libro.isb());
        }
        if (libro.fecha()!=null) {
            pre.setFecha_publicacion_libro(libro.fecha());
        }            
        if (libro.editorial() != null) {
            pre.setEditorial_id(libro.editorial());
        }

        return libro_acces.save(pre);//Lo guardamos
        
    }

    @Override
    public boolean deleteLibro(int id) {
        if (libro_acces.findById(id).isEmpty()) {
            return false;
        }
        libro_acces.deleteById(id);
        return true;
    }       
        
}
