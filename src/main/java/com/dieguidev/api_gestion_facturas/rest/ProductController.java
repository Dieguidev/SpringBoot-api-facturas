package com.dieguidev.api_gestion_facturas.rest;

import com.dieguidev.api_gestion_facturas.constantes.FacturaConstantes;
import com.dieguidev.api_gestion_facturas.service.ProductService;
import com.dieguidev.api_gestion_facturas.util.FacturaUtils;
import com.dieguidev.api_gestion_facturas.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return productService.addProduct(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseentity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<List<ProductWrapper>> getProducts(@RequestParam(required = false) String query) {
        try {
            if (query == null || query.isEmpty()){
                return productService.getAllProducts();
            }
            return productService.getAllProducts2();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @PutMapping
    public ResponseEntity<String> updateCategory(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return productService.updateProduct(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseentity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
        try {
            return productService.deleteProduct(productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseentity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/activate/{productId}")
    public ResponseEntity<String> activateProduct(@PathVariable String productId) {
        try {
            return productService.activateProduct(productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseentity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/productsByCategory/{categoryId}")
    public ResponseEntity<List<ProductWrapper>> productsByCategory(@PathVariable String categoryId) {
        try {
            return productService.productsByCategory(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductWrapper> getProductById(@PathVariable String id) {
        try {
            return productService.getProductById(Integer.parseInt(id) );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<ProductWrapper>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
