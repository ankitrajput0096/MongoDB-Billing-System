package com.mongo.MongoDB.repository;

import com.mongo.MongoDB.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Find user by email
    Optional<User> findByEmail(String email);

    // Find users by active status
    List<User> findByActive(Boolean active);

    // Find users with balance greater than specified amount
    List<User> findByBalanceGreaterThan(Double balance);

    // Find users with balance less than specified amount
    List<User> findByBalanceLessThan(Double balance);

    // Find users by city in billing address
    @Query("{ 'billingAddress.city': ?0 }")
    List<User> findByCity(String city);

    // Find users by country in billing address
    @Query("{ 'billingAddress.country': ?0 }")
    List<User> findByCountry(String country);

    // Find users by age range
    @Query("{ 'age': { $gte: ?0, $lte: ?1 } }")
    List<User> findByAgeBetween(Integer minAge, Integer maxAge);

    // Find users with specific hobby
    @Query("{ 'hobbies': ?0 }")
    List<User> findByHobby(String hobby);

    // Find users by multiple criteria
    @Query("{ 'active': ?0, 'billingAddress.city': ?1, 'balance': { $gte: ?2 } }")
    List<User> findByActiveAndCityAndMinBalance(Boolean active, String city, Double minBalance);

    // Count users by active status
    Long countByActive(Boolean active);

    // Check if user exists by email
    Boolean existsByEmail(String email);

    // Delete user by email
    void deleteByEmail(String email);
}
