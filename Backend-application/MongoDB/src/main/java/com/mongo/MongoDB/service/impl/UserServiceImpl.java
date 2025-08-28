package com.mongo.MongoDB.service.impl;

import com.mongo.MongoDB.exception.ResourceNotFoundException;
import com.mongo.MongoDB.model.User;
import com.mongo.MongoDB.repository.UserRepository;
import com.mongo.MongoDB.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        // Validate email uniqueness
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }

        // Set default values if not provided
        if (user.getActive() == null) {
            user.setActive(true);
        }

        if (user.getBalance() == null) {
            user.setBalance(0.0);
        }

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public User updateUser(String id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Update fields if provided
        if (userDetails.getName() != null) {
            user.setName(userDetails.getName());
        }

        if (userDetails.getEmail() != null && !userDetails.getEmail().equals(user.getEmail())) {
            // Check if new email is already taken
            if (userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email " + userDetails.getEmail() + " is already taken");
            }
            user.setEmail(userDetails.getEmail());
        }

        if (userDetails.getAge() != null) {
            user.setAge(userDetails.getAge());
        }

        if (userDetails.getBalance() != null) {
            user.setBalance(userDetails.getBalance());
        }

        if (userDetails.getActive() != null) {
            user.setActive(userDetails.getActive());
        }

        if (userDetails.getHobbies() != null) {
            user.setHobbies(userDetails.getHobbies());
        }

        if (userDetails.getBillingAddress() != null) {
            user.setBillingAddress(userDetails.getBillingAddress());
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsersByActiveStatus(Boolean active) {
        return userRepository.findByActive(active);
    }

    @Override
    public List<User> getUsersByCity(String city) {
        return userRepository.findByCity(city);
    }

    @Override
    public List<User> getUsersWithBalanceGreaterThan(Double minBalance) {
        return userRepository.findByBalanceGreaterThan(minBalance);
    }

    @Override
    @Transactional
    public User updateUserBalance(String userId, Double amount, String transactionType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if ("CREDIT".equalsIgnoreCase(transactionType)) {
            user.setBalance(user.getBalance() + amount);
        } else if ("DEBIT".equalsIgnoreCase(transactionType)) {
            if (user.getBalance() < amount) {
                throw new IllegalArgumentException("Insufficient balance for user: " + userId);
            }
            user.setBalance(user.getBalance() - amount);
        } else {
            throw new IllegalArgumentException("Invalid transaction type: " + transactionType);
        }

        return userRepository.save(user);
    }

    @Override
    public Long countActiveUsers() {
        return userRepository.countByActive(true);
    }

    @Override
    public Boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }
}