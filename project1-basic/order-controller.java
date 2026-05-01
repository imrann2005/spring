package com.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Create a new order
     * POST /api/orders
     */
    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderEntity order, BindingResult bindingResult) {
        log.info("Received request to create order for customer: {}", order.getCustomerName());
        
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        try {
            OrderEntity createdOrder = orderService.createOrder(order);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Order created successfully");
            response.put("order", createdOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error creating order: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Get all orders
     * GET /api/orders
     */
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        log.info("Received request to get all orders");
        try {
            List<OrderEntity> orders = orderService.getAllOrders();
            Map<String, Object> response = new HashMap<>();
            response.put("totalCount", orders.size());
            response.put("orders", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving orders: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Get order by ID
     * GET /api/orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        log.info("Received request to get order with ID: {}", id);
        
        try {
            Optional<OrderEntity> order = orderService.getOrderById(id);
            
            if (order.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Order found");
                response.put("order", order.get());
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Order not found with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            log.error("Error retrieving order with ID {}: {}", id, e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Get order by order number
     * GET /api/orders/number/{orderNumber}
     */
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<?> getOrderByNumber(@PathVariable String orderNumber) {
        log.info("Received request to get order with number: {}", orderNumber);
        
        try {
            Optional<OrderEntity> order = orderService.getOrderByNumber(orderNumber);
            
            if (order.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Order found");
                response.put("order", order.get());
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Order not found with number: " + orderNumber);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            log.error("Error retrieving order with number {}: {}", orderNumber, e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Get orders by customer ID
     * GET /api/orders/customer/{customerId}
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable Long customerId) {
        log.info("Received request to get orders for customer ID: {}", customerId);
        
        try {
            List<OrderEntity> orders = orderService.getOrdersByCustomerId(customerId);
            Map<String, Object> response = new HashMap<>();
            response.put("customerId", customerId);
            response.put("totalCount", orders.size());
            response.put("orders", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving orders for customer {}: {}", customerId, e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Get orders by status
     * GET /api/orders/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable String status) {
        log.info("Received request to get orders with status: {}", status);
        
        try {
            List<OrderEntity> orders = orderService.getOrdersByStatus(status);
            Map<String, Object> response = new HashMap<>();
            response.put("status", status);
            response.put("totalCount", orders.size());
            response.put("orders", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving orders with status {}: {}", status, e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Update order
     * PUT /api/orders/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderEntity orderDetails, BindingResult bindingResult) {
        log.info("Received request to update order with ID: {}", id);
        
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        try {
            OrderEntity updatedOrder = orderService.updateOrder(id, orderDetails);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Order updated successfully");
            response.put("order", updatedOrder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error updating order with ID {}: {}", id, e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Update order status
     * PATCH /api/orders/{id}/status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        log.info("Received request to update status of order with ID: {} to: {}", id, status);
        
        try {
            OrderEntity updatedOrder = orderService.updateOrderStatus(id, status);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Order status updated successfully");
            response.put("order", updatedOrder);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error updating status of order with ID {}: {}", id, e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Delete order
     * DELETE /api/orders/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        log.info("Received request to delete order with ID: {}", id);
        
        try {
            orderService.deleteOrder(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Order deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error deleting order with ID {}: {}", id, e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Search orders by customer name
     * GET /api/orders/search?customerName={customerName}
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchByCustomerName(@RequestParam String customerName) {
        log.info("Received request to search orders for customer: {}", customerName);
        
        try {
            List<OrderEntity> orders = orderService.searchByCustomerName(customerName);
            Map<String, Object> response = new HashMap<>();
            response.put("customerName", customerName);
            response.put("totalCount", orders.size());
            response.put("orders", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error searching orders for customer {}: {}", customerName, e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Get total order count
     * GET /api/orders/stats/total
     */
    @GetMapping("/stats/total")
    public ResponseEntity<?> getTotalOrderCount() {
        log.info("Received request to get total order count");
        
        try {
            long totalCount = orderService.getTotalOrderCount();
            Map<String, Object> response = new HashMap<>();
            response.put("totalOrders", totalCount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving total order count: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
