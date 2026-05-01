package com.bookstore.controller;

import com.bookstore.dto.UserDTO;
import com.bookstore.entity.UserEntity;
import com.bookstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Show login page
     */
    @GetMapping("/login")
    public String showLoginPage() {
        log.info("Loading login page");
        return "login";
    }

    /**
     * Show registration page
     */
    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        log.info("Loading registration page");
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    /**
     * Register new user
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserDTO userDTO, 
                               BindingResult bindingResult, 
                               Model model) {
        log.info("Registering new user: {}", userDTO.getUsername());
        
        if (bindingResult.hasErrors()) {
            log.error("Validation errors in registration");
            return "register";
        }

        try {
            if (userService.getUserByUsername(userDTO.getUsername()).isPresent()) {
                bindingResult.rejectValue("username", "error.user", "Username already exists");
                return "register";
            }

            if (userService.getUserByEmail(userDTO.getEmail()).isPresent()) {
                bindingResult.rejectValue("email", "error.user", "Email already exists");
                return "register";
            }

            UserEntity user = new UserEntity();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());

            userService.registerUser(user);
            log.info("User registered successfully: {}", userDTO.getUsername());
            
            model.addAttribute("message", "Registration successful. Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            log.error("Error during registration: {}", e.getMessage());
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }

    /**
     * Handle login (Spring Security will handle actual authentication)
     */
    @PostMapping("/api/auth/login")
    @ResponseBody
    public Map<String, Object> login(@RequestParam String username, 
                                     @RequestParam String password) {
        log.info("Login attempt for user: {}", username);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<UserEntity> user = userService.getUserByUsername(username);
            
            if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("userId", user.get().getId());
                response.put("username", user.get().getUsername());
                log.info("User logged in successfully: {}", username);
            } else {
                response.put("success", false);
                response.put("message", "Invalid username or password");
                log.warn("Login failed for user: {}", username);
            }
        } catch (Exception e) {
            log.error("Error during login: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Login failed: " + e.getMessage());
        }
        
        return response;
    }

    /**
     * Logout user
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("User logout");
        
        // Clear authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            log.info("Clearing authentication for user: {}", authentication.getName());
        }
        
        return "redirect:/";
    }

    /**
     * User profile page
     */
    @GetMapping("/profile")
    public String showProfile(Model model) {
        log.info("Loading user profile page");
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<UserEntity> user = userService.getUserByUsername(username);
            
            if (user.isPresent()) {
                model.addAttribute("user", userService.convertToDTO(user.get()));
                return "profile";
            }
        }
        
        return "redirect:/login";
    }

    /**
     * Update user profile
     */
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") UserDTO userDTO, 
                               Model model) {
        log.info("Updating user profile");
        
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Optional<UserEntity> user = userService.getUserByUsername(authentication.getName());
                
                if (user.isPresent()) {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setFirstName(userDTO.getFirstName());
                    userEntity.setLastName(userDTO.getLastName());
                    userEntity.setPhoneNumber(userDTO.getPhoneNumber());
                    userEntity.setAddress(userDTO.getAddress());
                    userEntity.setCity(userDTO.getCity());
                    userEntity.setState(userDTO.getState());
                    userEntity.setZipCode(userDTO.getZipCode());
                    userEntity.setCountry(userDTO.getCountry());
                    
                    userService.updateUserProfile(user.get().getId(), userEntity);
                    
                    model.addAttribute("message", "Profile updated successfully");
                    return "redirect:/profile";
                }
            }
        } catch (Exception e) {
            log.error("Error updating profile: {}", e.getMessage());
            model.addAttribute("error", "Profile update failed: " + e.getMessage());
        }
        
        return "profile";
    }
}
