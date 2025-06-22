package com.apprack.pm_ms.model.sale;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_invoice_items")
public class SaleInvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private SaleInvoice invoice;

    // Getters, Setters, Constructors
}
