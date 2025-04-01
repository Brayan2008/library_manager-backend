package com.books.lybrary.libros_microservice.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.books.lybrary.libros_microservice.dto.EditorialRequest;
import com.books.lybrary.libros_microservice.dto.EditorialResponse;
import com.books.lybrary.libros_microservice.services.EditorialService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("editorial")
public class EditorialController {
   
    @Autowired
    private EditorialService editorialService;

    @GetMapping
    public ResponseEntity<List<EditorialResponse>> getEditoriales() {
        return ResponseEntity.ok(editorialService.getEditoriales());
    }
    
    @GetMapping("find")
    public ResponseEntity<EditorialResponse> getEditorialbyID(@RequestParam long id) {
        return editorialService.getEditorialById(id) == null ? ResponseEntity.notFound().build():ResponseEntity.ok(editorialService.getEditorialById(id)); 
    }

    @PostMapping
    public ResponseEntity<EditorialRequest> postEditorial(@RequestBody EditorialRequest editorial) {
        if (editorialService.saveEditorial(editorial)==null) {
            return ResponseEntity.badRequest().build();
        }
        URI direccion = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(editorial.id()).toUri();

        return ResponseEntity.created(direccion).build();

    }

    @PutMapping("update/{id}")
    public ResponseEntity<EditorialResponse> actualizarEditorial(@PathVariable long id, @RequestBody EditorialRequest editorial) {
        return editorialService.updateEditorial(editorial)==null ? ResponseEntity.badRequest().build():ResponseEntity.noContent().build();
    }
    
    @PatchMapping("patch/{id}")
    public ResponseEntity<EditorialResponse> patchEditoril(@PathVariable long id, @RequestBody EditorialRequest editorial) {
        return editorialService.patchEditorial(editorial)==null ? ResponseEntity.badRequest().build():ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")    
    public ResponseEntity<?> deleteEditorial(@PathVariable long id) {
        return editorialService.deleteEditorial(id) ? ResponseEntity.noContent().build():ResponseEntity.notFound().build();
    }

}
