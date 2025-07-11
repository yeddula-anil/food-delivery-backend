package org.example.food.controller;

import org.example.food.config.JwtProvider;
import org.example.food.model.Cart;
import org.example.food.model.USER_ROLE;
import org.example.food.model.User;
import org.example.food.repository.CartRepository;
import org.example.food.repository.UserRepository;
import org.example.food.request.LoginRequest;
import org.example.food.response.AuthResponse;
import org.example.food.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; // ✅ Inject AuthenticationManager

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist != null) {
           AuthResponse response = new AuthResponse();
           response.setMessage("Email already exists");
           response.setJwt(null);
           response.setRole(null);
           return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullname(user.getFullname());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setRole(user.getRole());

        User savedUser = userRepository.save(createdUser);

        Cart cart = new Cart();
        cart.setUser(createdUser);
        cartRepository.save(cart);

        // ✅ Authenticate user to generate JWT
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Register success");
        authResponse.setJwt(jwt);
        authResponse.setRole(savedUser.getRole());
        System.out.println(jwt);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest request) {
        User existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null,"Email not registered", null));  // ✅ Distinct message
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            String jwt = jwtProvider.generateToken(authentication);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Login success");
            authResponse.setJwt(jwt);
            authResponse.setRole(USER_ROLE.valueOf(authentication.getAuthorities().iterator().next().getAuthority()));

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse( null,"Incorrect password", null));  // ✅ Distinct message
        }
    }

}
