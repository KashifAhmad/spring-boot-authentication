package com.apprack.inventory.model;
public class ProductDTO {
    private Long productId;
    private String productName;
    private String category;

    // Add any other minimal fields you need for inventory purposes
    // Constructors, getters, and setters

    public ProductDTO(Long productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    // Getters and setters
}
