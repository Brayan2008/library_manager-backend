package com.books.lybrary.libros_microservice.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.books.lybrary.libros_microservice.dto.LibroRequest;
import com.books.lybrary.libros_microservice.dto.LibroResponse;
import com.books.lybrary.libros_microservice.model.Libro;
import com.books.lybrary.libros_microservice.repository.EditorialRepositorio;
import com.books.lybrary.libros_microservice.repository.LibroRepositorio;
import com.books.lybrary.libros_microservice.services.interfaces.LibroService;


@Service
public class LibroServiceImpl implements LibroService{

    @Autowired
    private LibroRepositorio libro_acces;
    
    @Autowired 
    private EditorialRepositorio editorialrepo;

    @Override
    public List<LibroResponse> getLibros() {
        if (libro_acces.findAll().isEmpty()) {return null;}

        return libro_acces.findAll()
                .stream()
                .map(LibroResponse::convertToLibroResponse) //Si la editorial (id) es nula, se asigna null
                .toList();
    }

    @Override
    public LibroResponse getLibroById(int id) {
        var libro_pre = libro_acces.findById(id);
        if (libro_pre.isEmpty()) return null;
       
        var libro = libro_pre.get();
        return new LibroResponse(libro.getTitulo_libro(),
                                 libro.getId_libro(), 
                                 libro.getIsbn_libro(),
                                 libro.getFecha_publicacion_libro(),
                                 (libro.getEditorial_id()!=null)?libro.getEditorial_id().getNombre_editorial():null,
                                 libro.getImagen_path());
    }

    @Override
    public Libro getLibroComplete(int id){
        return !libro_acces.existsById(id) ? null:libro_acces.findById(id).get();
    }

    @Override
    public Libro saveLibro(LibroRequest libro, MultipartFile file) {
        if (libro_acces.findById(libro.codigo_libro()).isPresent()) return null;
        if (libro.codigo_libro()==0||libro.nombre_libro()==null||libro.fecha()==null||libro.isb()==0) return null;                

        Libro pre = new Libro();
        pre.setId_libro(libro.codigo_libro());
        pre.setTitulo_libro(libro.nombre_libro());
        pre.setIsbn_libro(libro.isb());
        pre.setFecha_publicacion_libro(libro.fecha());

        /* En caso la editorial sea nula o no aparezca */
        if (libro.editorial() == 0) {
            pre.setEditorial_id(null);
        } else if(editorialrepo.existsById(libro.editorial())){ //Buscamos la editorial, en caso de no encontrarla, dara un error
            pre.setEditorial_id(editorialrepo.findById(libro.editorial()).get());
        } else {
            return null; //Error la editorial no existe
        }
        
        return agregarImagen(file, pre);//Lo guardamos
    }
    
    @Override
    public Libro updateLibro(int id, LibroRequest libro) {
        var a = libro_acces.findById(id);
        if (!a.isPresent()) return null;

        Libro pre = a.get();
            //El metodo Put debe tener todos los campos a excepcion de {Editorial}, pues se actualiza (esto es mas por seguridad, pues todos tienen que tener un valor asignado)
            if (libro.nombre_libro()==null||libro.fecha()==null||libro.isb()==0) return null;                  

            pre.setTitulo_libro(libro.nombre_libro());
            pre.setIsbn_libro(libro.isb());
            pre.setFecha_publicacion_libro(libro.fecha());

            //Aqui asignamos la editorial con la que se relacionará, en caso de encontrarla
            if(editorialrepo.existsById(libro.editorial())){
                pre.setEditorial_id(editorialrepo.findById(libro.editorial()).get());
            } else {
                if (libro.editorial() == 0) {
                    pre.setEditorial_id(null);
                    return libro_acces.save(pre);
                }
                return null; //Error la editorial no existe
            };

        return libro_acces.save(pre);//Lo guardamos
    }

    @Override
    public Libro patchLibro(int id, LibroRequest libro) { 
        var a = libro_acces.findById(id);
        //Si no existe el libro 
        if (a.isEmpty()) return null;
        
        //Obtiene el libro
        Libro pre = a.get();

        //Busca los campos que se quieren modificar
        if (libro.nombre_libro()!=null) pre.setTitulo_libro(libro.nombre_libro());
        if (libro.isb() != 0)           pre.setIsbn_libro(libro.isb());
        if (libro.fecha()!=null)        pre.setFecha_publicacion_libro(libro.fecha());

        //Buscamos que editorial exista y que no sea 0 -> (campo vacio)            
        if (libro.editorial() != 0 && editorialrepo.existsById(libro.editorial())) {
            pre.setEditorial_id(editorialrepo.findById(libro.editorial()).get());
        } else{
            if (libro.editorial() == 0) {
                pre.setEditorial_id(null);
                return libro_acces.save(pre);
            }
            return null; //La editorial no existe
        }

        return libro_acces.save(pre);//Lo guardamos
        
    }

    @Override
    public boolean deleteLibro(int id) {
        if (libro_acces.findById(id).isEmpty()) return false;
        Path image_name = Paths.get(libro_acces.findById(id).get().getImagen_path());
        Path image_path = Paths.get(System.getProperty("user.dir") + "\\images\\").resolve(image_name);
        try {
            Files.delete(image_path);
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).severe("Error al eliminar la imagen");
        }
        
        libro_acces.deleteById(id);
        return true;
    }
    
    @Override
    public Libro agregarImagen(MultipartFile file, Libro pre) {
        //Buscamos la carpeta y le asignamos un nombre al archivo
        if(!file.isEmpty()){
            Path carpeta_destino = Paths.get(System.getProperty("user.dir") + "/images/");
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath =  carpeta_destino.resolve(filename);   //Esta sera la ruta final
            
            try{
                Files.copy(file.getInputStream(), filePath);
                pre.setImagen_path(filename);
                Logger.getLogger(this.getClass().getName()).info("Se subio la imagen");
            } catch (IllegalStateException | IOException e) {
                Logger.getLogger(this.getClass().getName()).severe("Error al guardar la imagen");
            }
        } else {
            try{
                pre.setImagen_path("images\\default.png");
            } catch (IllegalStateException e) {
                Logger.getLogger(this.getClass().getName()).severe("Error al poner la imagen por defecto");
            }
        }
        return libro_acces.save(pre);        
    }
        
}