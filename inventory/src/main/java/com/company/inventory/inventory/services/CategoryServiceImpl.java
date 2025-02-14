package com.company.inventory.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.inventory.dao.ICategoryDao;
import com.company.inventory.inventory.model.Category;
import com.company.inventory.inventory.response.CategoryResponseRest;

@Service
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private ICategoryDao categoryDao;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> search() {
        
        CategoryResponseRest response = new CategoryResponseRest();

        try {

            List<Category> category = (List<Category>) categoryDao.findAll();

            response.getCategoryResponse().setCategory(category);
            response.setMetadata("Respuesta ok", "00",  "Respuesta exitosa");

        } catch (Exception e) {

            response.setMetadata("Respuesta error", "-1",  "Error al consultar");
            e.getStackTrace();

            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> searchById(Long id) {

        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();

        try {
            Optional<Category> category = categoryDao.findById(id);
            
            if (category.isPresent()) {

                list.add(category.get());
                response.getCategoryResponse().setCategory(list);
                response.setMetadata("Respuesta ok", "00",  "Categoria encontrada exitosamente");

            } else {

                response.setMetadata("Respuesta error", "-2",  "Categoria no encontrada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);

            }

        } catch (Exception e) {
            
            response.setMetadata("Respuesta error", "-1",  "Error al consultar");
            e.getStackTrace();

            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @SuppressWarnings("unused")
    @Override
    @Transactional()
    public ResponseEntity<CategoryResponseRest> save(Category category) {

        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();

        try {
            Category savedCategory = categoryDao.save(category);

            if (savedCategory != null) {

                list.add(savedCategory);
                response.getCategoryResponse().setCategory(list);
                response.setMetadata("Respuesta ok", "00",  "Categoria creada exitosamente");
                
            } else {
                response.setMetadata("Respuesta error", "-3", "Categoria no creada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            
            response.setMetadata("Respuesta error", "-1",  "Error al consultar");
            e.getStackTrace();

            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.CREATED);
    }

    @SuppressWarnings("unused")
    @Override
    @Transactional()
    public ResponseEntity<CategoryResponseRest> update(Category category, Long id) {
        
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();

        try {
            
            Optional<Category> searchCategory = categoryDao.findById(id);

            if (searchCategory.isPresent()) {

                searchCategory.get().setName(category.getName());
                searchCategory.get().setDescription(category.getDescription());

                Category updatedCategory = categoryDao.save(searchCategory.get());

                if (updatedCategory != null) {
                    
                    list.add(updatedCategory);
                    response.getCategoryResponse().setCategory(list);
                    response.setMetadata("Respuesta ok", "00",  "Categoria actualizada exitosamente");

                } else {

                    response.setMetadata("Respuesta error", "-3", "Categoria no actualizada");
                    return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);

                }

            } else {

                response.setMetadata("Respuesta error", "-2",  "Categoria no encontrada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);

            }

        } catch (Exception e) {
            
            response.setMetadata("Respuesta error", "-1",  "Error al consultar");
            e.getStackTrace();

            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.ACCEPTED);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> deleteById(Long id) {
        
        CategoryResponseRest response = new CategoryResponseRest();
        
        try {

            categoryDao.deleteById(id);
            response.setMetadata("Respuesta ok", "00",  "Categoria eliminada exitosamente");

        } catch (Exception e) {

            response.setMetadata("Respuesta error", "-1",  "Error al consultar");
            e.getStackTrace();

            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }
}
