package prod_mng_microservice.service;

import auth_mircroservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prod_mng_microservice.model.ApiResponse;
import prod_mng_microservice.model.Category;
import prod_mng_microservice.model.Products;
import prod_mng_microservice.repository.CategoryRepository;
import prod_mng_microservice.repository.ProductRepository;

import static auth_mircroservice.constants.HttpResponseCodes.*;
import static auth_mircroservice.constants.HttpResponseMessages.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ApiResponse<Products> addProduct(Products products) {
        Products existingProduct = productRepository.findByProductName(products.getName());
        if (existingProduct != null) {
            return ApiResponse.error(UNAUTHORIZED_CODE, USERNAME_ALREADY_EXISTS);
        }

        // Save the new product
        Products savedProduct = productRepository.save(products);
        return ApiResponse.success(SUCCESS_CODE, savedProduct, SUCCESS_MESSAGE);
    }

    public ApiResponse<Category> addCategory(Category category) {
        System.out.println("Received_category: " + category.getName()); // Debug log
        Category existingCategory = categoryRepository.findByCategoryName(category.getName());
        if (existingCategory != null) {
            return ApiResponse.error(UNAUTHORIZED_CODE, USERNAME_ALREADY_EXISTS);
        }

        // Save the new category
        Category savedCategory = categoryRepository.save(category);
        return ApiResponse.success(SUCCESS_CODE, savedCategory, SUCCESS_MESSAGE);
    }

}
