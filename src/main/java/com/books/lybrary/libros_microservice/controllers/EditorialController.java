package com.books.lybrary.libros_microservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.books.lybrary.libros_microservice.dto.EditorialResponse;
import com.books.lybrary.libros_microservice.services.EditorialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("editorial")
public class EditorialController {
   
    @Autowired
    private EditorialService editorialService;

    @GetMapping()
    public ResponseEntity<List<EditorialResponse>> getEditoriales() {
        return ResponseEntity.ok(editorialService.getEditoriales());
    }
    
    @GetMapping("find")
    public ResponseEntity<EditorialResponse> getEditorialbyID(@RequestParam long id) {
        return editorialService.getEditorialById(id) == null ? ResponseEntity.notFound().build():ResponseEntity.ok(editorialService.getEditorialById(id)); 
    }
    

}
