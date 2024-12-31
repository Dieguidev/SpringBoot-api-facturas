package com.dieguidev.api_gestion_facturas.service;

import com.dieguidev.api_gestion_facturas.pojo.Categoria;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoriaService {
    ResponseEntity<String> addCategory(Map<String, String> requestMap);

    ResponseEntity<List<Categoria>> getAllCategories(String valueFilter);
}
