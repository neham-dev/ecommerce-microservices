package com.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = false) // ❌ fail on unknown fields
public class OrderRequestDTO {

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotNull
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    // ❌ Reject userId if sent
    @JsonSetter("userId")
    public void rejectUserId(Long userId) {
        throw new IllegalArgumentException("userId must not be sent in request body");
    }

    // ❌ Reject price if sent
    @JsonSetter("price")
    public void rejectPrice(Double price) {
        throw new IllegalArgumentException("price must not be sent in request body");
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
