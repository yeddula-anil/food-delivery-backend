package org.example.food.controller;

import org.example.food.model.Category;
import org.example.food.model.User;
import org.example.food.request.CreateCategoryRequest;
import org.example.food.service.CategoryService;
import org.example.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody CreateCategoryRequest request,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        Category createdcategory=categoryService.createCategory(request.getCategory(),user.getId());
        return new ResponseEntity<>(createdcategory, HttpStatus.CREATED);
    }
    @GetMapping("/category/restaurant/{id}")
    public ResponseEntity<List<Category>> getRestaurantCategories(@RequestHeader("Authorization") String jwt,@PathVariable("id") Long id) throws Exception {
        User user=userService.findByJwtToken(jwt);
        List<Category> categories=categoryService.findCategoryByRestaurantId(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);


    }
}
