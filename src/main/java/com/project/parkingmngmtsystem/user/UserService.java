package com.project.parkingmngmtsystem.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    // Register a new user
    public String registerUser( String username, String password, String contact,String role,String vehicleNumber,String vehicleType) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "A user with this username is already registered.";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setContact(contact);
        user.setRole(role);
        user.setVehicleNumber(vehicleNumber);
        user.setVehicleType(vehicleType);
        userRepository.save(user);
        return "user registered successfully.";
    }


    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(updatedUser.getUsername());
        user.setContact(updatedUser.getContact());
        user.setVehicleNumber(updatedUser.getVehicleNumber());
        return userRepository.save(user);
    }
    // Login User with username and password
    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);

        // Check if user exists and password matches
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }

        return Optional.empty();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

}

