package com.company.inventory.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.inventory.inventory.model.Category;
import com.company.inventory.inventory.response.CategoryResponseRest;
import com.company.inventory.inventory.services.ICategoryService;

@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * get all the categories
     * @return
     */
    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseRest> searchCatergories() {
        
        ResponseEntity<CategoryResponseRest> response = categoryService.search();
        return response;
    }

    /**
     * get categories by id
     * @param id
     * @return
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> searchCatergories(@PathVariable Long id) {
        
        ResponseEntity<CategoryResponseRest> response = categoryService.searchById(id);
        return response;
    }

    /**
     * save categories
     * @param category
     * @return
     */
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category) {

        ResponseEntity<CategoryResponseRest> response = categoryService.save(category);
        return response;
    }

    /**
     * updated categories
     * @param category
     * @param id
     * @return
     */
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> update(@RequestBody Category category, @PathVariable Long id) {

        ResponseEntity<CategoryResponseRest> response = categoryService.update(category, id);
        return response;
    }
}