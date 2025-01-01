package com.dieguidev.api_gestion_facturas.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    //solo va a listar las categoria cuando se lo pida
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria-fk", nullable = false)
    private Categoria categoria;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "precio")
    private Integer price;

    @Column(name = "estado")
    private String status;
}
