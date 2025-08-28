package com.mongo.MongoDB.controller;

import com.mongo.MongoDB.dto.request.SubscriptionRequestDTO;
import com.mongo.MongoDB.dto.response.ApiResponseDTO;
import com.mongo.MongoDB.dto.response.SubscriptionResponseDTO;
import com.mongo.MongoDB.dto.mapper.SubscriptionMapper;
import com.mongo.MongoDB.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<SubscriptionResponseDTO>> createSubscription(
            @Valid @RequestBody SubscriptionRequestDTO subscriptionRequest) {

        var subscription = subscriptionMapper.toEntity(subscriptionRequest);
        var createdSubscription = subscriptionService.createSubscription(subscription);
        var response = subscriptionMapper.toDTO(createdSubscription);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response, "/api/v1/subscriptions"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<SubscriptionResponseDTO>> getSubscriptionById(@PathVariable String id) {
        var subscription = subscriptionService.getSubscriptionById(id);
        // Handle the case where user is not found
        if (subscription.isEmpty()) {
            ApiResponseDTO<SubscriptionResponseDTO> response = new ApiResponseDTO<>();
            response.setStatus(404);
            response.setMessage("Subscription not found with id: " + id);
            response.setPath("/api/v1/users/" + id);
            return ResponseEntity.status(404).body(response);
        }
        var response = subscriptionMapper.toDTO(subscription.get());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/subscriptions/" + id));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<SubscriptionResponseDTO>>> getAllSubscriptions() {
        // Get all subscriptions without pagination
        var subscriptions = subscriptionService.getAllSubscriptions();
        // Convert to DTO list
        List<SubscriptionResponseDTO> subscriptionDTOs = subscriptions.stream()
                .map(subscriptionMapper::toDTO)
                .collect(Collectors.toList());
        // Create response
        var response = ApiResponseDTO.success(subscriptionDTOs, "/api/v1/subscriptions");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteSubscription(@PathVariable String id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.ok(ApiResponseDTO.success(null, "/api/v1/subscriptions/" + id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDTO<List<SubscriptionResponseDTO>>> getSubscriptionsByUserId(
            @PathVariable String userId) {

        var subscriptions = subscriptionService.getSubscriptionsByUserId(userId);
        var response = subscriptions.stream()
                .map(subscriptionMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/subscriptions/user/" + userId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponseDTO<List<SubscriptionResponseDTO>>> getSubscriptionsByStatus(
            @PathVariable String status) {

        var subscriptions = subscriptionService.getSubscriptionsByStatus(status);
        var response = subscriptions.stream()
                .map(subscriptionMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/subscriptions/status/" + status));
    }

    @GetMapping("/plan/{planName}")
    public ResponseEntity<ApiResponseDTO<List<SubscriptionResponseDTO>>> getSubscriptionsByPlanName(
            @PathVariable String planName) {

        var subscriptions = subscriptionService.getSubscriptionsByPlanName(planName);
        var response = subscriptions.stream()
                .map(subscriptionMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/subscriptions/plan/" + planName));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponseDTO<List<SubscriptionResponseDTO>>> getActiveSubscriptions() {
        var subscriptions = subscriptionService.getActiveSubscriptions();
        var response = subscriptions.stream()
                .map(subscriptionMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/subscriptions/active"));
    }

    @GetMapping("/needing-renewal")
    public ResponseEntity<ApiResponseDTO<List<SubscriptionResponseDTO>>> getSubscriptionsNeedingRenewal() {
        var subscriptions = subscriptionService.getSubscriptionsNeedingRenewal();
        var response = subscriptions.stream()
                .map(subscriptionMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/subscriptions/needing-renewal"));
    }

    @GetMapping("/status/{status}/count")
    public ResponseEntity<ApiResponseDTO<Long>> countSubscriptionsByStatus(@PathVariable String status) {
        var count = subscriptionService.countSubscriptionsByStatus(status);
        return ResponseEntity.ok(ApiResponseDTO.success(count, "/api/v1/subscriptions/status/" + status + "/count"));
    }
}