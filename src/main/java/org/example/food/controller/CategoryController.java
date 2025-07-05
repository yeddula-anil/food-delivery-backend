package org.example.food.controller;

import org.example.food.model.Category;
import org.example.food.model.User;
import org.example.food.service.CategoryService;
import org.example.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findByEmail(jwt);
        Category createdcategory=categoryService.createCategory(category.getName(),user.getId());
        return new ResponseEntity<>(createdcategory, HttpStatus.CREATED);
    }
    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategories(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findByJwtToken(jwt);
        List<Category> categories=categoryService.findCategoryByRestaurantId(user.getId());
        return new ResponseEntity<>(categories, HttpStatus.OK);


    }
}
