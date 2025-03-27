package com.books.lybrary.libros_microservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.lybrary.libros_microservice.dto.LibroRequest;
import com.books.lybrary.libros_microservice.dto.LibroResponse;
import com.books.lybrary.libros_microservice.model.Editorial;
import com.books.lybrary.libros_microservice.model.Libro;
import com.books.lybrary.libros_microservice.repository.LibroRepositorio;

import com.books.lybrary.libros_microservice.repository.EditorialRepositorio;

@Service
public class LibroServiceImpl implements LibroService{
    
    @Autowired
    private LibroRepositorio libro_acces;
    @Autowired
    private EditorialRepositorio editorial_acces;

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
        //Primero creamos la editorial
        Editorial editorial = new Editorial();
        editorial.setId_editorial(libro.id_editorial());
        editorial.setNombre_editorial(libro.nombre_editorial());
        System.out.println("Guardando la editorial: " + editorial.toString());
        editorial_acces.save(editorial);//Guardamos la editorial
        
        //Luego creamos el libro
        Libro pre = new Libro();
        pre.setId_libro(libro.codigo_libro());
        pre.setTitulo_libro(libro.nombre_libro());
        pre.setIsbn_libro(libro.isb());
        pre.setFecha_publicacion_libro(libro.fecha());
        //Lo relacionamos con la editorial
        pre.setEditorial_id(editorial);
        
        return libro_acces.save(pre);//Lo guardamos
    
    }
    
    @Override
    public Libro updateLibro(LibroRequest libro) {
        if (!libro_acces.findById(libro.codigo_libro()).isPresent()) {
            return null;
        }

        Libro pre = libro_acces.findById(libro.codigo_libro()).get();
        pre.setTitulo_libro(libro.nombre_libro());
        pre.setIsbn_libro(libro.isb());
        pre.setFecha_publicacion_libro(libro.fecha());


        //Ahora la editorial
        var a = editorial_acces.findById(libro.id_editorial());
        if (a.isPresent()) {
            a.get().setNombre_editorial(libro.nombre_editorial());
            editorial_acces.save(a.get());
            return libro_acces.save(pre);
        }

        Editorial editorial = new Editorial();
        editorial.setId_editorial(libro.id_editorial());
        editorial.setNombre_editorial(libro.nombre_editorial());
        editorial_acces.save(editorial);//Guardamos la editorial
        
        pre.setEditorial_id(editorial);
        
        return libro_acces.save(pre);//Lo guardamos
    }

    


    @Override
    public Libro patchLibro(LibroRequest libro) {
        var a = libro_acces.findById(libro.codigo_libro());
        //Si no existe el libro 
        if (a.isEmpty()) {
            return null;
        }
        //Obtiene el libro
        Libro pre = a.get();
        //Busca los campos que se quieren modificar
        if (!libro.nombre_libro().isEmpty())  {
            pre.setTitulo_libro(libro.nombre_libro());
        }
        if (!(libro.isb() == 0)) {
            pre.setIsbn_libro(libro.isb());
        }
        if (!libro.fecha().toString().isEmpty()) {
            pre.setFecha_publicacion_libro(libro.fecha());
        }            
            
        //Busca la editorial
        var b = editorial_acces.findById(libro.id_editorial());
            //Si la editorial existe, se modifica
            if (b.isPresent()) {
                b.get().setNombre_editorial(libro.nombre_editorial());
                editorial_acces.save(b.get());
                pre.setEditorial_id(b.get());
            }else{
                Editorial editorial = new Editorial();
                editorial.setId_editorial(libro.id_editorial());
                editorial.setNombre_editorial(libro.nombre_editorial());
                editorial_acces.save(editorial);//Guardamos la editorial
                pre.setEditorial_id(editorial);
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
