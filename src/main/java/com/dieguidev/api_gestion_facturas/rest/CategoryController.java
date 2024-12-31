package com.dieguidev.api_gestion_facturas.rest;

import com.dieguidev.api_gestion_facturas.constantes.FacturaConstantes;
import com.dieguidev.api_gestion_facturas.service.CategoriaService;
import com.dieguidev.api_gestion_facturas.util.FacturaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestBody(required = true) Map<String, String> requestMap){
        try {
            return categoriaService.addCategory(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseentity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
