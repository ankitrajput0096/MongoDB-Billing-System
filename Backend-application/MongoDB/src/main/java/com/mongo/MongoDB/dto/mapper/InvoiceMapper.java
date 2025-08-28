package com.mongo.MongoDB.dto.mapper;

import com.mongo.MongoDB.dto.request.InvoiceRequestDTO;
import com.mongo.MongoDB.dto.response.InvoiceResponseDTO;
import com.mongo.MongoDB.model.Invoice;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    public Invoice toEntity(InvoiceRequestDTO dto) {
        Invoice invoice = new Invoice();
        invoice.setUserId(new ObjectId(dto.getUserId()));
        invoice.setStatus(dto.getStatus());
        invoice.setDueDate(dto.getDueDate());
        invoice.setTotalAmount(dto.getTotalAmount());

        if (dto.getItems() != null) {
            invoice.setItems(dto.getItems().stream()
                    .map(item -> {
                        Invoice.Item invoiceItem = new Invoice.Item();
                        invoiceItem.setProductName(item.getProductName());
                        invoiceItem.setQuantity(item.getQuantity());
                        return invoiceItem;
                    })
                    .toList());
        }

        return invoice;
    }

    public InvoiceResponseDTO toDTO(Invoice invoice) {
        return InvoiceResponseDTO.fromEntity(invoice);
    }
}