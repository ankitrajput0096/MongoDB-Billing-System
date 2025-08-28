package com.mongo.MongoDB.dto.mapper;

import com.mongo.MongoDB.dto.request.SubscriptionRequestDTO;
import com.mongo.MongoDB.dto.response.SubscriptionResponseDTO;
import com.mongo.MongoDB.model.Subscription;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {

    public Subscription toEntity(SubscriptionRequestDTO dto) {
        Subscription subscription = new Subscription();
        subscription.setUserId(new ObjectId(dto.getUserId()));
        subscription.setStartDate(dto.getStartDate());
        subscription.setEndDate(dto.getEndDate());
        subscription.setStatus(dto.getStatus());

        if (dto.getPlan() != null) {
            Subscription.Plan plan = new Subscription.Plan();
            plan.setPlanName(dto.getPlan().getPlanName());
            plan.setPrice(dto.getPlan().getPrice());
            plan.setBillingCycle(dto.getPlan().getBillingCycle());
            subscription.setPlan(plan);
        }

        return subscription;
    }

    public SubscriptionResponseDTO toDTO(Subscription subscription) {
        return SubscriptionResponseDTO.fromEntity(subscription);
    }
}