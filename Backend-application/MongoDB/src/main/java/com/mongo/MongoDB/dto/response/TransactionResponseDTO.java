package com.mongo.MongoDB.dto.response;

import com.mongo.MongoDB.model.Transaction;
import lombok.Data;
import java.util.Date;

@Data
public class TransactionResponseDTO {
    private String id;
    private String userId;
    private Double amount;
    private String status;
    private Date timestamp;
    private Date createdAt;
    private Date updatedAt;
    private MetadataDTO metadata;

    @Data
    public static class MetadataDTO {
        private String gateway;
        private String transactionId;
    }

    public static TransactionResponseDTO fromEntity(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(transaction.getId());
        dto.setUserId(transaction.getUserId().toString());
        dto.setAmount(transaction.getAmount());
        dto.setStatus(transaction.getStatus());
        dto.setTimestamp(transaction.getTimestamp());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setUpdatedAt(transaction.getUpdatedAt());

        if (transaction.getMetadata() != null) {
            MetadataDTO metadataDTO = new MetadataDTO();
            metadataDTO.setGateway(transaction.getMetadata().getGateway());
            metadataDTO.setTransactionId(transaction.getMetadata().getTransactionId());
            dto.setMetadata(metadataDTO);
        }

        return dto;
    }
}