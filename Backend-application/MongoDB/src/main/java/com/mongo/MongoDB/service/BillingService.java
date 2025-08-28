package com.mongo.MongoDB.service;


import com.mongo.MongoDB.model.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

public interface BillingService {
    @Transactional
    Transaction processPayment(String userId, Double amount, String gateway, String transactionId);

    @Transactional
    Map<String, Object> generateBillingReport(String userId, Date startDate, Date endDate);
}