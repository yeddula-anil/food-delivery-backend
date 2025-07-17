package org.example.food.DTO;
import org.example.food.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class RestaurantDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // ✅ 1. Primary Key for this table (FavoriteRestaurant)

    private Long restaurantId; // ✅ 2. This is the ID of the original Restaurant being favorited

    private String title;
    private String description;

    @ElementCollection
    private List<String> images;

    @ManyToOne
    @JsonIgnore
    private User user;


}
