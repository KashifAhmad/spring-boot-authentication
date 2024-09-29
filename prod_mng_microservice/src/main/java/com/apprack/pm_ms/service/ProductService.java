package com.apprack.pm_ms.service;

import com.apprack.pm_ms.contants.HttpResponseCodes;
import com.apprack.pm_ms.contants.HttpResponseMessages;
import com.apprack.pm_ms.model.ApiResponse;
import com.apprack.pm_ms.model.Category;
import com.apprack.pm_ms.model.Products;
import com.apprack.pm_ms.repository.CategoryRepository;
import com.apprack.pm_ms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.apprack.pm_ms.contants.HttpResponseCodes.SUCCESS_CODE;
import static com.apprack.pm_ms.contants.HttpResponseCodes.UNAUTHORIZED_CODE;
import static com.apprack.pm_ms.contants.HttpResponseMessages.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ApiResponse<Products> addProduct(Products products) {
        // Check if the category exists
        Long categoryId = products.getCategory().getCategoryId();
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(CATEGORY_NOT_FOUND));

        // Set the existing category to the product
        products.setCategory(existingCategory);

        // Check if the product already exists
        Products existingProduct = productRepository.findByProductName(products.getProductName());
        if (existingProduct != null) {
            return ApiResponse.error(UNAUTHORIZED_CODE, USERNAME_ALREADY_EXISTS);
        }

        // Save the new product
        Products savedProduct = productRepository.save(products);
        return ApiResponse.success(SUCCESS_CODE, savedProduct, SUCCESS_MESSAGE);
    }

    public List<String> saveProductImages(MultipartFile[] images) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        String directory = "/Users/kashif/Desktop/javaSpringRack/spring-boot-authentication/images/";

        for (MultipartFile image : images) {
            // Generate a unique filename
            String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path imagePath = Paths.get(directory, filename);

            // Save the file
            Files.copy(image.getInputStream(), imagePath);

            // Add the path to the list
            imagePaths.add(imagePath.toString());
        }

        return imagePaths;  // Return the list of image paths
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

    public ApiResponse<String> deleteCategory(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (!categoryOptional.isPresent()) {
            // Return an appropriate response if the category is not found
            return ApiResponse.error(UNAUTHORIZED_CODE, USERNAME_ALREADY_EXISTS);
        }
        try {
            categoryRepository.deleteById(categoryId); // Delete the category
            return ApiResponse.success(SUCCESS_CODE, CATEGORY_DELETED, CATEGORY_DELETED);
        } catch (Exception e) {
            // Handle any unexpected exceptions
            return ApiResponse.error(HttpResponseCodes.INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR_MESSAGE);
        }
    }


    public ApiResponse<List<Products>> getAllProducts() {
        try {
            List<Products> products = productRepository.findAll();
            return ApiResponse.success(SUCCESS_CODE, products, PRODUCT_FETCHED);
        } catch (Exception e) {
            return ApiResponse.error(HttpResponseCodes.INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR_MESSAGE);
        }
    }


    public ApiResponse<Void> deleteProductFromCategory(Long categoryId, Long productId) {
        try {
            // Ensure the category exists
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException(CATEGORY_NOT_FOUND));

            // Ensure the product exists
            Products product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));

            if (!product.getCategory().getCategoryId().equals(categoryId)) {
                return ApiResponse.error(HttpResponseCodes.UNAUTHORIZED_CODE, PRODUCT_NOT_BELONGS);
            }

            productRepository.delete(product);
            return ApiResponse.success(SUCCESS_CODE, null, PRODUCT_DELETED);
        } catch (Exception e) {
            return ApiResponse.error(HttpResponseCodes.INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR_MESSAGE);
        }
    }

    public ApiResponse<Products> updateProduct(Long productId, Products updatedProduct) {
        // Check if the product exists
        Products existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));

        // Check if the category exists
        Long categoryId = updatedProduct.getCategory().getCategoryId();
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(CATEGORY_NOT_FOUND));

        // Update product fields
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setExpiryDate(updatedProduct.getExpiryDate());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCategory(existingCategory); // Set updated category

        // Save updated product
        Products savedProduct = productRepository.save(existingProduct);

        return ApiResponse.success(SUCCESS_CODE, savedProduct, PRODUCT_UPDATED);
    }

    public ApiResponse<Products> getProductById(Long productId) {
        try {
            Products product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));
            return ApiResponse.success(SUCCESS_CODE, product, SUCCESS_MESSAGE);
        } catch (Exception e) {
            return ApiResponse.error(HttpResponseCodes.NOT_FOUND_CODE, PRODUCT_NOT_FOUND);
        }
    }
}
