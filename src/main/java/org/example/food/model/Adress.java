package org.example.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Adress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String streetAdress;
    private String city;
    private String stateProvince;

    private String postalCode;
    private String country;
    @ManyToOne
    @JoinColumn(name = "user_id")  // Adds 'user_id' column in 'adress' table
    @JsonIgnore
    private User user;


}
