package com.books.lybrary.libros_microservice.services.interfaces;

import java.util.List;

import com.books.lybrary.libros_microservice.dto.NationResponse;
import com.books.lybrary.libros_microservice.model.Nation;

public interface NationService {
    public List<NationResponse> getNations();
    public NationResponse getNationbyID(int id);
    public NationResponse saveNation(Nation libro);
    public NationResponse updateNation(int id, Nation libro);
    public NationResponse patchNation(int id, Nation libro);
    public boolean deleteNation(int id);

}
