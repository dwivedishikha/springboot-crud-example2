package com.java.service;

import com.java.entity.Product;
import com.java.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // save product to repository
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    // save product list to repository
    public List<Product> saveAllProducts(List<Product> productlist){
        return productRepository.saveAll(productlist);
    }

    // get all the product from repository
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    // get the product by id from repository
    public Optional<Product> getProductbyId(int id){
        return productRepository.findAllById(Collections.singleton(id)).stream().findFirst();
    }

    // get product by name from repository
    public List<Product> getProducts(String name){
        return Collections.singletonList(productRepository.findByName(name));
    }

    // update product
    public Product updateProduct(Product product) {
        Optional<Product> existingProduct = productRepository.findById(product.getId());
        if (existingProduct.isPresent()) {
            // Update fields and save the product
            Product updatedProduct = existingProduct.get();
            updatedProduct.setName(product.getName());
            updatedProduct.setQuantity(product.getQuantity());
            updatedProduct.setPrice(product.getPrice());
            return productRepository.save(updatedProduct);  // Save updated product
        }
        return null;  // Return null if product not found
    }

    // delete product
    public boolean deleteProduct(int id) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            productRepository.deleteById(id);  // Delete the product by ID
            return true;  // Return true if deletion is successful
        } else {
            return false;  // Return false if product is not found
        }
    }

}
