package com.dieguidev.api_gestion_facturas.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ProductService {

    ResponseEntity<String> addProduct(Map<String, String> requestMap);
}
