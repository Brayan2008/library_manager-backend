package com.books.lybrary.libros_microservice.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Editorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_EDITORIAL")
    long id_editorial;

    @Column(name = "NOMBRE_EDITORIAL")
    String nombre_editorial;

    @OneToMany(mappedBy = "editorial_id")
    List<Libro> lista_Libros = new ArrayList<>();
}
    