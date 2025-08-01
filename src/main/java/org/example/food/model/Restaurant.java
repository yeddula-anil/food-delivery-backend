package org.example.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User owner;
    private String name;
    private String description;
    private String cuisineType;
    @OneToOne(cascade = CascadeType.ALL)
    private Adress adress;
    @Embedded
    private ContactInformation contactInformation;
    private String openingHours;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orders=new ArrayList<>();
    @ElementCollection
    @Column(length = 1000)
    private List<String> images;
    private LocalDateTime registrationDate;
    private boolean open;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Food> foods=new ArrayList<>();


}
