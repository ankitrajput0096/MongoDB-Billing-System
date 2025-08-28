package com.mongo.MongoDB.service;

import com.mongo.MongoDB.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    Subscription createSubscription(Subscription subscription);
    Optional<Subscription> getSubscriptionById(String id);
    List<Subscription> getAllSubscriptions();
    Page<Subscription> getSubscriptions(Pageable pageable);
    void deleteSubscription(String id);
    List<Subscription> getSubscriptionsByUserId(String userId);
    List<Subscription> getSubscriptionsByStatus(String status);
    List<Subscription> getSubscriptionsByPlanName(String planName);
    List<Subscription> getActiveSubscriptions();
    List<Subscription> getSubscriptionsNeedingRenewal();
    Long countSubscriptionsByStatus(String status);
}