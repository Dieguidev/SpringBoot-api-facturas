package com.dieguidev.api_gestion_facturas.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
@NamedQuery(name = "User.getAllUsers", query = "SELECT new com.dieguidev.api_gestion_facturas.wrapper.UserWrapper(u.id, u.nombre, u.email, u.numeroContacto, u.status) FROM User u WHERE u.role='user'")
@NamedQuery(name = "User.updateStatus", query = "UPDATE User u set u.status=:status WHERE u.id=:id")
@NamedQuery(name = "User.getAllAdmins", query = "SELECT u.email FROM User u where u.role='admin'")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "numeroDeContacto")
    private String numeroContacto;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;
}
