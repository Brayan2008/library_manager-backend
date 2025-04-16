package com.books.lybrary.libros_microservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.lybrary.libros_microservice.dto.NationResponse;
import com.books.lybrary.libros_microservice.model.Nation;
import com.books.lybrary.libros_microservice.repository.NationRepositorio;
import com.books.lybrary.libros_microservice.services.interfaces.NationService;

@Service
public class NationServiceImpl implements NationService{
    
    @Autowired
    private NationRepositorio nationRepository;

    @Override
    public List<NationResponse> getNations() {
        return nationRepository.findAll().stream().map(NationResponse::convertirNation).toList();
    }

    @Override
    public NationResponse getNationbyID(int id) {
        return nationRepository.findById(id).map(NationResponse::convertirNation).orElse(null);
    }

    @Override
    public NationResponse saveNation(Nation libro) {
        return libro.getId_nation()==0 || nationRepository.existsById(libro.getId_nation()) ? null:NationResponse.convertirNation(nationRepository.save(libro));
    }

    @Override
    public NationResponse updateNation(int id, Nation libro) {
        Nation update_nation = nationRepository.findById(id).orElse(null);
        if (update_nation != null) {
            update_nation.setName_nation(libro.getName_nation());
            return NationResponse.convertirNation(nationRepository.save(update_nation));
        }
        return null;
    }

    @Override
    public NationResponse patchNation(int id, Nation libro) {
        Nation patch_nation = nationRepository.findById(id).orElse(null);
        if (patch_nation != null) {
            if (libro.getName_nation() != null) {
                patch_nation.setName_nation(libro.getName_nation());
            }
            return NationResponse.convertirNation(nationRepository.save(patch_nation));

        }
        return null;
    }

    @Override
    public boolean deleteNation(int id) {
        if (!nationRepository.existsById(id)) return false;
        nationRepository.deleteById(id);
        return true;
    }



}
