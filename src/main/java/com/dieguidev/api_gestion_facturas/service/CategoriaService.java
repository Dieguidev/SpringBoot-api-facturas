package com.dieguidev.api_gestion_facturas.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CategoriaService {
    ResponseEntity<String> addCategory(Map<String, String> requestMap);
}
