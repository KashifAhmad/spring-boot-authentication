package com.apprack.pm_ms.model.sale;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sale_invoice")
public class SaleInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceNumber;

    private LocalDate saleDate;

    private String buyerName;
    private String buyerContact;
    private String buyerAddress;

    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleInvoiceItem> items = new ArrayList<>();

    // Getters, Setters, Constructors
}

