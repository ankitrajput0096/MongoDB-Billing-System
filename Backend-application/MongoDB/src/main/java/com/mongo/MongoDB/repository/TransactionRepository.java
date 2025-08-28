package com.mongo.MongoDB.repository;

import com.mongo.MongoDB.model.Transaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    // Find transactions by id
    Optional<Transaction> findById(ObjectId id);

    // Find transactions by user ID
    List<Transaction> findByUserId(ObjectId userId);

    // Find transactions by status
    List<Transaction> findByStatus(String status);

    // Find transactions by amount range
    List<Transaction> findByAmountBetween(Double minAmount, Double maxAmount);

    // Find transactions by timestamp range
    List<Transaction> findByTimestampBetween(Date startDate, Date endDate);

    // Find transactions by gateway
    @Query("{ 'metadata.gateway': ?0 }")
    List<Transaction> findByGateway(String gateway);

    // Find transactions by user ID and status
    List<Transaction> findByUserIdAndStatus(String userId, String status);

    // Find transactions with amount greater than specified value
    List<Transaction> findByAmountGreaterThan(Double amount);

    // Find transactions with amount less than specified value
    List<Transaction> findByAmountLessThan(Double amount);

    // Count transactions by status
    Long countByStatus(String status);

    // Sum of transaction amounts by user
    @Query(value = "{ 'user_id': ?0 }", fields = "{ 'amount': 1 }")
    List<Transaction> findAmountsByUserId(String userId);

    // Find transactions by user ID and timestamp range
    List<Transaction> findByUserIdAndTimestampBetween(String userId, Date startDate, Date endDate);
}