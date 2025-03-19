package com.books.lybrary.libros_microservice.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Tema {

    @Id
    @Column(name = "ID_TEMA")
    int id_tema;

    @Column(name = "NOMBRE_TEMA")
    String nombre_tema;

    @ManyToMany(mappedBy = "tema")
    List<Libro> libros = new ArrayList<>();



}
