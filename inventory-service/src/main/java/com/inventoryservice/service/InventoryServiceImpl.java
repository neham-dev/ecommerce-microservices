package com.inventoryservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventoryservice.dto.InventoryRequestDTO;
import com.inventoryservice.dto.InventoryResponseDTO;
import com.inventoryservice.entity.Inventory;
import com.inventoryservice.repository.InventoryRepository;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryResponseDTO addOrUpdateStock(InventoryRequestDTO requestDTO) {

        Optional<Inventory> optionalInventory =
                inventoryRepository.findByProductName(requestDTO.getProductName());

        Inventory inventory;

        if (optionalInventory.isPresent()) {
            inventory = optionalInventory.get();
            inventory.setQuantity(inventory.getQuantity() + requestDTO.getQuantity());
        } else {
            inventory = new Inventory(
                    requestDTO.getProductName(),
                    requestDTO.getQuantity()
            );
        }

        Inventory saved = inventoryRepository.save(inventory);

        return new InventoryResponseDTO(
                saved.getId(),
                saved.getProductName(),
                saved.getQuantity(),
                "Stock updated successfully"
        );
    }

    @Override
    public boolean isStockAvailable(String productName, Integer requiredQty) {

        return inventoryRepository.findByProductName(productName)
                .map(inv -> inv.getQuantity() >= requiredQty)
                .orElse(false);
    }

    @Override
    public void reduceStock(String productName, Integer quantity) {

        Inventory inventory = inventoryRepository.findByProductName(productName)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
    }
}
