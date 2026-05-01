package com.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    /**
     * Find order by order number
     */
    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    /**
     * Find all orders by customer ID
     */
    List<OrderEntity> findByCustomerId(Long customerId);

    /**
     * Find all orders by status
     */
    List<OrderEntity> findByStatus(String status);

    /**
     * Find all orders by customer ID and status
     */
    List<OrderEntity> findByCustomerIdAndStatus(Long customerId, String status);

    /**
     * Custom query to get orders by customer name
     */
    @Query("SELECT o FROM OrderEntity o WHERE o.customerName LIKE %:customerName%")
    List<OrderEntity> findByCustomerNameContaining(@Param("customerName") String customerName);

    /**
     * Check if order exists by order number
     */
    boolean existsByOrderNumber(String orderNumber);
}
