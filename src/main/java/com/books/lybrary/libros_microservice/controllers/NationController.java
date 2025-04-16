package com.books.lybrary.libros_microservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.books.lybrary.libros_microservice.dto.NationResponse;
import com.books.lybrary.libros_microservice.model.Nation;
import com.books.lybrary.libros_microservice.services.interfaces.NationService;

import java.net.URI;
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
@RequestMapping("nations")
public class NationController {
    
    @Autowired
    private NationService nationService;


    @GetMapping
    public ResponseEntity<List<NationResponse>> getNations() {
        return ResponseEntity.ok(nationService.getNations());
    }

    @GetMapping("find")
    public ResponseEntity<NationResponse> getByID(@RequestParam int id) {
        return nationService.getNationbyID(id) == null ? ResponseEntity.notFound().build():ResponseEntity.ok(nationService.getNationbyID(id));
    }

    @PostMapping
    public ResponseEntity<NationResponse> postNation(@RequestBody Nation entity) {
        return nationService.saveNation(entity) == null ? ResponseEntity.badRequest().build():ResponseEntity.ok(nationService.saveNation(entity));
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<NationResponse> actualizarById(@PathVariable int id, @RequestBody Nation nation) {
        if(nationService.saveNation(nation) == null) return ResponseEntity.badRequest().build();
        
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nation.getId_nation())
                .toUri();

        return ResponseEntity.created(uri).body(nationService.saveNation(nation));
    }

    @PutMapping("modificar/{id}")
    public ResponseEntity<NationResponse> putLibro(@PathVariable int id, @RequestBody Nation nation) {
        return nationService.updateNation(id,nation)!=null ? ResponseEntity.noContent().build():ResponseEntity.badRequest().build();
    }

    @PatchMapping("modificar/{id}")
    public ResponseEntity<NationResponse> patchLibro(@PathVariable int id, @RequestBody Nation nation) {
        return nationService.patchNation(id, nation)!=null ? ResponseEntity.noContent().build():ResponseEntity.badRequest().build();
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable int id) {
        return nationService.deleteNation(id) ? ResponseEntity.noContent().build():ResponseEntity.badRequest().build();    
    }

    

}
