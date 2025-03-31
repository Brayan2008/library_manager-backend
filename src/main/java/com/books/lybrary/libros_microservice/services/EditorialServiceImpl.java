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
                                    .map(EditorialServiceImpl::convertirEditorial)
                                    .toList();
    }

    
    @Override
    public EditorialResponse getEditorialById(long id) {
        var editorial = editorialRepositorio.findById(id);
        return editorial.isPresent() ? convertirEditorial(editorial.get()):null;

    }

    
    
    @Deprecated 
    @Override
    public List<LibroResponse> getLibros(long id) {
        return !editorialRepositorio.existsById(id) ? null:editorialRepositorio.findById(id).get()
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
        if (editorialRepositorio.existsById(editorial.getId_editorial())) {
            return null;
        }
        return editorialRepositorio.save(editorial);
    }
    
    @Override
    public Editorial updateEditorial(Editorial editorial) {
        long id = editorial.getId_editorial();
        if (!editorialRepositorio.existsById(id)) {
            return null;
        }
        editorialRepositorio.findById(id).get().setNombre_editorial(editorial.getNombre_editorial());
        return editorial;
    }
    
    @Override
    public Editorial patchEditorial(Editorial editorial) {
        long id = editorial.getId_editorial();
        if (!editorialRepositorio.existsById(id)) {
            return null;
        }
        Editorial patch = editorialRepositorio.findById(id).get();
        if (editorial.getNombre_editorial() !=null) {
            patch.setNombre_editorial(editorial.getNombre_editorial());
        }
        
        return editorial;
    }

    @Override
    public boolean deleteEditorial(long id) {
        if (editorialRepositorio.existsById(id)) {
            editorialRepositorio.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Metodo para convertir una editorial en una editorialDTO, para evitar la recursividad
     * @param ed Editorial
     * @return EditorialResponse (con el nombre de la editorial en null)
     */
    public static EditorialResponse convertirEditorial(Editorial ed) {
        return new EditorialResponse(ed.getId_editorial()
                                    ,ed.getNombre_editorial()
                                    ,ed.getLista_Libros().stream()
                                                        .map(LibroResponse::toBookResponse)
                                                        .toList());
    }

}
