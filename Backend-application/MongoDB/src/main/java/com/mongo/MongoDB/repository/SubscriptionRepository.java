package com.mongo.MongoDB.repository;

import com.mongo.MongoDB.model.Subscription;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

    // Find subscriptions by user ID
    List<Subscription> findByUserId(ObjectId userId);

    // Find subscriptions by status
    List<Subscription> findByStatus(String status);

    // Find subscriptions by plan name
    @Query("{ 'plan.planName': ?0 }")
    List<Subscription> findByPlanName(String planName);

    // Find subscriptions by billing cycle
    @Query("{ 'plan.billingCycle': ?0 }")
    List<Subscription> findByBillingCycle(String billingCycle);

    // Find active subscriptions (status active and end date null or in future)
    @Query("{ 'status': 'active', $or: [ { 'endDate': null }, { 'endDate': { $gt: ?0 } } ] }")
    List<Subscription> findActiveSubscriptions(Date currentDate);

    // Find subscriptions that need renewal (end date within specified days)
    @Query("{ 'status': 'active', 'endDate': { $lte: ?0 } }")
    List<Subscription> findSubscriptionsNeedingRenewal(Date renewalDate);

    // Find subscriptions by start date range
    List<Subscription> findByStartDateBetween(Date startDate, Date endDate);

    // Find subscriptions by end date range
    List<Subscription> findByEndDateBetween(Date startDate, Date endDate);

    // Find subscriptions by user ID and status
    List<Subscription> findByUserIdAndStatus(String userId, String status);

    // Count subscriptions by status
    Long countByStatus(String status);

    // Count subscriptions by plan name
    @Query(value = "{ 'plan.planName': ?0 }", count = true)
    Long countByPlanName(String planName);

    // Find subscriptions by price range
    @Query("{ 'plan.price': { $gte: ?0, $lte: ?1 } }")
    List<Subscription> findByPriceRange(Double minPrice, Double maxPrice);
}