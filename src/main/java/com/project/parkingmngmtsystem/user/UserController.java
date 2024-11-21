package com.project.parkingmngmtsystem.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.prefs.Preferences;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerCustomer(@RequestBody Map<String, String> customerData) {
        String username = customerData.get("username");
        String password = customerData.get("password");
        String contact = customerData.get("contact");
        String role = "USER";
        String vehicleNumber = customerData.get("vehicleNumber");
        String vehicleType = customerData.get("vehicleType");

        String response = userService.registerUser(username, password, contact, role, vehicleNumber, vehicleType);

        Map<String, Object> responseBody = new HashMap<>();
        if (response.contains("successfully")) {
            responseBody.put("success", true);
            responseBody.put("message", response);
            return ResponseEntity.ok(responseBody);
        } else {
            responseBody.put("success", false);
            responseBody.put("message", response);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        }
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String username,
                              @RequestParam String password,
                              RedirectAttributes redirectAttributes) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username and password are required.");
            return new ModelAndView(new RedirectView("/login"));
        }

        Optional<User> optionalUser = userService.loginUser(username, password);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Preferences prefs = Preferences.userNodeForPackage(UserController.class);
            prefs.putLong("loggedInUserId", user.getId());
            prefs.put("loggedUserCarNumber", user.getVehicleNumber());
            prefs.put("loggedInUserRole", user.getRole());

            switch (user.getRole()) {
                case "ADMIN":
                    return new ModelAndView(new RedirectView("/adminDashboard"));
                case "USER":
                    return new ModelAndView(new RedirectView("/customerDashboard"));
                default:
                    redirectAttributes.addFlashAttribute("errorMessage", "Access denied: Unknown role");
                    return new ModelAndView(new RedirectView("/login"));
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password.");
            return new ModelAndView(new RedirectView("/login"));
        }
    }

    @PostMapping("/logout")
    public ModelAndView logout() {
        Preferences prefs = Preferences.userNodeForPackage(UserController.class);
        prefs.remove("loggedInUserId");
        prefs.remove("loggedUserCarNumber");
        prefs.remove("loggedInUserRole");

        return new ModelAndView(new RedirectView("/login"));
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        Preferences prefs = Preferences.userNodeForPackage(UserController.class);
        Long loggedInUserId = prefs.getLong("loggedInUserId", -1);

        if (loggedInUserId == -1) {
            return ResponseEntity.status(401).body(null);
        }

        Optional<User> user = userService.getUserById(loggedInUserId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // New Endpoint to get the total number of users
    @GetMapping("/total")
    public ResponseEntity<Long> getTotalUsers() {
        long totalUsers = userService.getTotalUsers();
        return ResponseEntity.ok(totalUsers);
    }
}
