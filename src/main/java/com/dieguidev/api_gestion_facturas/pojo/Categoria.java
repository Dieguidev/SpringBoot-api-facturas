package com.dieguidev.api_gestion_facturas.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@NamedQuery(name = "Categoria.getAllCategories", query = "SELECT c FROM Categoria c")


@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

}
