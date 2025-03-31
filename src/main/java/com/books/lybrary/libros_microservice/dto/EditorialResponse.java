package com.books.lybrary.libros_microservice.dto;

import java.util.List;

public record EditorialResponse(long id, String nombre_editorial, List<LibroResponse> libros) {}
