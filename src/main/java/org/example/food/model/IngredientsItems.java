package org.example.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String category;
    @JsonIgnore
    @ManyToOne
    private Food food;
    @JsonIgnore
    @ManyToOne

    private Restaurant restaurant;
    private boolean inStock=true;



}
