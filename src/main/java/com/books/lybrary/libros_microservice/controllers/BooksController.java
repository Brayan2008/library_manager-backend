package com.books.lybrary.libros_microservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.books.lybrary.libros_microservice.dto.LibroRequest;
import com.books.lybrary.libros_microservice.dto.LibroResponse;
import com.books.lybrary.libros_microservice.model.Libro;
import com.books.lybrary.libros_microservice.services.interfaces.LibroService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private LibroService libroservice;

    @GetMapping
    public ResponseEntity<List<LibroResponse>> getBookName() {
        return ResponseEntity.ok(libroservice.getLibros());
    }

    @GetMapping({"/find"})
    public ResponseEntity<LibroResponse> getBookbyID(@RequestParam int id) {
        return libroservice.getLibroById(id) ==null ? ResponseEntity.notFound().build():ResponseEntity.ok(libroservice.getLibroById(id));
    }

    @PostMapping
    public ResponseEntity<LibroResponse> postLibro(@RequestParam String nombre_libro, 
                                                   @RequestParam int codigo_libro,
                                                   @RequestParam long isb,
                                                   @RequestParam LocalDate fecha,
                                                   @RequestParam long editorial,
                                                   @RequestParam MultipartFile file) {  

        LibroRequest libro = new LibroRequest(nombre_libro, codigo_libro, isb, fecha, editorial);        

        if (libroservice.saveLibro(libro,file)==null) return ResponseEntity.badRequest().build();   

        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(libro.codigo_libro()).
                toUri();
 
        return ResponseEntity.created(url).build();
    }

    @PutMapping("updateImagen/{codigo_libro}")
    public ResponseEntity<?> actualizarImagen(@PathVariable int codigo_libro, @RequestParam MultipartFile file) {
        Libro libro = libroservice.getLibroComplete(codigo_libro);
        if(libro == null) return ResponseEntity.notFound().build();
        libroservice.agregarImagen(file, libro);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("modificar/{id}")
    public ResponseEntity<Libro> putLibro(@PathVariable int id, @RequestBody LibroRequest entity) {
        return libroservice.updateLibro(id,entity)!=null ? ResponseEntity.noContent().build():ResponseEntity.badRequest().build();
    }

    @PatchMapping("modificar/{id}")
    public ResponseEntity<Libro> patchLibro(@PathVariable int id, @RequestBody LibroRequest entity) {
        return libroservice.patchLibro(id, entity)!=null ? ResponseEntity.noContent().build():ResponseEntity.badRequest().build();
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable int id) {
        return libroservice.deleteLibro(id) ? ResponseEntity.noContent().build():ResponseEntity.badRequest().build();    
    }
    
}    