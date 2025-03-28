package com.books.lybrary.libros_microservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.lybrary.libros_microservice.dto.EditorialResponse;
import com.books.lybrary.libros_microservice.dto.LibroResponse;
import com.books.lybrary.libros_microservice.model.Editorial;
import com.books.lybrary.libros_microservice.repository.EditorialRepositorio;

@Service
public class EditorialServiceImpl implements EditorialService {

    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Override
    public List<EditorialResponse> getEditoriales() {
        if (editorialRepositorio.findAll().isEmpty()) {return null;}

        return editorialRepositorio.findAll().stream()
                                    .map(ed -> new EditorialResponse(ed.getId_editorial(), ed.getNombre_editorial()))
                                    .toList();
    }


    @Override
    public EditorialResponse getEditorialById(long id) {
        var editorial = editorialRepositorio.findById(id);
        
        if (editorial.isEmpty()) {return null;}

        return new EditorialResponse(editorial.get().getId_editorial(),editorial.get().getNombre_editorial());

    }

    
    @Override
    public List<LibroResponse> getLibros(long id) {
        return editorialRepositorio.findById(id).get()
                                                .getLista_Libros()
                                                .stream()
                                                .map(lib->new LibroResponse(lib.getTitulo_libro(), 
                                                                            lib.getId_libro(), 
                                                                            lib.getIsbn_libro(), 
                                                                            lib.getFecha_publicacion_libro(), 
                                                                            lib.getEditorial_id().getNombre_editorial())).toList();
    }

    
    @Override
    public Editorial saveEditorial(Editorial editorial) {
        return editorialRepositorio.save(editorial);
    }
    
    @Override
    public Editorial updateEditorial(Editorial editorial) {
        
        return null;
    }

    @Override
    public Editorial patchEditorial(Editorial editorial) {
        
        return null;
    }

    @Override
    public boolean deleteEditorial(long id) {
        if (editorialRepositorio.existsById(id)) {
            editorialRepositorio.deleteById(id);
            return true;
        }
        return false;
    }
    
}
