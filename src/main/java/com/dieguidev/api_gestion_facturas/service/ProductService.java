package com.dieguidev.api_gestion_facturas.service;

import com.dieguidev.api_gestion_facturas.pojo.Product;
import com.dieguidev.api_gestion_facturas.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ResponseEntity<String> addProduct(Map<String, String> requestMap);

    ResponseEntity<List<ProductWrapper>> getAllProducts();

    ResponseEntity<List<ProductWrapper>> getAllProducts2();

    ResponseEntity<String> updateProduct(Map<String, String> requestMap);

    ResponseEntity<String> deleteProduct(String productId);

    ResponseEntity<String> activateProduct(String productId);

    ResponseEntity<List<ProductWrapper>> productsByCategory(String categoryId);

    ResponseEntity<ProductWrapper> getProductById(Integer id);
}
