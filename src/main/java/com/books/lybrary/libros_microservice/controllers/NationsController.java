package com.books.lybrary.libros_microservice.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.books.lybrary.libros_microservice.dto.NationRequest;
import com.books.lybrary.libros_microservice.model.Nation;
import com.books.lybrary.libros_microservice.services.NationService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("nation")
public class NationsController {

    @Autowired
    NationService nationService;

    @GetMapping
    public ResponseEntity<List<Nation>> getNation() {
        return ResponseEntity.ok(nationService.getNation());
    }

    @GetMapping("/find")
    public ResponseEntity<Nation> getNationById(@RequestParam int id) {
        return nationService.getNationbyID(id)==null ? ResponseEntity.notFound().build():ResponseEntity.ok(nationService.getNationbyID(id));
    }

    @PostMapping
    public ResponseEntity<Nation> postNation(@RequestBody NationRequest nation) {        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(nation.id()).toUri(); 
        return nationService.saveNation(nation)==null ? ResponseEntity.badRequest().build():ResponseEntity.created(uri).build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Nation> updateNation(@PathVariable int id, @RequestBody NationRequest nation) {        
        return nationService.updateNation(id, nation)==null?ResponseEntity.badRequest().build():ResponseEntity.noContent().build();
    }
    
    @PatchMapping("update/{id}")
    public ResponseEntity<Nation> patchNation(@PathVariable int id, @RequestBody NationRequest nation) {
        return nationService.patchNation(id, nation) == null ? ResponseEntity.badRequest().build():ResponseEntity.noContent().build();        
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteNation(@RequestParam int id) {
        return nationService.deleteNation(id) ? ResponseEntity.notFound().build():ResponseEntity.noContent().build();   
    }
    

    
    
    
    

}
