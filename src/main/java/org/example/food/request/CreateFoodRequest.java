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
    private Long categoryId;

    private boolean vegetarian;

    private boolean isSeasonal;

}
