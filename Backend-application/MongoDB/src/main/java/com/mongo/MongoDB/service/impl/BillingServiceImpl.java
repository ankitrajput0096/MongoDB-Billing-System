package com.mongo.MongoDB.service.impl;

import com.mongo.MongoDB.exception.ResourceNotFoundException;
import com.mongo.MongoDB.model.*;
import com.mongo.MongoDB.repository.*;
import com.mongo.MongoDB.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final InvoiceRepository invoiceRepository;
    private final AuditLogRepository auditLogRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    @Transactional
    public Transaction processPayment(String userId, Double amount, String gateway, String transactionId) {
        // 1. Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // 2. Create transaction
        Transaction transaction = new Transaction();
        transaction.setUserId(new ObjectId(userId));
        transaction.setAmount(amount);
        transaction.setStatus("completed");
        transaction.setTimestamp(new Date());

        Transaction.Metadata metadata = new Transaction.Metadata();
        metadata.setGateway(gateway);
        metadata.setTransactionId(transactionId);
        transaction.setMetadata(metadata);

        Transaction savedTransaction = transactionRepository.save(transaction);

        // 3. Update user balance
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        // 4. Create audit log
        AuditLog auditLog = new AuditLog();
        auditLog.setEntityType("transaction");
        auditLog.setEntityId(new ObjectId(savedTransaction.getId()));
        auditLog.setAction("payment_processed");
        auditLog.setTimestamp(new Date());

        Map<String, Object> details = new HashMap<>();
        details.put("userId", userId);
        details.put("amount", amount);
        details.put("gateway", gateway);
        auditLog.setDetails(details);

        auditLogRepository.save(auditLog);

        return savedTransaction;
    }

    @Override
    @Transactional
    public Map<String, Object> generateBillingReport(String userId, Date startDate, Date endDate) {
        // 1. Verify user exists
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        // 2. Get all transactions for the period
        Map<String, Object> report = new HashMap<>();

        // 3. Get transactions
        report.put("transactions", transactionRepository.findByUserIdAndTimestampBetween(userId, startDate, endDate));

        // 4. Get invoices
        report.put("invoices", invoiceRepository.findByUserIdAndDueDateBetween(userId, startDate, endDate));

        // 5. Calculate total spent
        double totalSpent = transactionRepository.findByUserIdAndTimestampBetween(userId, startDate, endDate)
                .stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
        report.put("totalSpent", totalSpent);

        // 6. Get current balance
        User user = userRepository.findById(userId).get();
        report.put("currentBalance", user.getBalance());

        // 7. Create audit log
        AuditLog auditLog = new AuditLog();
        auditLog.setEntityType("user");
        auditLog.setEntityId(new ObjectId(userId));
        auditLog.setAction("billing_report_generated");
        auditLog.setTimestamp(new Date());

        Map<String, Object> details = new HashMap<>();
        details.put("startDate", startDate);
        details.put("endDate", endDate);
        details.put("transactionCount", ((List<?>) report.get("transactions")).size());
        auditLog.setDetails(details);

        auditLogRepository.save(auditLog);

        return report;
    }

    @Override
    public List<Map<String, Object>> getRevenueByGateway(Date startDate, Date endDate) {
        // Create aggregation pipeline for revenue by gateway
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("timestamp").gte(startDate).lte(endDate)
                        .and("status").is("completed")),
                Aggregation.group("metadata.gateway")
                        .sum("amount").as("totalRevenue")
                        .count().as("transactionCount"),
                Aggregation.project("totalRevenue", "transactionCount")
                        .and("_id").as("gateway")
                        .andExclude("_id"),
                Aggregation.sort(Sort.Direction.DESC, "totalRevenue")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation, "transactions", Document.class);

        return results.getMappedResults().stream()
                .map(this::documentToMap)
                .toList();
    }

    @Override
    public List<Map<String, Object>> getTopSpenders(Date startDate, Date endDate, int limit) {
        // Create aggregation pipeline for top spenders
        // Fixed: Using "user_id" instead of "userId" to match the field name in MongoDB
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("timestamp").gte(startDate).lte(endDate)
                        .and("status").is("completed")),
                Aggregation.group("user_id")  // Changed from "userId" to "user_id"
                        .sum("amount").as("totalSpent")
                        .count().as("transactionCount"),
                Aggregation.lookup("users", "_id", "_id", "userDetails"),
                Aggregation.unwind("userDetails", true),
                Aggregation.project()
                        .and("_id").as("userId")
                        .and("userDetails.name").as("userName")
                        .and("userDetails.email").as("userEmail")
                        .and("totalSpent").as("totalSpent")
                        .and("transactionCount").as("transactionCount")
                        .andExclude("_id"),
                Aggregation.sort(Sort.Direction.DESC, "totalSpent"),
                Aggregation.limit(limit)
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation, "transactions", Document.class);

        return results.getMappedResults().stream()
                .map(this::documentToMap)
                .collect(Collectors.toList());
    }

    // Helper method to convert Document to Map
    private Map<String, Object> documentToMap(Document document) {
        Map<String, Object> map = new HashMap<>();
        for (String key : document.keySet()) {
            map.put(key, document.get(key));
        }
        return map;
    }
}