package com.dieguidev.api_gestion_facturas.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FacturaUtils {
    private FacturaUtils() {
    }

    public static ResponseEntity<String> getResponseentity(String message, HttpStatus httpStatus) {
        return new ResponseEntity<String>("Mensaje : " + message, httpStatus);
    }
}
