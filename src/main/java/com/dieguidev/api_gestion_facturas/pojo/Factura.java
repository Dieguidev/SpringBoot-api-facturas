package com.dieguidev.api_gestion_facturas.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "nombre")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "numeroContacto")
    private String contactNumber;

    @Column(name = "metodoPago")
    private String paymentMethod;

    @Column(name = "total")
    private Integer total;

    @Column(name = "productoDetalles")
    private String productDetails;

    @Column(name = "createdBy")
    private String createdBy;



}
