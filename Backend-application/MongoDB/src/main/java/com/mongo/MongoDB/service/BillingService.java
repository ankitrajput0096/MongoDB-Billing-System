package com.mongo.MongoDB.service;


import com.mongo.MongoDB.model.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BillingService {
    @Transactional
    Transaction processPayment(String userId, Double amount, String gateway, String transactionId);

    @Transactional
    Map<String, Object> generateBillingReport(String userId, Date startDate, Date endDate);

    List<Map<String, Object>> getRevenueByGateway(Date startDate, Date endDate);

    List<Map<String, Object>> getTopSpenders(Date startDate, Date endDate, int limit);
}