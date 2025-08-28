package com.mongo.MongoDB.repository;

import com.mongo.MongoDB.model.Invoice;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice, String> {

    // Find invoices by user ID
    List<Invoice> findByUserId(ObjectId userId);

    // Find invoices by status
    List<Invoice> findByStatus(String status);

    // Find invoices by due date range
    List<Invoice> findByDueDateBetween(Date startDate, Date endDate);

    // Find invoices by total amount range
    List<Invoice> findByTotalAmountBetween(Double minAmount, Double maxAmount);

    // Find overdue invoices (due date before current date and status not paid)
    @Query("{ 'dueDate': { $lt: ?0 }, 'status': { $ne: 'paid' } }")
    List<Invoice> findOverdueInvoices(Date currentDate);

    // Find invoices by product name in items
    @Query("{ 'items.productName': ?0 }")
    List<Invoice> findByProductName(String productName);

    // Find invoices by user ID and status
    List<Invoice> findByUserIdAndStatus(String userId, String status);

    // Find invoices with total amount greater than specified value
    List<Invoice> findByTotalAmountGreaterThan(Double amount);

    // Find invoices with total amount less than specified value
    List<Invoice> findByTotalAmountLessThan(Double amount);

    // Count invoices by status
    Long countByStatus(String status);

    // Sum of total amounts by user
    @Query(value = "{ 'user_id': ?0 }", fields = "{ 'totalAmount': 1 }")
    List<Invoice> findTotalAmountsByUserId(String userId);

    // Find invoices by creation date range
    List<Invoice> findByCreatedAtBetween(Date startDate, Date endDate);

    // Add this new method
    List<Invoice> findByUserIdAndDueDateBetween(String userId, Date startDate, Date endDate);

}