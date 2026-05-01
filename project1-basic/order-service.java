package com.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Create a new order
     */
    public OrderEntity createOrder(OrderEntity order) {
        log.info("Creating order for customer: {}", order.getCustomerName());
        
        // Generate unique order number if not provided
        if (order.getOrderNumber() == null || order.getOrderNumber().isEmpty()) {
            order.setOrderNumber("ORD-" + System.currentTimeMillis());
        }
        
        // Check if order number already exists
        if (orderRepository.existsByOrderNumber(order.getOrderNumber())) {
            throw new IllegalArgumentException("Order number already exists: " + order.getOrderNumber());
        }
        
        // Set default status if not provided
        if (order.getStatus() == null || order.getStatus().isEmpty()) {
            order.setStatus("PENDING");
        }
        
        OrderEntity savedOrder = orderRepository.save(order);
        log.info("Order created successfully with ID: {}", savedOrder.getId());
        return savedOrder;
    }

    /**
     * Get all orders
     */
    public List<OrderEntity> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll();
    }

    /**
     * Get order by ID
     */
    public Optional<OrderEntity> getOrderById(Long id) {
        log.info("Fetching order with ID: {}", id);
        return orderRepository.findById(id);
    }

    /**
     * Get order by order number
     */
    public Optional<OrderEntity> getOrderByNumber(String orderNumber) {
        log.info("Fetching order with number: {}", orderNumber);
        return orderRepository.findByOrderNumber(orderNumber);
    }

    /**
     * Get all orders for a customer
     */
    public List<OrderEntity> getOrdersByCustomerId(Long customerId) {
        log.info("Fetching orders for customer ID: {}", customerId);
        return orderRepository.findByCustomerId(customerId);
    }

    /**
     * Get all orders by status
     */
    public List<OrderEntity> getOrdersByStatus(String status) {
        log.info("Fetching orders with status: {}", status);
        return orderRepository.findByStatus(status);
    }

    /**
     * Update order
     */
    public OrderEntity updateOrder(Long id, OrderEntity orderDetails) {
        log.info("Updating order with ID: {}", id);
        
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found with ID: {}", id);
                    return new RuntimeException("Order not found with ID: " + id);
                });

        if (orderDetails.getCustomerName() != null) {
            order.setCustomerName(orderDetails.getCustomerName());
        }
        if (orderDetails.getCustomerId() != null) {
            order.setCustomerId(orderDetails.getCustomerId());
        }
        if (orderDetails.getTotalAmount() != null) {
            order.setTotalAmount(orderDetails.getTotalAmount());
        }
        if (orderDetails.getStatus() != null) {
            order.setStatus(orderDetails.getStatus());
        }
        if (orderDetails.getShippingAddress() != null) {
            order.setShippingAddress(orderDetails.getShippingAddress());
        }
        if (orderDetails.getNotes() != null) {
            order.setNotes(orderDetails.getNotes());
        }

        OrderEntity updatedOrder = orderRepository.save(order);
        log.info("Order updated successfully with ID: {}", id);
        return updatedOrder;
    }

    /**
     * Update order status
     */
    public OrderEntity updateOrderStatus(Long id, String status) {
        log.info("Updating order status to: {} for order ID: {}", status, id);
        
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found with ID: {}", id);
                    return new RuntimeException("Order not found with ID: " + id);
                });

        order.setStatus(status);
        OrderEntity updatedOrder = orderRepository.save(order);
        log.info("Order status updated successfully");
        return updatedOrder;
    }

    /**
     * Delete order
     */
    public void deleteOrder(Long id) {
        log.info("Deleting order with ID: {}", id);
        
        if (!orderRepository.existsById(id)) {
            log.error("Order not found with ID: {}", id);
            throw new RuntimeException("Order not found with ID: " + id);
        }
        
        orderRepository.deleteById(id);
        log.info("Order deleted successfully with ID: {}", id);
    }

    /**
     * Get total number of orders
     */
    public long getTotalOrderCount() {
        return orderRepository.count();
    }

    /**
     * Search orders by customer name
     */
    public List<OrderEntity> searchByCustomerName(String customerName) {
        log.info("Searching orders for customer name: {}", customerName);
        return orderRepository.findByCustomerNameContaining(customerName);
    }
}
