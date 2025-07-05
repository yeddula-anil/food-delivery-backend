package org.example.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.food.DTO.RestaurantDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullname;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Enumerated(EnumType.STRING)
    private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private List<Order> orders=new ArrayList<>();
    @Transient
    private List<RestaurantDto> favourites=new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Adress> adresses=new ArrayList<>();


}
