package com.apprack.pm_ms.model.sale;

import java.time.LocalDate;
import java.util.List;
public class SaleRequest {

    public String invoiceNumber;
    private LocalDate saleDate;
    public List<SaleItem> products;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public List<SaleItem> getProducts() {
        return products;
    }

    public void setProducts(List<SaleItem> products) {
        this.products = products;
    }



}
