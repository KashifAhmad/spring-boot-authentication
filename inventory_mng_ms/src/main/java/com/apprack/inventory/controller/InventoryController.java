package com.apprack.inventory.controller;

import com.apprack.inventory.model.RestockRequest;
import com.apprack.inventory.service.InventoryService;
import com.apprack.pm_ms.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping(value = "/restock/{productId}")
    public ResponseEntity<ApiResponse<String>> restockProduct(
            @PathVariable Long productId,
            @RequestBody RestockRequest restockRequest
    ) {
        // Delegate the restocking logic to the service class
        ApiResponse<String> response = inventoryService.restockProduct(productId, restockRequest);
        HttpStatus status = HttpStatus.valueOf(response.getCode());
        return new ResponseEntity<>(response, status);
    }
}
