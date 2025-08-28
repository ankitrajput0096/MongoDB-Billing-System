package com.mongo.MongoDB.dto.mapper;

import com.mongo.MongoDB.dto.response.*;
import com.mongo.MongoDB.model.*;
import org.springframework.stereotype.Component;

@Component
public class BillingMapper {

    public UserResponseDTO toUserDTO(User user) {
        return UserResponseDTO.fromEntity(user);
    }

    public TransactionResponseDTO toTransactionDTO(Transaction transaction) {
        return TransactionResponseDTO.fromEntity(transaction);
    }

    public InvoiceResponseDTO toInvoiceDTO(Invoice invoice) {
        return InvoiceResponseDTO.fromEntity(invoice);
    }

    public SubscriptionResponseDTO toSubscriptionDTO(Subscription subscription) {
        return SubscriptionResponseDTO.fromEntity(subscription);
    }

    public AuditLogResponseDTO toAuditLogDTO(AuditLog auditLog) {
        return AuditLogResponseDTO.fromEntity(auditLog);
    }
}