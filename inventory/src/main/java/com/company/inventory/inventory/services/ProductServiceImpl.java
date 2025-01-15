package com.company.inventory.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.company.inventory.inventory.dao.ICategoryDao;
import com.company.inventory.inventory.dao.IProductDao;
import com.company.inventory.inventory.model.Category;
import com.company.inventory.inventory.model.Product;
import com.company.inventory.inventory.response.ProductResponseRest;

@Service
public class ProductServiceImpl implements IProductService {

    private ICategoryDao categoryDao;
    private IProductDao productDao;

    public ProductServiceImpl(ICategoryDao categoryDao, IProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @SuppressWarnings("unused")
    @Override
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
        
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();

        try {
            System.out.println("entra aca");
            Optional<Category> category = categoryDao.findById(categoryId);

            System.out.println(category);
            if (category.isPresent()) {
                System.out.println("entra primer if");
                product.setCategory(category.get());
            } else {
                response.setMetadata("Respuesta error", "-1", "Categoria encontrada asociada al producto");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
            }
            System.out.println("llega aca");
            Product productSaved = productDao.save(product);
            System.out.println("pasa esto");
            if (productSaved != null) {
                list.add(productSaved);
                response.setMetadata("Respuesta ok", "00", "Producto guardado");
                response.getProductResponse().setProducts(list);
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.CREATED);
            } else {
                response.setMetadata("Respuesta error", "-1", "Producto no guardado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            
            response.setMetadata("Respuesta error", "-1",  "Error al guardar producto");
            e.getStackTrace();

            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
