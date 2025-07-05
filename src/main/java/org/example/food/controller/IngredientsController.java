package org.example.food.controller;

import org.example.food.model.IngredientsCategory;
import org.example.food.model.IngredientsItems;
import org.example.food.request.IngredientsCategoryRequest;
import org.example.food.request.IngredientsItemRequest;

import org.example.food.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")

public class IngredientsController {
    @Autowired
    private IngredientsService ingredientsService;
    @PostMapping("/category")
    public ResponseEntity<IngredientsCategory> createIngredientsCategory(@RequestBody IngredientsCategoryRequest request,
                                                                         @RequestHeader("Authorization") String jwt) throws Exception {
        IngredientsCategory item=ingredientsService.createIngredientCategory(request.getName(),request.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }
    @PostMapping
    public ResponseEntity<IngredientsItems> createItem(@RequestBody IngredientsItemRequest req) throws Exception{
        IngredientsItems item=ingredientsService.createIngredientItem(req.getRestaurantId(),req.getName(),req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);


    }
    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItems> updateIngredientsStock(@PathVariable Long id) throws Exception{
        IngredientsItems item=ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);

    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItems>> getRestaurantIngredients(@PathVariable Long id) throws Exception{
        List<IngredientsItems> items=ingredientsService.findIngredientsItemsByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);

    }
    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientsCategory>> getRestaurantIngredientsCategory(@PathVariable Long id) throws Exception{
        List<IngredientsCategory> categories=ingredientsService.findIngredientsCategoryByRestaurantId(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
