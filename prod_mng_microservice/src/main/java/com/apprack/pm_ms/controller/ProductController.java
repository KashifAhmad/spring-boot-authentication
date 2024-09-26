package com.apprack.pm_ms.controller;

import ch.qos.logback.classic.Logger;
import com.apprack.pm_ms.model.ApiResponse;
import com.apprack.pm_ms.model.Category;
import com.apprack.pm_ms.model.Products;
import com.apprack.pm_ms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    Logger logger;

    @PostMapping("/add_product")
    public ResponseEntity<ApiResponse<Products>> addProduct(@RequestBody Products products) {
        ApiResponse<Products> response = productService.addProduct(products);
        HttpStatus status = HttpStatus.valueOf(response.getCode());
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/add_category")
    public ResponseEntity<ApiResponse<Category>> addCategory(@RequestBody Category category) {
        try {
            ApiResponse<Category> response = productService.addCategory(category);
            HttpStatus status = HttpStatus.valueOf(response.getCode());
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            logger.error("Error adding category: " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
