package org.example.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
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

    @Column(length=10000)
    @ElementCollection
    private List<String> images;

}
