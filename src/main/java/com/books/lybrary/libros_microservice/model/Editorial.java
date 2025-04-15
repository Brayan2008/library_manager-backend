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
public class Editorial {

    @Id
    @Column(name = "ID_EDITORIAL")
    private long id_editorial;

    @Column(name = "NOMBRE_EDITORIAL")
    private String nombre_editorial;

    @OneToMany(mappedBy = "editorial_id")    
    private List<Libro> lista_Libros = new ArrayList<>();
}
    