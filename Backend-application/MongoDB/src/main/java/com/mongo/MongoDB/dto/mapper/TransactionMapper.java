package com.mongo.MongoDB.dto.mapper;

import com.mongo.MongoDB.dto.request.TransactionRequestDTO;
import com.mongo.MongoDB.dto.response.TransactionResponseDTO;
import com.mongo.MongoDB.model.Transaction;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransactionRequestDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setUserId(new ObjectId(dto.getUserId()));
        transaction.setAmount(dto.getAmount());
        transaction.setStatus(dto.getStatus());
        transaction.setTimestamp(dto.getTimestamp());

        if (dto.getMetadata() != null) {
            Transaction.Metadata metadata = new Transaction.Metadata();
            metadata.setGateway(dto.getMetadata().getGateway());
            metadata.setTransactionId(dto.getMetadata().getTransactionId());
            transaction.setMetadata(metadata);
        }

        return transaction;
    }

    public TransactionResponseDTO toDTO(Transaction transaction) {
        return TransactionResponseDTO.fromEntity(transaction);
    }
}