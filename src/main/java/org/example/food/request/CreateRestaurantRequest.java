package org.example.food.request;

import lombok.Data;
import org.example.food.model.Adress;
import org.example.food.model.ContactInformation;

import java.util.List;

@Data
public class CreateRestaurantRequest {

    private String name;
    private String description;
    private String cuisineType;
    private Adress adress;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;

}
