package com.apprack.pm_ms.controller;

import ch.qos.logback.classic.Logger;
import com.apprack.auth.service.UserService;
import com.apprack.pm_ms.model.ApiResponse;
import com.apprack.pm_ms.model.Category;
import com.apprack.pm_ms.model.Products;
import com.apprack.pm_ms.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    Logger logger;

    @Autowired
    private UserService authServiceClient;

    @PostMapping(value = "/add_product", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse<Products>> addProduct(
            @RequestPart("product") String productJson, // Change type to String for deserialization
            @RequestParam("images") MultipartFile[] images) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Ensure the module is registered
        Products products = mapper.readValue(productJson, Products.class);
        try {
            // Handle image saving and get paths
            List<String> imagePaths = productService.saveProductImages(images);
            products.setImagePaths(imagePaths); // Assuming there's a field in Products to hold multiple paths

            // Save product details
            ApiResponse<Products> response = productService.addProduct(products);
            HttpStatus status = HttpStatus.valueOf(response.getCode());
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update_product/{productId}", consumes = "application/json")
    public ResponseEntity<ApiResponse<Products>> updateProduct(
            @PathVariable Long productId,
            @RequestBody Products updatedProduct) {
        ApiResponse<Products> response = productService.updateProduct(productId, updatedProduct);
        HttpStatus status = HttpStatus.valueOf(response.getCode());
        return new ResponseEntity<>(response, status);
    }


    @GetMapping("/get_product/{productId}")
    public ResponseEntity<ApiResponse<Products>> getProductById(@PathVariable Long productId) {
        ApiResponse<Products> response = productService.getProductById(productId);
        HttpStatus status = HttpStatus.valueOf(response.getCode());
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/add_category")
    public ResponseEntity<ApiResponse<Category>> addCategory(@RequestHeader("Authorization") String tokenWithBearer, @RequestBody Category category) {
        try {
            // Proceed with adding the category
            ApiResponse<Category> response = productService.addCategory(category);
            HttpStatus status = HttpStatus.valueOf(response.getCode());

            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            logger.error("Error adding category: " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get_all_categories")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {

        try {
            ApiResponse<List<Category>> response = productService.getAllCategories();
            HttpStatus status = HttpStatus.valueOf(response.getCode());
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete_category/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@RequestHeader("Authorization") String tokenWithBearer, @PathVariable Long id) {
        try {
            // Optional: Validate the token and user existence if needed (like you did in add_category)

            ApiResponse<String> response = productService.deleteCategory(id);
            HttpStatus status = HttpStatus.valueOf(response.getCode());
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            logger.error("Error deleting category: " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get_all_products")
    public ResponseEntity<ApiResponse<List<Products>>> getAllProducts() {
        try {
            ApiResponse<List<Products>> response = productService.getAllProducts();
            HttpStatus status = HttpStatus.valueOf(response.getCode());
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            logger.error("Error fetching products: " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete_product/{categoryId}/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductFromCategory(
            @RequestHeader("Authorization") String tokenWithBearer,
            @PathVariable Long categoryId,
            @PathVariable Long productId
    ) {
        try {

            ApiResponse<Void> response = productService.deleteProductFromCategory(categoryId, productId);
            HttpStatus status = HttpStatus.valueOf(response.getCode());
            return new ResponseEntity<>(response, status);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/restock_product/{productId}")
    public ResponseEntity<ApiResponse<String>> restockProduct(
            @PathVariable Long productId,
            @RequestParam int quantityToAdd) {
        try {
            ApiResponse<String> response = productService.restockProduct(productId, quantityToAdd);
            HttpStatus status = HttpStatus.valueOf(response.getCode());
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
