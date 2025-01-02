package com.dieguidev.api_gestion_facturas.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface FacturaService {

    ResponseEntity<String> generateReport(Map<String, Object> requestMap);
}
