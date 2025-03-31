package com.books.lybrary.libros_microservice.services;

import java.util.List;

import com.books.lybrary.libros_microservice.dto.EditorialRequest;
import com.books.lybrary.libros_microservice.dto.EditorialResponse;
import com.books.lybrary.libros_microservice.dto.LibroResponse;
import com.books.lybrary.libros_microservice.model.Editorial;

public interface EditorialService {
    public List<EditorialResponse> getEditoriales();
    public List<LibroResponse> getLibros(long id);
    public EditorialResponse getEditorialById(long id);
    public Editorial saveEditorial(EditorialRequest editorial);
    public Editorial updateEditorial(EditorialRequest editorial);
    public Editorial patchEditorial(EditorialRequest editorial);
    public boolean deleteEditorial(long id);
}
