package com.dieguidev.api_gestion_facturas.service.impl;

import com.dieguidev.api_gestion_facturas.constantes.FacturaConstantes;
import com.dieguidev.api_gestion_facturas.dao.UserDAO;
import com.dieguidev.api_gestion_facturas.pojo.User;
import com.dieguidev.api_gestion_facturas.service.UserService;
import com.dieguidev.api_gestion_facturas.util.FacturaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public ResponseEntity<String> singUp(Map<String, String> requestMap) {
        log.info("Registro interno de un usuario {}", requestMap);
        try {
            if(validateSignUpMap(requestMap)){
                User user = userDAO.findByEmail(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDAO.save(getUserFromMap(requestMap));
                    return FacturaUtils.getResponseentity("Usuario registrado correctamente", HttpStatus.CREATED);
                } else {
                    return FacturaUtils.getResponseentity("El usuario con ese email ya existe", HttpStatus.BAD_REQUEST);
                }
            } else {
                return FacturaUtils.getResponseentity("Por favor, proporcione los datos necesarios", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseentity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if(requestMap.containsKey("nombre") && requestMap.containsKey("numeroDeContacto") && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setNombre(requestMap.get("nombre"));
        user.setEmail(requestMap.get("email"));
        user.setNumeroContacto(requestMap.get("numeroDeContacto"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");

        return user;
    }
}
