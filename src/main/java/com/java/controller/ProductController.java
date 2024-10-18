package com.java.controller;

import com.java.entity.Product;
import com.java.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product productObj =productService.saveProduct(product);
        return ResponseEntity.ok(productObj);
    }

    @PostMapping("/addProductList")
    public ResponseEntity<List<Product>> addProductList(@RequestBody List<Product> products) {
        try {
            productService.saveAllProducts(products);
            return ResponseEntity.ok(products);  // Success case
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Handle failure
        }
    }

    @GetMapping("/AllProducts")
    public ResponseEntity<List<Product>> getAllProduct() {
        try {
            List<Product> products = productService.getAllProduct();

            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();  // HTTP 204 No Content if no products are found
            }

            return ResponseEntity.ok(products);  // HTTP 200 OK with the products
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // HTTP 500 for unexpected errors
        }
    }

    @GetMapping("/productById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        try {
            Optional<Product> product = productService.getProductbyId(id);

            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());  // HTTP 200 OK with the product
            } else {
                return ResponseEntity.notFound().build();  // HTTP 404 Not Found if product not found
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // HTTP 500 for unexpected errors
        }
    }

    @GetMapping("/product/{name}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProducts(name);

            if (products.isEmpty()) {
                return ResponseEntity.notFound().build();  // HTTP 404 if no products found
            } else {
                return ResponseEntity.ok(products);  // HTTP 200 OK with the products list
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // HTTP 500 for unexpected errors
        }
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        if (false) {
            return ResponseEntity.badRequest().build();  // Return 400 if product ID is missing
        }

        Product updatedProduct = productService.updateProduct(product);

        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();  // Return 404 if product was not found
        }

        return ResponseEntity.ok(updatedProduct);  // Return 200 OK with updated product
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        boolean isDeleted = productService.deleteProduct(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();  // Return 204 No Content if deletion is successful
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + id);  // Return 404 if product not found
        }
    }

}
