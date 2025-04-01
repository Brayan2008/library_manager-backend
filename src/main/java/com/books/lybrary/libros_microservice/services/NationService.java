package com.books.lybrary.libros_microservice.services;

import java.util.List;

import com.books.lybrary.libros_microservice.dto.NationRequest;
import com.books.lybrary.libros_microservice.model.Nation;

public interface NationService{
    public List<Nation> getNation();
    public Nation getNationbyID(int id);
    public Nation saveNation(NationRequest nation);
    public Nation updateNation(int id, NationRequest nation);
    public Nation patchNation(int id,NationRequest nation);
    public boolean deleteNation(int id);
}
