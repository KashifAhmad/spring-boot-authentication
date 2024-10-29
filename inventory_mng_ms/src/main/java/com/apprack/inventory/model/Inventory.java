package com.apprack.inventory.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;  // References the Product entity from Product Management Service

    @Column(name = "current_stock", nullable = false)
    private Integer currentStock;

    @Column(name = "last_restock_date")
    private LocalDate lastRestockDate;

    @Column(name = "low_stock_threshold", nullable = false)
    private Integer lowStockThreshold;  // Threshold for low stock alerts

    @Column(name = "expiry_date")
    private LocalDate expiryDate;  // Optional: If you want to track product expiry dates



}
