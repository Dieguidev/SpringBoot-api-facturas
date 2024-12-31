package com.dieguidev.api_gestion_facturas.service.impl;

import com.dieguidev.api_gestion_facturas.constantes.FacturaConstantes;
import com.dieguidev.api_gestion_facturas.dao.CategoriaDAO;
import com.dieguidev.api_gestion_facturas.pojo.Categoria;
import com.dieguidev.api_gestion_facturas.pojo.User;
import com.dieguidev.api_gestion_facturas.security.jwt.JwtFilter;
import com.dieguidev.api_gestion_facturas.service.CategoriaService;
import com.dieguidev.api_gestion_facturas.util.FacturaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if(validateCategoryMap(requestMap, false)) {
                    categoriaDAO.save(getCategoryFromMap(requestMap, false));
                    return FacturaUtils.getResponseentity("Categoria agregada con éxito", HttpStatus.OK);
                }

            } else {
                return FacturaUtils.getResponseentity(FacturaConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseentity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("nombre") && validateId) {
            return true;
        }
        if (!validateId) {
            return true;
        }
        return false;
    }

    private Categoria getCategoryFromMap(Map<String, String> requestMap, boolean isAdd){
        Categoria categoria = new Categoria();
        if(isAdd) {
            categoria.setId(Integer.parseInt(requestMap.get("id")));
        }
        categoria.setNombre(requestMap.get("name"));
        return categoria;
    }
}
