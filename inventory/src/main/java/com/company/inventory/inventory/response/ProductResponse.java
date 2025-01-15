package com.company.inventory.inventory.response;

import java.util.List;

import com.company.inventory.inventory.model.Product;

public class ProductResponse {

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
