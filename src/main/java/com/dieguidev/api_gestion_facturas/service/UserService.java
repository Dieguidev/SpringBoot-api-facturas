package com.dieguidev.api_gestion_facturas.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {
    ResponseEntity<String> singUp(Map<String, String> requestMap);

    ResponseEntity<String> login(Map<String, String> requestMap);

}
