package com.inventoryservice.dto;

public class InventoryResponseDTO {

    private Long id;
    private String productName;
    private Integer quantity;
    private String message;

    // 🔹 REQUIRED constructor (used in service)
    public InventoryResponseDTO(Long id, String productName, Integer quantity, String message) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.message = message;
    }

    // 🔹 Default constructor (safe)
    public InventoryResponseDTO() {
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
