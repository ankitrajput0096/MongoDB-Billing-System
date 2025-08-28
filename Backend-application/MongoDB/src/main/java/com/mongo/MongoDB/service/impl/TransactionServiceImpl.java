package com.mongo.MongoDB.service.impl;

import com.mongo.MongoDB.exception.ResourceNotFoundException;
import com.mongo.MongoDB.model.Transaction;
import com.mongo.MongoDB.repository.TransactionRepository;
import com.mongo.MongoDB.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        // Validate required fields
        if (transaction.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required for transaction");
        }

        if (transaction.getAmount() == null || transaction.getAmount() <= 0) {
            throw new IllegalArgumentException("Valid amount is required for transaction");
        }

        if (transaction.getStatus() == null) {
            transaction.setStatus("pending");
        }

        if (transaction.getTimestamp() == null) {
            transaction.setTimestamp(new Date());
        }

        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Page<Transaction> getTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteTransaction(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        transactionRepository.delete(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByUserId(String userId) {
        return transactionRepository.findByUserId(new ObjectId(userId));
    }

    @Override
    public List<Transaction> getTransactionsByStatus(String status) {
        return transactionRepository.findByStatus(status);
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate) {
        return transactionRepository.findByTimestampBetween(startDate, endDate);
    }

    @Override
    public List<Transaction> getTransactionsByGateway(String gateway) {
        return transactionRepository.findByGateway(gateway);
    }

    @Override
    public Map<String, Object> getTransactionStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalTransactions", transactionRepository.count());
        stats.put("completedTransactions", transactionRepository.countByStatus("completed"));
        stats.put("pendingTransactions", transactionRepository.countByStatus("pending"));
        stats.put("failedTransactions", transactionRepository.countByStatus("failed"));

        return stats;
    }

    @Override
    public Double getTotalTransactionAmountByUser(String userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(new ObjectId(userId));
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    @Override
    public Long countTransactionsByStatus(String status) {
        return transactionRepository.countByStatus(status);
    }
}