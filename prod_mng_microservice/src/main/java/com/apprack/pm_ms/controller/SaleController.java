package com.apprack.pm_ms.controller;

import com.apprack.pm_ms.model.Products;
import com.apprack.pm_ms.model.sale.SaleItem;
import com.apprack.pm_ms.model.sale.SaleRequest;
import com.apprack.pm_ms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
public class SaleController {
    @Autowired
    private ProductRepository productRepository;
    @PostMapping
    public ResponseEntity<?> saleInvoice(@RequestBody SaleRequest saleRequest) {
        for (SaleItem item : saleRequest.products) {
            Products product = productRepository.findByProductName(item.getProductName());
            if (product == null) {
                throw new RuntimeException("Product " + item.getProductName() + " not found");
            }
            if (product.getQuantity() < item.getQuantity()) {
                return ResponseEntity.badRequest().body("Insufficient stock for product: " + product.getProductName());
            }

            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);
        }

        return ResponseEntity.ok("Stock updated successfully for invoice: " + saleRequest.getInvoiceNumber());
    }
}
