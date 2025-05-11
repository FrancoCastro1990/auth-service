package com.duoc.authService.Service;

import org.springframework.stereotype.Service;

import com.duoc.authService.Model.User;
import com.duoc.authService.Repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService implements IUserService {
    
    @Autowired
    private UserRepository userRepository;
    
    
    @Override
    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if (existingUser.getEmail().equals(user.getEmail())) {
            // Username and email are correct, generate and set new password
            String newPassword = generatePassword();
            existingUser.setPassword(newPassword);
            return userRepository.save(existingUser);
            }
        }
        throw new IllegalArgumentException("Invalid username or email");
    }

    @Override
    public String login(User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if (user.getPassword().equals(existingUser.getPassword())) {
                return "Login successful";
            }
        }
        return "Login failed";
    }

    @Override
    public String generatePassword() {
        return UUID.randomUUID().toString();
    }

}
