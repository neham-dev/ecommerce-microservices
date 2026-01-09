package com.orderservice.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderservice.client.InventoryClient;
import com.orderservice.dto.OrderRequestDTO;
import com.orderservice.dto.OrderResponseDTO;
import com.orderservice.entity.Order;
import com.orderservice.exception.OrderNotFoundException;
import com.orderservice.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryClient inventoryClient;

    // 🔥 Temporary pricing map (enterprise-friendly demo)
    private static final Map<String, Double> PRODUCT_PRICES = Map.of(
            "AC", 40000.0,
            "Laptop", 60000.0,
            "Mobile", 20000.0,
            "Charger", 1000.0
    );

    @Override
    public OrderResponseDTO createOrder(Long userId, OrderRequestDTO request) {

        Boolean stockAvailable = inventoryClient.checkStock(
                request.getProductName(),
                request.getQuantity()
        );

        if (!stockAvailable) {
            throw new RuntimeException("Insufficient stock for product: " + request.getProductName());
        }

        inventoryClient.reduceStock(
                request.getProductName(),
                request.getQuantity()
        );

        Double unitPrice = PRODUCT_PRICES.get(request.getProductName());
        if (unitPrice == null) {
            throw new RuntimeException("Price not configured for product");
        }

        Double totalAmount = unitPrice * request.getQuantity();

        Order order = new Order();
        order.setUserId(userId);
        order.setProductName(request.getProductName());
        order.setQuantity(request.getQuantity());
        order.setUnitPrice(unitPrice);
        order.setTotalAmount(totalAmount);

        return mapToResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return mapToResponse(order);
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private OrderResponseDTO mapToResponse(Order order) {
        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(order.getId());
        response.setUserId(order.getUserId());
        response.setProductName(order.getProductName());
        response.setQuantity(order.getQuantity());
        response.setUnitPrice(order.getUnitPrice());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }
}
