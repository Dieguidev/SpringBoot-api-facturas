package com.dieguidev.api_gestion_facturas.dao;

import com.dieguidev.api_gestion_facturas.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {

}
