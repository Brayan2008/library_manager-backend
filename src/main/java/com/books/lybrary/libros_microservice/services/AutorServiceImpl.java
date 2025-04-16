package com.books.lybrary.libros_microservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.lybrary.libros_microservice.dto.AutorRequest;
import com.books.lybrary.libros_microservice.dto.AutorResponse;
import com.books.lybrary.libros_microservice.model.Autor;
import com.books.lybrary.libros_microservice.repository.AutorRepositorio;
import com.books.lybrary.libros_microservice.repository.NationRepositorio;
import com.books.lybrary.libros_microservice.services.interfaces.AutorService;

@Service
public class AutorServiceImpl implements AutorService{
    
    @Autowired
    AutorRepositorio autorRepositorio;

    @Autowired
    NationRepositorio nationRepositorio;

    @Override
    public List<AutorResponse> getAutor() {  
        return autorRepositorio.findAll().stream().map(AutorServiceImpl::convertirAutor).toList();
    }

    @Override
    public AutorResponse getAutorbyID(int id) {
        return autorRepositorio.existsById(id) ? convertirAutor(autorRepositorio.findById(id).get()):null; 
    }

    @Override
    public AutorResponse saveAutor(AutorRequest autor) {
        if (autorRepositorio.existsById(autor.cod_autor()) || autor.cod_autor()==0 || autor.nombre_autor()==null) {
            return null;
        }
        Autor pre = new Autor();
        pre.setCod_autor(autor.cod_autor());
        pre.setNombre_autor(autor.nombre_autor());
        if (nationRepositorio.existsById(autor.id_nation())) {
            pre.setNation_id(nationRepositorio.findById(autor.id_nation()).get());
        } else if (autor.id_nation() == 0){ 
            pre.setNation_id(null);
        } else {
            return null;
        }

        return convertirAutor(autorRepositorio.save(pre));
    }

    @Override
    public AutorResponse updateAutor(int id,AutorRequest autor) {
        if (!autorRepositorio.existsById(id) || autor.nombre_autor() == null) {
            return null;
        }
        Autor pre = new Autor();
        pre.setNombre_autor(autor.nombre_autor());
        if (nationRepositorio.existsById(autor.id_nation())) {
            pre.setNation_id(nationRepositorio.findById(autor.id_nation()).get());
        } else if(autor.id_nation() == 0) {
            pre.setNation_id(null);
        } else {
            return null;
        }
        return convertirAutor(autorRepositorio.save(pre));
    }

    @Override
    public AutorResponse patchAutor(int id, AutorRequest autor) {
        if (!autorRepositorio.existsById(id)) {
            return null;
        }
        Autor pre = autorRepositorio.findById(id).get();
        if (autor.nombre_autor()!=null) {
            pre.setNombre_autor(autor.nombre_autor());
        } 
        if (nationRepositorio.existsById(autor.id_nation())) {
            pre.setNation_id(nationRepositorio.findById(autor.id_nation()).get());
        } else if (autor.id_nation() == 0){
            pre.setNation_id(null);
        } else {
            return null;
        }
        return convertirAutor(autorRepositorio.save(pre));

    }

    @Override
    public boolean deleteAutor(int id) {
        if(!autorRepositorio.existsById(id)) return false;
        autorRepositorio.deleteById(id);
        return true;
    }

    private static AutorResponse convertirAutor(Autor autor) {
        return new AutorResponse(autor.getNombre_autor(), 
                                autor.getNation_id() == null ? null : autor.getNation_id().getName_nation(),
                                autor.getCod_autor());
    }
    
}
