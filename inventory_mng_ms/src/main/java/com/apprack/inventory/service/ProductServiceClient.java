package com.apprack.inventory.service;

import com.apprack.inventory.model.ProductDTO;
import com.apprack.pm_ms.model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceClient {
    @Autowired
    private RestTemplate restTemplate;

    private static final String PRODUCT_SERVICE_URL = "http://localhost:8082/products/";

    public ProductDTO getProductById(Long productId) {
        ResponseEntity<Products> response = restTemplate.getForEntity(PRODUCT_SERVICE_URL + productId, Products.class);

        Products product = response.getBody();

        return new ProductDTO(product.getProductId(), product.getProductName(), product.getCategory().getCategoryName());
    }

}
