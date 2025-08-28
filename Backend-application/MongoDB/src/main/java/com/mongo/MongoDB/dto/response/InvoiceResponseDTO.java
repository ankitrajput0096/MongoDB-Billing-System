package com.mongo.MongoDB.dto.response;

import com.mongo.MongoDB.model.Invoice;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class InvoiceResponseDTO {
    private String id;
    private String userId;
    private List<ItemDTO> items;
    private String status;
    private Date dueDate;
    private Double totalAmount;
    private Date createdAt;
    private Date updatedAt;

    @Data
    public static class ItemDTO {
        private String productName;
        private Integer quantity;
    }

    public static InvoiceResponseDTO fromEntity(Invoice invoice) {
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setId(invoice.getId());
        dto.setUserId(invoice.getUserId().toString());
        dto.setStatus(invoice.getStatus());
        dto.setDueDate(invoice.getDueDate());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setCreatedAt(invoice.getCreatedAt());
        dto.setUpdatedAt(invoice.getUpdatedAt());

        if (invoice.getItems() != null) {
            List<ItemDTO> itemDTOs = invoice.getItems().stream()
                    .map(item -> {
                        ItemDTO itemDTO = new ItemDTO();
                        itemDTO.setProductName(item.getProductName());
                        itemDTO.setQuantity(item.getQuantity());
                        return itemDTO;
                    })
                    .toList();
            dto.setItems(itemDTOs);
        }

        return dto;
    }
}