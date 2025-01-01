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
    private Integer categoryId;
    private String categoryName;

}
