package com.dieguidev.api_gestion_facturas.service.impl;

import com.dieguidev.api_gestion_facturas.constantes.FacturaConstantes;
import com.dieguidev.api_gestion_facturas.dao.ProductDAO;
import com.dieguidev.api_gestion_facturas.pojo.Categoria;
import com.dieguidev.api_gestion_facturas.pojo.Product;
import com.dieguidev.api_gestion_facturas.security.jwt.JwtFilter;
import com.dieguidev.api_gestion_facturas.service.ProductService;
import com.dieguidev.api_gestion_facturas.util.FacturaUtils;
import com.dieguidev.api_gestion_facturas.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductSeviceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, false)) {
                    productDAO.save(getProductFromMap(requestMap, false));
                    return FacturaUtils.getResponseentity("Producto agregado con éxito", HttpStatus.CREATED);
                }
                return FacturaUtils.getResponseentity(FacturaConstantes.INVALID_DAT, HttpStatus.BAD_REQUEST);
            } else {
                return FacturaUtils.getResponseentity(FacturaConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseentity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProducts() {
        System.out.println("siiiiiiii");
        try {
                List<ProductWrapper> products = productDAO.getAllProducts();
                return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProducts2() {
        try {
            if (jwtFilter.isAdmin()) {
                List<ProductWrapper> products = productDAO.findAll().stream().map(product -> {
                    ProductWrapper productWrapper = new ProductWrapper();
                    productWrapper.setId(product.getId());
                    productWrapper.setNombre(product.getNombre());
                    productWrapper.setDescription(product.getDescription());
                    productWrapper.setPrice(product.getPrice());
                    productWrapper.setCategoryId(product.getCategoria().getId());
                    productWrapper.setCategoryName(product.getCategoria().getNombre());
                    return productWrapper;
                }).toList();
                return new ResponseEntity<>(products, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, true)) {
                    Optional<Product> productDB = productDAO.findById(Integer.parseInt(requestMap.get("id")));
                    System.out.println(productDB);
                    if (!productDB.isEmpty()) {
                        Product product = getProductFromMap(requestMap, true);
                        product.setStatus(productDB.get().getStatus());
                        productDAO.save(product);
                        return FacturaUtils.getResponseentity("Producto actualizada con éxito", HttpStatus.OK);
                    } else {
                        return FacturaUtils.getResponseentity("El producto con ese Id no existe", HttpStatus.NOT_FOUND);
                    }
                }
            } else {
                return FacturaUtils.getResponseentity(FacturaConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseentity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(String productId) {
        try {
            if (jwtFilter.isAdmin()) {

                Optional<Product> productDB = productDAO.findById(Integer.parseInt(productId));

                if (productDB.isPresent() && "true".equalsIgnoreCase(productDB.get().getStatus())) {
                    productDB.get().setStatus("false");
                    productDAO.save(productDB.get());
                    return FacturaUtils.getResponseentity("Producto eliminado con éxito", HttpStatus.OK);
                } else {
                    return FacturaUtils.getResponseentity("El producto con ese Id no existe", HttpStatus.NOT_FOUND);
                }

            } else {
                return FacturaUtils.getResponseentity(FacturaConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseentity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> activateProduct(String productId) {
        try {
            if (jwtFilter.isAdmin()) {

                Optional<Product> productDB = productDAO.findById(Integer.parseInt(productId));
                if (productDB.isPresent() && "true".equalsIgnoreCase(productDB.get().getStatus())) {
                    return FacturaUtils.getResponseentity("Producto ya se encuentra activado", HttpStatus.BAD_REQUEST);
                }
                if (productDB.isPresent()) {
                    productDB.get().setStatus("true");
                    productDAO.save(productDB.get());
                    return FacturaUtils.getResponseentity("Producto activado con éxito", HttpStatus.OK);
                } else {
                    return FacturaUtils.getResponseentity("El producto con ese Id no existe", HttpStatus.NOT_FOUND);
                }

            } else {
                return FacturaUtils.getResponseentity(FacturaConstantes.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseentity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> productsByCategory(String categoryId) {
        try {
            if (jwtFilter.isAdmin()) {
                List<ProductWrapper> products = productDAO.findByCategoria_Id(Integer.parseInt(categoryId)).stream().map(product -> {
                    ProductWrapper productWrapper = new ProductWrapper();
                    productWrapper.setId(product.getId());
                    productWrapper.setNombre(product.getNombre());
                    productWrapper.setDescription(product.getDescription());
                    productWrapper.setPrice(product.getPrice());
                    productWrapper.setCategoryId(product.getCategoria().getId());
                    productWrapper.setCategoryName(product.getCategoria().getNombre());
                    return productWrapper;
                }).toList();
                return new ResponseEntity<>(products, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try {
            return new ResponseEntity<>(productDAO.getProductById(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();
        if (isAdd) {
            product.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            product.setStatus("true");
        }
        product.setCategoria(categoria);
        product.setNombre(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));

        return product;
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            }
            if (!validateId) {
                return true;
            }
        }
        return false;
    }
}
