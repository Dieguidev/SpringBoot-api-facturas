package com.dieguidev.api_gestion_facturas.dao;

import com.dieguidev.api_gestion_facturas.pojo.Product;
import com.dieguidev.api_gestion_facturas.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {

    List <ProductWrapper> getAllProducts();

    List<Product> findByCategoria_Id(Integer categoryId);

    ProductWrapper getProductById(@Param("id") Integer id);
}
