package com.books.lybrary.libros_microservice.dto;

import java.util.List;

import com.books.lybrary.libros_microservice.model.Nation;

public record NationResponse(int id_nation, String name_nation, List<AutorResponse> lista_autores) {    

    public static NationResponse convertirNation(Nation nation) {
        return new NationResponse(nation.getId_nation(), 
                                  nation.getName_nation(), 
                                  nation.getLista_autores()
                                        .stream()
                                        .map(AutorResponse::convertirAutor)
                                        .toList());
    }
}