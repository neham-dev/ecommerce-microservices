package com.inventoryservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.inventoryservice.dto.InventoryRequestDTO;
import com.inventoryservice.dto.InventoryResponseDTO;
import com.inventoryservice.service.InventoryService;

@RestController
@RequestMapping("/inventory")
@Validated
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // 🔹 Add or Update Stock
    @PostMapping
    public ResponseEntity<InventoryResponseDTO> addStock(
            @RequestBody @Validated InventoryRequestDTO requestDTO) {

        InventoryResponseDTO response =
                inventoryService.addOrUpdateStock(requestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 🔹 Check Stock Availability
    @GetMapping("/check/{productName}")
    public ResponseEntity<Boolean> checkStock(
            @PathVariable String productName,
            @RequestParam Integer quantity) {

        boolean available =
                inventoryService.isStockAvailable(productName, quantity);

        return ResponseEntity.ok(available);
    }

    // 🔹 Reduce Stock (Internal use by Order Service)
    @PostMapping("/reduce")
    public ResponseEntity<String> reduceStock(
            @RequestParam String productName,
            @RequestParam Integer quantity) {

        inventoryService.reduceStock(productName, quantity);

        return ResponseEntity.ok("Stock reduced successfully");
    }
}
