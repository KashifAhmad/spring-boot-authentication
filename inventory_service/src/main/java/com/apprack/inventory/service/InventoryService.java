package com.apprack.inventory.service;

import com.apprack.inventory.contants.HttpResponseCodes;
import com.apprack.inventory.model.ApiResponse;
import com.apprack.inventory.model.Products;
import com.apprack.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;

    public ApiResponse<String> restockProduct(Long productId, int quantityToAdd) {
        Optional<Products> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Products product = productOpt.get();
            product.setQuantity(product.getQuantity() + quantityToAdd);

            productRepository.save(product);

            if(product.getQuantity() <5){
                //need to trigger alert here
            }

            return ApiResponse.success(HttpResponseCodes.SUCCESS_CODE, "Product restocked successfully", "Success");
        } else {
            return ApiResponse.error(HttpResponseCodes.NOT_FOUND_CODE, "Product not found");
        }

    }
}