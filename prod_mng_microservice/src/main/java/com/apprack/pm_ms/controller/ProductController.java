package com.apprack.pm_ms.controller;

import ch.qos.logback.classic.Logger;
import com.apprack.auth.service.UserService;
import com.apprack.pm_ms.model.ApiResponse;
import com.apprack.pm_ms.model.Category;
import com.apprack.pm_ms.model.Products;
import com.apprack.pm_ms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    Logger logger;

    @Autowired
    private UserService authServiceClient;

    @PostMapping("/add_product")
    public ResponseEntity<ApiResponse<Products>> addProduct(@RequestBody Products products) {
        ApiResponse<Products> response = productService.addProduct(products);
        HttpStatus status = HttpStatus.valueOf(response.getCode());
        return new ResponseEntity<>(response, status);
    }


    @PostMapping("/add_category")
    public ResponseEntity<ApiResponse<Category>> addCategory(@RequestHeader("Authorization") String tokenWithBearer, @RequestBody Category category) {
        try {
//             Extract the actual JWT token from the Bearer token
//            String token = tokenWithBearer.startsWith("Bearer ") ? tokenWithBearer.substring(7) : tokenWithBearer;
//
//            // Check if the user exists in the auth service
//            boolean userExists = authServiceClient.checkUserExists(token);
//
//
//            if (!userExists) {
//                // Return 401 Unauthorized if the user doesn't exist
//                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//            }

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


}
