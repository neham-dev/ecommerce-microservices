package com.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/inventory/check/{productName}")
    Boolean checkStock(
            @PathVariable String productName,
            @RequestParam Integer quantity
    );

    @PostMapping("/inventory/reduce")
    String reduceStock(
            @RequestParam String productName,
            @RequestParam Integer quantity
    );
}
