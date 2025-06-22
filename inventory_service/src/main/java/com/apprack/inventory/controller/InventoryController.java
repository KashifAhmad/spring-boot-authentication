package com.apprack.inventory.controller;

import com.apprack.inventory.model.ApiResponse;
import com.apprack.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PutMapping("/restock_product/{productId}")
    public ResponseEntity<ApiResponse<String>> restockProduct(
            @PathVariable Long productId,
            @RequestParam int quantityToAdd) {
        try {
            ApiResponse<String> response = inventoryService.restockProduct(productId, quantityToAdd);
            HttpStatus status = HttpStatus.valueOf(response.getCode());
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
