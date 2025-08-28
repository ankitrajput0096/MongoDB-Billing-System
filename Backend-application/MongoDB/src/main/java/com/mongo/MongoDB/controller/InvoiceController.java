package com.mongo.MongoDB.controller;

import com.mongo.MongoDB.dto.request.InvoiceRequestDTO;
import com.mongo.MongoDB.dto.response.ApiResponseDTO;
import com.mongo.MongoDB.dto.response.InvoiceResponseDTO;
import com.mongo.MongoDB.dto.mapper.InvoiceMapper;
import com.mongo.MongoDB.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceMapper invoiceMapper;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<InvoiceResponseDTO>> createInvoice(
            @Valid @RequestBody InvoiceRequestDTO invoiceRequest) {

        var invoice = invoiceMapper.toEntity(invoiceRequest);
        var createdInvoice = invoiceService.createInvoice(invoice);
        var response = invoiceMapper.toDTO(createdInvoice);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response, "/api/v1/invoices"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<InvoiceResponseDTO>> getInvoiceById(@PathVariable String id) {
        var invoice = invoiceService.getInvoiceById(id);

        // Handle the case where user is not found
        if (invoice.isEmpty()) {
            ApiResponseDTO<InvoiceResponseDTO> response = new ApiResponseDTO<>();
            response.setStatus(404);
            response.setMessage("User not found with id: " + id);
            response.setPath("/api/v1/users/" + id);
            return ResponseEntity.status(404).body(response);
        }

        var response = invoiceMapper.toDTO(invoice.get());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/invoices/" + id));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<InvoiceResponseDTO>>> getAllInvoices() {

        // Get all users without pagination
        var invoices = invoiceService.getAllInvoices();
        // Convert to DTO list
        List<InvoiceResponseDTO> invoiceDTOs = invoices.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
        // Create response
        var response = ApiResponseDTO.success(invoiceDTOs, "/api/v1/invoices");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<InvoiceResponseDTO>> updateInvoice(
            @PathVariable String id,
            @Valid @RequestBody InvoiceRequestDTO invoiceRequest) {

        var invoice = invoiceService.getInvoiceById(id);
        var updatedInvoice = invoiceService.updateInvoice(id, invoiceMapper.toEntity(invoiceRequest));
        var response = invoiceMapper.toDTO(updatedInvoice);

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/invoices/" + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteInvoice(@PathVariable String id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok(ApiResponseDTO.success(null, "/api/v1/invoices/" + id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDTO<List<InvoiceResponseDTO>>> getInvoicesByUserId(@PathVariable String userId) {
        var invoices = invoiceService.getInvoicesByUserId(userId);
        var response = invoices.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/invoices/user/" + userId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponseDTO<List<InvoiceResponseDTO>>> getInvoicesByStatus(@PathVariable String status) {
        var invoices = invoiceService.getInvoicesByStatus(status);
        var response = invoices.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/invoices/status/" + status));
    }

    @GetMapping("/overdue")
    public ResponseEntity<ApiResponseDTO<List<InvoiceResponseDTO>>> getOverdueInvoices() {
        var invoices = invoiceService.getOverdueInvoices();
        var response = invoices.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/invoices/overdue"));
    }

    @GetMapping("/due-date-range")
    public ResponseEntity<ApiResponseDTO<List<InvoiceResponseDTO>>> getInvoicesByDueDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        var invoices = invoiceService.getInvoicesByDueDateRange(startDate, endDate);
        var response = invoices.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/invoices/due-date-range"));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponseDTO<InvoiceResponseDTO>> updateInvoiceStatus(
            @PathVariable String id,
            @RequestParam String status) {

        var invoice = invoiceService.updateInvoiceStatus(id, status);
        var response = invoiceMapper.toDTO(invoice);

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/invoices/" + id + "/status"));
    }

    @GetMapping("/status/{status}/count")
    public ResponseEntity<ApiResponseDTO<Long>> countInvoicesByStatus(@PathVariable String status) {
        var count = invoiceService.countInvoicesByStatus(status);
        return ResponseEntity.ok(ApiResponseDTO.success(count, "/api/v1/invoices/status/" + status + "/count"));
    }
}
