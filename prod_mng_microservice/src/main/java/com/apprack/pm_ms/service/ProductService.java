package com.apprack.pm_ms.service;

import com.apprack.pm_ms.model.ApiResponse;
import com.apprack.pm_ms.model.Category;
import com.apprack.pm_ms.model.Products;
import com.apprack.pm_ms.repository.CategoryRepository;
import com.apprack.pm_ms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.apprack.pm_ms.contants.HttpResponseCodes.SUCCESS_CODE;
import static com.apprack.pm_ms.contants.HttpResponseCodes.UNAUTHORIZED_CODE;
import static com.apprack.pm_ms.contants.HttpResponseMessages.SUCCESS_MESSAGE;
import static com.apprack.pm_ms.contants.HttpResponseMessages.USERNAME_ALREADY_EXISTS;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ApiResponse<Products> addProduct(Products products) {
        Products existingProduct = productRepository.findByProductName(products.getProductName());
        if (existingProduct != null) {
            return ApiResponse.error(UNAUTHORIZED_CODE, USERNAME_ALREADY_EXISTS);
        }

        // Save the new product
        Products savedProduct = productRepository.save(products);
        return ApiResponse.success(SUCCESS_CODE, savedProduct, SUCCESS_MESSAGE);
    }

    public ApiResponse<Category> addCategory(Category category) {
        System.out.println("Received_category: " + category.getCategoryName()); // Debug log
        Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (existingCategory != null) {
            return ApiResponse.error(UNAUTHORIZED_CODE, USERNAME_ALREADY_EXISTS);
        }

        // Save the new category
        Category savedCategory = categoryRepository.save(category);
        return ApiResponse.success(SUCCESS_CODE, savedCategory, SUCCESS_MESSAGE);
    }

    public ApiResponse<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ApiResponse.success(SUCCESS_CODE, categories, SUCCESS_MESSAGE);
    }


}
