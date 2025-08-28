package com.mongo.MongoDB.service.impl;

import com.mongo.MongoDB.exception.ResourceNotFoundException;
import com.mongo.MongoDB.model.*;
import com.mongo.MongoDB.repository.*;
import com.mongo.MongoDB.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final InvoiceRepository invoiceRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final AuditLogRepository auditLogRepository;

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
}