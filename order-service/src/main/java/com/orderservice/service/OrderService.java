package com.orderservice.service;

import java.util.List;

import com.orderservice.dto.OrderRequestDTO;
import com.orderservice.dto.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO createOrder(Long userId, OrderRequestDTO request);

    OrderResponseDTO getOrderById(Long orderId);

    List<OrderResponseDTO> getOrdersByUser(Long userId);
}
