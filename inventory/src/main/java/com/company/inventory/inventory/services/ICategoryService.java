package com.company.inventory.inventory.services;

import org.springframework.http.ResponseEntity;

import com.company.inventory.inventory.response.CategoryResponseRest;

public interface ICategoryService {

    public ResponseEntity<CategoryResponseRest> search();
}
