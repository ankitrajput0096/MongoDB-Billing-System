package com.mongo.MongoDB.dto.response;

import com.mongo.MongoDB.model.Subscription;
import lombok.Data;
import java.util.Date;

@Data
public class SubscriptionResponseDTO {
    private String id;
    private String userId;
    private PlanDTO plan;
    private Date startDate;
    private Date endDate;
    private String status;
    private Date createdAt;
    private Date updatedAt;

    @Data
    public static class PlanDTO {
        private String planName;
        private Double price;
        private String billingCycle;
    }

    public static SubscriptionResponseDTO fromEntity(Subscription subscription) {
        SubscriptionResponseDTO dto = new SubscriptionResponseDTO();
        dto.setId(subscription.getId().toString());
        dto.setUserId(subscription.getUserId().toString());
        dto.setStartDate(subscription.getStartDate());
        dto.setEndDate(subscription.getEndDate());
        dto.setStatus(subscription.getStatus());
        dto.setCreatedAt(subscription.getCreatedAt());
        dto.setUpdatedAt(subscription.getUpdatedAt());

        if (subscription.getPlan() != null) {
            PlanDTO planDTO = new PlanDTO();
            planDTO.setPlanName(subscription.getPlan().getPlanName());
            planDTO.setPrice(subscription.getPlan().getPrice());
            planDTO.setBillingCycle(subscription.getPlan().getBillingCycle());
            dto.setPlan(planDTO);
        }

        return dto;
    }
}