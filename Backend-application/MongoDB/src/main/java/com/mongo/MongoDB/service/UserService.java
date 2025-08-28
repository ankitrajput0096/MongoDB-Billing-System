package com.mongo.MongoDB.service;

import com.mongo.MongoDB.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(String id);
    List<User> getAllUsers();
    Page<User> getUsers(Pageable pageable);
    User updateUser(String id, User userDetails);
    void deleteUser(String id);
    Optional<User> getUserByEmail(String email);
    List<User> getUsersByActiveStatus(Boolean active);
    List<User> getUsersByCity(String city);
    List<User> getUsersWithBalanceGreaterThan(Double minBalance);
    User updateUserBalance(String userId, Double amount, String transactionType);
    Long countActiveUsers();
    Boolean userExists(String email);
}