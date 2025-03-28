package com.books.lybrary.libros_microservice.services;

import java.util.List;

import com.books.lybrary.libros_microservice.dto.EditorialResponse;
import com.books.lybrary.libros_microservice.dto.LibroResponse;
import com.books.lybrary.libros_microservice.model.Editorial;

public interface EditorialService {
    public List<EditorialResponse> getEditoriales();
    public List<LibroResponse> getLibros(long id);
    public EditorialResponse getEditorialById(long id);
    public Editorial saveEditorial(Editorial editorial);
    public Editorial updateEditorial(Editorial editorial);
    public Editorial patchEditorial(Editorial editorial);
    public boolean deleteEditorial(long id);
}
