package com.mongo.MongoDB.controller;

import com.mongo.MongoDB.dto.request.TransactionRequestDTO;
import com.mongo.MongoDB.dto.response.ApiResponseDTO;
import com.mongo.MongoDB.dto.response.TransactionResponseDTO;
import com.mongo.MongoDB.dto.mapper.TransactionMapper;
import com.mongo.MongoDB.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<TransactionResponseDTO>> createTransaction(
            @Valid @RequestBody TransactionRequestDTO transactionRequest) {

        var transaction = transactionMapper.toEntity(transactionRequest);
        var createdTransaction = transactionService.createTransaction(transaction);
        var response = transactionMapper.toDTO(createdTransaction);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response, "/api/v1/transactions"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TransactionResponseDTO>> getTransactionById(@PathVariable String id) {
        var transaction = transactionService.getTransactionById(id);
        if (transaction.isEmpty()) {
            ApiResponseDTO<TransactionResponseDTO> response = new ApiResponseDTO<>();
            response.setStatus(404);
            response.setMessage("User not found with email: " + id);
            response.setPath("/api/v1/users/" + id);
            return ResponseEntity.status(404).body(response);
        }
        var response = transactionMapper.toDTO(transaction.get());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/transactions/" + id));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<TransactionResponseDTO>>> getAllTransactions() {
        // Get all users without pagination
        var transactions = transactionService.getAllTransactions();
        // Convert to DTO list
        List<TransactionResponseDTO> transactionDTOs = transactions.stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
        // Create response
        var response = ApiResponseDTO.success(transactionDTOs, "/api/v1/transactions");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteTransaction(@PathVariable String id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok(ApiResponseDTO.success(null, "/api/v1/transactions/" + id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDTO<List<TransactionResponseDTO>>> getTransactionsByUserId(
            @PathVariable String userId) {

        var transactions = transactionService.getTransactionsByUserId(userId);
        var response = transactions.stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/transactions/user/" + userId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponseDTO<List<TransactionResponseDTO>>> getTransactionsByStatus(
            @PathVariable String status) {

        var transactions = transactionService.getTransactionsByStatus(status);
        var response = transactions.stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/transactions/status/" + status));
    }

    @GetMapping("/date-range")
    public ResponseEntity<ApiResponseDTO<List<TransactionResponseDTO>>> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        var transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        var response = transactions.stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/transactions/date-range"));
    }

    @GetMapping("/gateway/{gateway}")
    public ResponseEntity<ApiResponseDTO<List<TransactionResponseDTO>>> getTransactionsByGateway(
            @PathVariable String gateway) {

        var transactions = transactionService.getTransactionsByGateway(gateway);
        var response = transactions.stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/transactions/gateway/" + gateway));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> getTransactionStats() {
        var stats = transactionService.getTransactionStats();
        return ResponseEntity.ok(ApiResponseDTO.success(stats, "/api/v1/transactions/stats"));
    }

    @GetMapping("/user/{userId}/total-amount")
    public ResponseEntity<ApiResponseDTO<Double>> getTotalTransactionAmountByUser(@PathVariable String userId) {
        var totalAmount = transactionService.getTotalTransactionAmountByUser(userId);
        return ResponseEntity.ok(ApiResponseDTO.success(totalAmount, "/api/v1/transactions/user/" + userId + "/total-amount"));
    }

    @GetMapping("/status/{status}/count")
    public ResponseEntity<ApiResponseDTO<Long>> countTransactionsByStatus(@PathVariable String status) {
        var count = transactionService.countTransactionsByStatus(status);
        return ResponseEntity.ok(ApiResponseDTO.success(count, "/api/v1/transactions/status/" + status + "/count"));
    }
}