package org.example.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long restaurantId; //  This is the ID of the original Restaurant being favorited
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    private String title;
    private String description;

    @ElementCollection
    @CollectionTable(name = "favorite_restaurant_images", joinColumns = @JoinColumn(name = "favorite_restaurant_id"))
    @Column(name = "image")
    private List<String> images;

}
