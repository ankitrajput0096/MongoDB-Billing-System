package com.mongo.MongoDB.service;

import com.mongo.MongoDB.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    Optional<Transaction> getTransactionById(String id);
    List<Transaction> getAllTransactions();
    Page<Transaction> getTransactions(Pageable pageable);
    void deleteTransaction(String id);
    List<Transaction> getTransactionsByUserId(String userId);
    List<Transaction> getTransactionsByStatus(String status);
    List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate);
    List<Transaction> getTransactionsByGateway(String gateway);
    Map<String, Object> getTransactionStats();
    Double getTotalTransactionAmountByUser(String userId);
    Long countTransactionsByStatus(String status);
}