package org.example.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.food.DTO.RestaurantDto;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User customer;
    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;
    private Long totalAmount;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    private Adress adress;
    @OneToMany
    private List<OrderItem> items;
    private int totalItem;
    private Long totalprice;

}
