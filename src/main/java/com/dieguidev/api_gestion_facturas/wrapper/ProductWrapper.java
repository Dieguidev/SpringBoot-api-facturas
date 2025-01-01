package com.dieguidev.api_gestion_facturas.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWrapper {
    private Integer id;
    private String nombre;
    private String description;
    private Integer price;
    private String status;
    private Integer categoryId;
    private String categoryName;

    public ProductWrapper(Integer id, String nombre, String description, Integer price) {
        this.id = id;
        this.nombre = nombre;
        this.description = description;
        this.price = price;
    }

}
