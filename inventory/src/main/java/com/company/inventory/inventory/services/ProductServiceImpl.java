package com.company.inventory.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.inventory.dao.ICategoryDao;
import com.company.inventory.inventory.dao.IProductDao;
import com.company.inventory.inventory.model.Category;
import com.company.inventory.inventory.model.Product;
import com.company.inventory.inventory.response.ProductResponseRest;
import com.company.inventory.inventory.utils.Util;

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
    @Transactional()
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
        
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();

        try {

            Optional<Category> category = categoryDao.findById(categoryId);

            if (category.isPresent()) {

                product.setCategory(category.get());
                
            } else {

                response.setMetadata("Respuesta error", "-1", "Categoria encontrada asociada al producto");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
                
            }

            Product productSaved = productDao.save(product);

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

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchById(Long id) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();

        try {
            
            Optional<Product> product = productDao.findById(id);

            if (product.isPresent()) {
                
                byte[] imageDescompressed = Util.decompressZLib(product.get().getPicture());
                product.get().setPicture(imageDescompressed);

                list.add(product.get());

                response.getProductResponse().setProducts(list);
                response.setMetadata("Respuesta ok", "00", "Producto encontrado");

                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.ACCEPTED);

            } else {

                response.setMetadata("Respuesta error", "-1", "Producto no encontrado");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);

            }
        } catch (Exception e) {
            
            response.setMetadata("Respuesta error", "-1",  "Error al buscar producto");
            e.getStackTrace();

            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchByName(String name) {
        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        List<Product> listAux = new ArrayList<>();

        try {
            
            listAux = productDao.findByNameContainingIgnoreCase(name);

            if (listAux.size() > 0) {
                
                listAux.stream().forEach((p) -> {

                    byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
                    p.setPicture(imageDescompressed);
    
                    list.add(p);
                    
                });

                response.getProductResponse().setProducts(list);
                response.setMetadata("Respuesta ok", "00", "Productos encontrados");

                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.ACCEPTED);

            } else {

                response.setMetadata("Respuesta error", "-1", "Productos no encontrados");
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);

            }
        } catch (Exception e) {
            
            response.setMetadata("Respuesta error", "-1",  "Error al buscar productos por nombre");
            e.getStackTrace();

            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> deleteById(Long id) {
        ProductResponseRest response = new ProductResponseRest();

        try {

            Optional<Product> product = productDao.findById(id);

            if (product.isEmpty()) {

                response.setMetadata("Respuesta error", "00", "Producto no encontrado");
    
                return new ResponseEntity<ProductResponseRest>(response, HttpStatus.ACCEPTED);
                
            } 
            
            productDao.deleteById(id);

            response.setMetadata("Respuesta ok", "00", "Producto eliminado");

            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            
            response.setMetadata("Respuesta error", "-1",  "Error al eliminar producto");
            e.getStackTrace();

            return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
