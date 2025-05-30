package com.books.lybrary.libros_microservice.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Libro {
        
    @Id
    @Column(name = "ID_LIBRO")
    private int id_libro;
    @Column(name = "TITULO_LIBRO")
    private String titulo_libro;
    @Column(name = "DIRECCION_IMAGEN_LIBRO")
    private String imagen_path;
    @Column(name = "ISBN_LIBRO")
    private long isbn_libro;
    @Column(name = "FECHA_PUBLICACION_LIBRO")
    private LocalDate fecha_publicacion_libro;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_EDITORIAL_FK")
    private Editorial editorial_id;
    
    @ManyToMany
    @JoinTable(name = "AUTORxLIBRO",
            joinColumns = @JoinColumn(name = "ID_LIBRO_FK"),
            inverseJoinColumns = @JoinColumn(name = "COD_AUTOR_FK"))
    private final List<Autor> lista_autores = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(name = "TEMAxLIBRO", 
            joinColumns = @JoinColumn(name = "ID_LIBRO_FK"), 
            inverseJoinColumns = @JoinColumn(name ="ID_TEMA_FX"))
    private final List<Tema> lista_tema = new ArrayList<>();
}
