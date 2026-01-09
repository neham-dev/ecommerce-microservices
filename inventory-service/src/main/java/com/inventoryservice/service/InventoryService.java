package com.inventoryservice.service;

import com.inventoryservice.dto.InventoryRequestDTO;
import com.inventoryservice.dto.InventoryResponseDTO;

public interface InventoryService {

    InventoryResponseDTO addOrUpdateStock(InventoryRequestDTO requestDTO);

    boolean isStockAvailable(String productName, Integer requiredQty);

    void reduceStock(String productName, Integer quantity);
}
