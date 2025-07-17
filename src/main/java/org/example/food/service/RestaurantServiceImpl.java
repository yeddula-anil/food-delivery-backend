package org.example.food.service;

import org.example.food.DTO.RestaurantDto;
import org.example.food.model.Adress;
import org.example.food.model.Restaurant;
import org.example.food.model.User;
import org.example.food.repository.AdressRepository;
import org.example.food.repository.RestaurantRepository;
import org.example.food.repository.UserRepository;
import org.example.food.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdressRepository adressRepository;






    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        if (req == null || req.getAdress() == null) {
            throw new IllegalArgumentException("Request or address cannot be null");
        }

        // Set user inside address
        Adress address = req.getAdress();
        address.setUser(user);

        Restaurant restaurant = new Restaurant();
        restaurant.setAdress(address); // Cascade will save it
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setImages(req.getImages());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }





    @Override
    public Restaurant updateRestaurant(Long restauarantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
        Restaurant restaurant=findRestaurantById(restauarantId);
        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if(restaurant.getDescription()!=null){
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if(restaurant.getName()!=null){
            restaurant.setName(updatedRestaurant.getName());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurantById(Long restaurantId) throws Exception {
        Restaurant restaurant=restaurantRepository.findById(restaurantId).get();
        if(restaurant==null){
            throw new Exception("no restaurant with that name found");
        }
        restaurantRepository.delete(restaurant);


    }

    @Override
    public List<Restaurant> getAllRestaurants() {
       return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String search) {
        return restaurantRepository.findBySearchQuery(search);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> opt=restaurantRepository.findById(restaurantId);
       if(!opt.isPresent()){
           throw new Exception("no restaurant with that id found");
       }
       return opt.get();

    }

    @Override
    public Restaurant findRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant=restaurantRepository.findRestaurantByOwnerId(userId);
        if(restaurant==null){
            throw new Exception("no restaurant with that owner id found");
        }
        return restaurant;
    }

//    @Override
//    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
//        Restaurant restaurant = findRestaurantById(restaurantId);
//
//        // Convert to DTO
//        RestaurantDto restaurantDto = new RestaurantDto();
//        restaurantDto.setDescription(restaurant.getDescription());
//        restaurantDto.setImages(restaurant.getImages());
//        restaurantDto.setTitle(restaurant.getName());
//        restaurantDto.setId(restaurantId);
//
//        // Fix: Check if restaurant exists in favourites by ID
//        boolean isAlreadyFavourite = user.getFavorites()
//                .stream()
//                .anyMatch(fav -> fav.getId().equals(restaurantId));
//
//        if (isAlreadyFavourite) {
//            user.getFavorites().removeIf(fav -> fav.getId().equals(restaurantId));
//        } else {
//            user.getFavorites().add(restaurantDto);
//        }
//
//        userRepository.save(user);
//        System.out.println(restaurantDto.getId());
//
//        return restaurantDto;
//    }


    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant=findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        restaurantRepository.save(restaurant);
        return restaurant;
    }
}
