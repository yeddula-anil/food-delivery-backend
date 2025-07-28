package org.example.food.model;
import lombok.*;
import org.example.food.DTO.RestaurantDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

import java.util.ArrayList;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"orders", "favorites", "adresses"})

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
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<OrderItem> orders=new ArrayList<>();
    @OneToMany(mappedBy = "user",  cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<FavoriteRestaurant> favorites=new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)

    private List<Adress> adresses=new ArrayList<>();


}
