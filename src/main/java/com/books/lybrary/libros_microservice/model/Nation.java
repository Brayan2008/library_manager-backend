package com.books.lybrary.libros_microservice.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Nation {
    @Id
    @Column(name = "ID_NATION")
    int id_nation;
    @Column(name = "NAME_NATION")
    String name_nation;
    
    @OneToMany(mappedBy = "nation_id") 
    List<Autor> lista_autores = new ArrayList<>();

}   
