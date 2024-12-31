package com.dieguidev.api_gestion_facturas.dao;

import com.dieguidev.api_gestion_facturas.pojo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaDAO extends JpaRepository<Categoria, Integer> {

    List<Categoria> getAllCategories();
}
