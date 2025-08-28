package com.mongo.MongoDB.service;

import com.mongo.MongoDB.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    Invoice createInvoice(Invoice invoice);
    Optional<Invoice> getInvoiceById(String id);
    List<Invoice> getAllInvoices();
    Page<Invoice> getInvoices(Pageable pageable);
    Invoice updateInvoice(String id, Invoice invoiceDetails);
    void deleteInvoice(String id);
    List<Invoice> getInvoicesByUserId(String userId);
    List<Invoice> getInvoicesByStatus(String status);
    List<Invoice> getOverdueInvoices();
    List<Invoice> getInvoicesByDueDateRange(Date startDate, Date endDate);
    Invoice updateInvoiceStatus(String id, String status);
    Long countInvoicesByStatus(String status);
}