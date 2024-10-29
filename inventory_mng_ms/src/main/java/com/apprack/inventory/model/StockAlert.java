package com.apprack.inventory.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_alert")
public class StockAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inventory_id", nullable = false)
    private Long inventoryId;  // References the Inventory entity

    @Column(name = "alert_type", nullable = false)
    private String alertType;  // Can be "LOW_STOCK" or "EXPIRY"

    @Column(name = "alert_message", nullable = false)
    private String alertMessage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Getters and Setters
}
