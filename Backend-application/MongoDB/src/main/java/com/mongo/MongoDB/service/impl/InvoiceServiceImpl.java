package com.mongo.MongoDB.service.impl;

import com.mongo.MongoDB.exception.ResourceNotFoundException;
import com.mongo.MongoDB.model.Invoice;
import com.mongo.MongoDB.repository.InvoiceRepository;
import com.mongo.MongoDB.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    @Transactional
    public Invoice createInvoice(Invoice invoice) {
        // Validate required fields
        if (invoice.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required for invoice");
        }

        if (invoice.getItems() == null || invoice.getItems().isEmpty()) {
            throw new IllegalArgumentException("Invoice must have at least one item");
        }

        if (invoice.getStatus() == null) {
            invoice.setStatus("pending");
        }

        if (invoice.getDueDate() == null) {
            // Set due date to 30 days from now by default
            Date dueDate = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000);
            invoice.setDueDate(dueDate);
        }

        // Calculate total amount if not provided
        if (invoice.getTotalAmount() == null) {
            // In a real application, you would calculate this from items
            invoice.setTotalAmount(0.0);
        }

        return invoiceRepository.save(invoice);
    }

    @Override
    public Optional<Invoice> getInvoiceById(String id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Page<Invoice> getInvoices(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Invoice updateInvoice(String id, Invoice invoiceDetails) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));

        // Update fields if provided
        if (invoiceDetails.getItems() != null) {
            invoice.setItems(invoiceDetails.getItems());
        }

        if (invoiceDetails.getStatus() != null) {
            invoice.setStatus(invoiceDetails.getStatus());
        }

        if (invoiceDetails.getDueDate() != null) {
            invoice.setDueDate(invoiceDetails.getDueDate());
        }

        if (invoiceDetails.getTotalAmount() != null) {
            invoice.setTotalAmount(invoiceDetails.getTotalAmount());
        }

        invoice.setUpdatedAt(new Date());

        return invoiceRepository.save(invoice);
    }

    @Override
    @Transactional
    public void deleteInvoice(String id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        invoiceRepository.delete(invoice);
    }

    @Override
    public List<Invoice> getInvoicesByUserId(String userId) {
        return invoiceRepository.findByUserId(new ObjectId(userId));
    }

    @Override
    public List<Invoice> getInvoicesByStatus(String status) {
        return invoiceRepository.findByStatus(status);
    }

    @Override
    public List<Invoice> getOverdueInvoices() {
        return invoiceRepository.findOverdueInvoices(new Date());
    }

    @Override
    public List<Invoice> getInvoicesByDueDateRange(Date startDate, Date endDate) {
        return invoiceRepository.findByDueDateBetween(startDate, endDate);
    }

    @Override
    @Transactional
    public Invoice updateInvoiceStatus(String id, String status) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));

        invoice.setStatus(status);
        invoice.setUpdatedAt(new Date());

        return invoiceRepository.save(invoice);
    }

    @Override
    public Long countInvoicesByStatus(String status) {
        return invoiceRepository.countByStatus(status);
    }
}