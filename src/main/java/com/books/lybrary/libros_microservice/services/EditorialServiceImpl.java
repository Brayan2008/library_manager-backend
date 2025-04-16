package com.books.lybrary.libros_microservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.lybrary.libros_microservice.dto.EditorialRequest;
import com.books.lybrary.libros_microservice.dto.EditorialResponse;
import com.books.lybrary.libros_microservice.dto.LibroResponse;
import com.books.lybrary.libros_microservice.model.Editorial;
import com.books.lybrary.libros_microservice.repository.EditorialRepositorio;
import com.books.lybrary.libros_microservice.services.interfaces.EditorialService;

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
                                                .map(LibroResponse::toBookResponse)
                                                .toList();
    }

    
    @Override
    public Editorial saveEditorial(EditorialRequest editorial) {
        if (editorialRepositorio.existsById(editorial.id()) || editorial.id()==0) {
            return null;
        }      
        Editorial pre = new Editorial();
        pre.setId_editorial(editorial.id());
        pre.setNombre_editorial(editorial.nombre_editorial());
        return editorialRepositorio.save(pre);
    }
    
    @Override
    public Editorial updateEditorial(long id,EditorialRequest editorial) {
        if (editorial.nombre_editorial() == null || id == 0) return null;
      
        Editorial pre  = editorialRepositorio.findById(id).get();
        pre.setNombre_editorial(editorial.nombre_editorial());
        return editorialRepositorio.save(pre);
    }
    
    @Override
    public Editorial patchEditorial(long id, EditorialRequest editorial) {
        if (!editorialRepositorio.existsById(id)) return null;
        
        var patch = editorialRepositorio.findById(id).get();
        if (editorial.nombre_editorial() !=null) {
            patch.setNombre_editorial(editorial.nombre_editorial());
            return editorialRepositorio.save(patch);
        }
        return null;
    }

    @Override
    public boolean deleteEditorial(long id) {
        if (!editorialRepositorio.existsById(id)) return false;
       
        editorialRepositorio.deleteById(id);
        return true;
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
