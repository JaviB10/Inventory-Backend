package com.company.inventory.inventory.services;

import org.springframework.http.ResponseEntity;

import com.company.inventory.inventory.model.Product;
import com.company.inventory.inventory.response.ProductResponseRest;

public interface IProductService {

    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
    public ResponseEntity<ProductResponseRest> searchById(Long id);
    public ResponseEntity<ProductResponseRest> searchByName(String name);
    public ResponseEntity<ProductResponseRest> deleteById(Long id);
}
