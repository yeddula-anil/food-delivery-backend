package org.example.food.repository;

import org.example.food.model.Order;
import org.example.food.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByCustomerId(Long userId);
    public List<Order> findByRestaurantId(Long restaurantId);
    @Query("SELECT oi.food.foodCategory.name AS categoryName, SUM(oi.food.price * oi.quantity) AS revenue " +
            "FROM Order o JOIN o.items oi " +
            "WHERE o.restaurant.id = :restaurantId AND o.orderStatus = 'COMPLETED' " +
            "GROUP BY oi.food.foodCategory.name")
    List<Object[]> getRevenueByCategory(@Param("restaurantId") Long restaurantId);

    @Query("SELECT FUNCTION('DATE', o.orderDate), SUM(o.totalprice) " +
            "FROM Order o " +
            "WHERE o.restaurant.id = :restaurantId AND o.orderStatus = 'COMPLETED' " +
            "GROUP BY FUNCTION('DATE', o.orderDate)")
    List<Object[]> getDailyRevenue(@Param("restaurantId") Long restaurantId);

    // ✅ Monthly Revenue
    @Query("SELECT FUNCTION('MONTH', o.orderDate), FUNCTION('YEAR', o.orderDate), SUM(o.totalprice) " +
            "FROM Order o " +
            "WHERE o.restaurant.id = :restaurantId AND o.orderStatus = 'COMPLETED' " +
            "GROUP BY FUNCTION('YEAR', o.orderDate), FUNCTION('MONTH', o.orderDate)")
    List<Object[]> getMonthlyRevenue(@Param("restaurantId") Long restaurantId);

    // ✅ Yearly Revenue
    @Query("SELECT FUNCTION('YEAR', o.orderDate), SUM(o.totalprice) " +
            "FROM Order o " +
            "WHERE o.restaurant.id = :restaurantId AND o.orderStatus = 'COMPLETED' " +
            "GROUP BY FUNCTION('YEAR', o.orderDate)")
    List<Object[]> getYearlyRevenue(@Param("restaurantId") Long restaurantId);
    List<Order> findByRestaurantIdAndOrderStatus(Long restaurantId, OrderStatus status);






}
