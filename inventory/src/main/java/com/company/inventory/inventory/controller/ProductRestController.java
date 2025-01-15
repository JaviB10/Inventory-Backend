package com.company.inventory.inventory.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.inventory.inventory.model.Product;
import com.company.inventory.inventory.response.ProductResponseRest;
import com.company.inventory.inventory.services.IProductService;
import com.company.inventory.inventory.utils.Util;

import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {

    @Autowired
    private IProductService productService;

    /**
     * 
     * @param picture
     * @param name
     * @param price
     * @param amount
     * @param categoryId
     * @return
     * @throws IOException
     */
    @PostMapping("/products")
    public ResponseEntity<ProductResponseRest> save(
        @RequestParam MultipartFile picture, 
        @RequestParam String name, 
        @RequestParam int price, 
        @RequestParam int amount,
        @RequestParam Long categoryId) throws IOException {
        
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setAmount(amount);
            product.setPicture(Util.compressZLib(picture.getBytes()));

            ResponseEntity<ProductResponseRest> response = productService.save(product, categoryId);
            return response;
    }
    
}
