package com.mongo.MongoDB.service.impl;

import com.mongo.MongoDB.exception.ResourceNotFoundException;
import com.mongo.MongoDB.model.Subscription;
import com.mongo.MongoDB.repository.SubscriptionRepository;
import com.mongo.MongoDB.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public Subscription createSubscription(Subscription subscription) {
        // Validate required fields
        if (subscription.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required for subscription");
        }

        if (subscription.getPlan() == null) {
            throw new IllegalArgumentException("Plan details are required for subscription");
        }

        if (subscription.getStatus() == null) {
            subscription.setStatus("active");
        }

        if (subscription.getStartDate() == null) {
            subscription.setStartDate(new Date());
        }

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Optional<Subscription> getSubscriptionById(String id) {
        return subscriptionRepository.findById(id);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Page<Subscription> getSubscriptions(Pageable pageable) {
        return subscriptionRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteSubscription(String id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + id));
        subscriptionRepository.delete(subscription);
    }

    @Override
    public List<Subscription> getSubscriptionsByUserId(String userId) {
        return subscriptionRepository.findByUserId(new ObjectId(userId));
    }

    @Override
    public List<Subscription> getSubscriptionsByStatus(String status) {
        return subscriptionRepository.findByStatus(status);
    }

    @Override
    public List<Subscription> getSubscriptionsByPlanName(String planName) {
        return subscriptionRepository.findByPlanName(planName);
    }

    @Override
    public List<Subscription> getActiveSubscriptions() {
        return subscriptionRepository.findActiveSubscriptions(new Date());
    }

    @Override
    public List<Subscription> getSubscriptionsNeedingRenewal() {
        // Define renewal threshold (e.g., 7 days from now)
        Date renewalThreshold = new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000);
        return subscriptionRepository.findSubscriptionsNeedingRenewal(renewalThreshold);
    }

    @Override
    public Long countSubscriptionsByStatus(String status) {
        return subscriptionRepository.countByStatus(status);
    }
}