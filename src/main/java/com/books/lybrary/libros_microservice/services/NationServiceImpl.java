package com.books.lybrary.libros_microservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.lybrary.libros_microservice.dto.NationRequest;
import com.books.lybrary.libros_microservice.model.Nation;
import com.books.lybrary.libros_microservice.repository.NationRepositorio;

@Service
public class NationServiceImpl implements NationService {
    
    @Autowired
    private NationRepositorio nationRepositorio;

    @Override
    public List<Nation> getNation() {
        return nationRepositorio.findAll();
    }
    
    @Override
    public Nation getNationbyID(int id) {
        return nationRepositorio.existsById(id) ? nationRepositorio.findById(id).get():null;
    }

    @Override
    public Nation saveNation(NationRequest nation) {
        if (nation.id() == 0 || nation.nombre_nacion()==null || nationRepositorio.existsById(nation.id())) {
            return null;
        }
        Nation a = new Nation();
        a.setId_nation(nation.id());
        a.setName_nation(nation.nombre_nacion());
        return nationRepositorio.save(a);
    }
    
    @Override
    public Nation updateNation(int id, NationRequest nation) {
        if (!nationRepositorio.existsById(id) || nation.nombre_nacion()==null) {
            System.out.println(nationRepositorio.existsById(id));
            return null;
        }
        var pre = nationRepositorio.findById(id).get();
        pre.setName_nation(nation.nombre_nacion());
        return nationRepositorio.save(pre);
    }

    @Override
    public Nation patchNation(int id, NationRequest nation) {
        if (!nationRepositorio.existsById(id)) {
            return null;
        }
        Nation pre = nationRepositorio.findById(id).get();
        if (nation.nombre_nacion()!=null) {
            System.out.println("Se cambia");
            pre.setName_nation(nation.nombre_nacion());
        }
        return nationRepositorio.save(pre);
        
    }

    @Override
    public boolean deleteNation(int id) {
        if (!nationRepositorio.existsById(id)) {
            return false;
        }
        nationRepositorio.deleteById(id);
        return true;
    }

    
    

}
