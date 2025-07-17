package org.example.food.request;

import lombok.Data;
import org.example.food.model.Category;
import org.example.food.model.IngredientsItems;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;
    private List<String> images;
    private Category category;
    private Long restaurantId;
    private boolean vegetarian;

    private boolean isSeasonal;
    private List<IngredientsItems> ingredients;
}
