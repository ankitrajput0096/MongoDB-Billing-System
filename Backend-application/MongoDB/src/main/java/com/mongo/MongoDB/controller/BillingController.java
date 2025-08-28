package com.mongo.MongoDB.controller;

import com.mongo.MongoDB.dto.response.ApiResponseDTO;
import com.mongo.MongoDB.dto.response.TransactionResponseDTO;
import com.mongo.MongoDB.dto.mapper.InvoiceMapper;
import com.mongo.MongoDB.dto.mapper.TransactionMapper;
import com.mongo.MongoDB.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;
    private final TransactionMapper transactionMapper;
    private final InvoiceMapper invoiceMapper;

    @PostMapping("/process-payment")
    public ResponseEntity<ApiResponseDTO<TransactionResponseDTO>> processPayment(
            @RequestParam String userId,
            @RequestParam Double amount,
            @RequestParam String gateway,
            @RequestParam String transactionId) {

        var transaction = billingService.processPayment(userId, amount, gateway, transactionId);
        var response = transactionMapper.toDTO(transaction);

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/billing/process-payment"));
    }

    @GetMapping("/report/{userId}")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> generateBillingReport(
            @PathVariable String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        var report = billingService.generateBillingReport(userId, startDate, endDate);
        return ResponseEntity.ok(ApiResponseDTO.success(report, "/api/v1/billing/report/" + userId));
    }
    // New endpoint: Get revenue statistics by gateway
    @GetMapping("/revenue-by-gateway")
    public ResponseEntity<ApiResponseDTO<List<Map<String, Object>>>> getRevenueByGateway(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        List<Map<String, Object>> revenueStats = billingService.getRevenueByGateway(startDate, endDate);
        return ResponseEntity.ok(ApiResponseDTO.success(revenueStats, "/api/v1/billing/revenue-by-gateway"));
    }

    // New endpoint: Get top spending users
    @GetMapping("/top-spenders")
    public ResponseEntity<ApiResponseDTO<List<Map<String, Object>>>> getTopSpenders(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(defaultValue = "5") int limit) {

        List<Map<String, Object>> topSpenders = billingService.getTopSpenders(startDate, endDate, limit);
        return ResponseEntity.ok(ApiResponseDTO.success(topSpenders, "/api/v1/billing/top-spenders"));
    }
}