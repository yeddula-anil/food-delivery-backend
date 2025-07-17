package org.example.food.service;

import jakarta.transaction.Transactional;
import org.example.food.model.FavoriteRestaurant;
import org.example.food.model.Restaurant;
import org.example.food.model.User;
import org.example.food.repository.FavoriteRestaurantRepository;
import org.example.food.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class FavoriteRestaurantServiceImpl implements FavoriteRestaurantService {
    @Autowired
    private FavoriteRestaurantRepository favoriteRestaurantRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public FavoriteRestaurant addToFavorites(Long restaurantId, String jwt) throws Exception {
        User user = userService.findByJwtToken(jwt);
        if (user == null) throw new Exception("User not found");

        Restaurant res = restaurantService.findRestaurantById(restaurantId);
        if (res == null) throw new Exception("Restaurant not found");

        // ✅ Use repository method instead of relying on lazy-loaded list
        Optional<FavoriteRestaurant> existingFavOpt =
                favoriteRestaurantRepository.findByUserIdAndRestaurantId(user.getId(), restaurantId);

        if (existingFavOpt.isPresent()) {
            favoriteRestaurantRepository.delete(existingFavOpt.get());
            System.out.println("Removed favorite for restaurantId=" + restaurantId);
            return null;
        } else {
            FavoriteRestaurant fr = new FavoriteRestaurant();
            fr.setRestaurantId(restaurantId);
            fr.setDescription(res.getDescription());
            fr.setImages(res.getImages() != null ? res.getImages() : new ArrayList<>());
            fr.setTitle(res.getName());
            fr.setUser(user);

            FavoriteRestaurant saved = favoriteRestaurantRepository.save(fr);
            System.out.println("Added favorite for restaurantId=" + restaurantId);
            return saved;
        }
    }

}
