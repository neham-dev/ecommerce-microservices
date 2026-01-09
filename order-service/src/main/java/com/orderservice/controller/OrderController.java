package com.orderservice.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.orderservice.dto.OrderRequestDTO;
import com.orderservice.dto.OrderResponseDTO;
import com.orderservice.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 🔹 CREATE ORDER (userId from JWT via Gateway)
    @PostMapping
    public OrderResponseDTO createOrder(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @Valid @RequestBody OrderRequestDTO request) {

        if (userId == null) {
            throw new RuntimeException("Unauthorized request");
        }

        return orderService.createOrder(userId, request);
    }

    

    // 🔹 GET ORDER BY ID (ownership check can be added later)
    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // 🔹 GET MY ORDERS (LOGGED-IN USER)
    @GetMapping("/my")
    public List<OrderResponseDTO> getMyOrders(
            @RequestHeader("X-User-Id") Long userId) {

        return orderService.getOrdersByUser(userId);
    }

    // 🔹 GET ORDERS BY USER (ADMIN ONLY)
    // 🔐 RBAC enforced at Gateway
    @GetMapping("/user/{userId}")
    public List<OrderResponseDTO> getOrdersByUser(
            @PathVariable Long userId) {

        return orderService.getOrdersByUser(userId);
    }
}
