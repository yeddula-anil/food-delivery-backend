package org.example.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Long price;
    @JsonIgnore
    @ManyToOne
    private Category foodCategory;
    @Column(length=10000)
    @ElementCollection
    private List<String> images;
    private boolean available;

    @ManyToOne
    @JsonIgnoreProperties("foods")
    private Restaurant restaurant;
    private boolean isVegetarian;
    private boolean isSeasonal;
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientsItems> ingredients = new ArrayList<>();
    private Date creationDate;
}
