package org.example.food.repository;

import lombok.Data;
import org.example.food.model.OrderItem;
import org.example.food.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByUserId(Long userId);
    List<OrderItem> findByRestaurantId(Long restaurantId);
    @Query("SELECT o FROM OrderItem o WHERE o.food.restaurant.id = :restaurantId AND o.orderStatus = :status")
    List<OrderItem> findByRestaurantIdAndStatus(@Param("restaurantId") Long restaurantId, @Param("status") OrderStatus status);

    @Query("SELECT FUNCTION('MONTHNAME', oi.orderDate) AS month, SUM(oi.totalPrice) AS revenue " +
            "FROM OrderItem oi " +
            "WHERE oi.restaurant.id = :restaurantId AND oi.orderStatus = 'COMPLETED' " +
            "GROUP BY FUNCTION('MONTH', oi.orderDate), FUNCTION('MONTHNAME', oi.orderDate) " +
            "ORDER BY FUNCTION('MONTH', oi.orderDate)")
    List<Object[]> getMonthlyRevenueByRestaurant(@Param("restaurantId") Long restaurantId);





}
