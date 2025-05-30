package com.books.lybrary.libros_microservice.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Autor {

    @Id
    @Column(name = "COD_AUTOR")
    private int cod_autor;
    @Column(name = "NOMBRE_AUTOR")
    private String nombre_autor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_NATION_FK")
    private Nation nation_id; //Como foraneo

    @ManyToMany(mappedBy = "lista_autores")
    private List<Libro> lista_libros_autor = new ArrayList<>();

}
