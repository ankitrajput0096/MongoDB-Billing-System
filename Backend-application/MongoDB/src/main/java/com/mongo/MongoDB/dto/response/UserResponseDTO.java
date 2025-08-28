package com.mongo.MongoDB.dto.response;

import com.mongo.MongoDB.model.User;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class UserResponseDTO {
    private String id;
    private String name;
    private String email;
    private Integer age;
    private Double balance;
    private Boolean active;
    private Date createdAt;
    private Date updatedAt;
    private List<String> hobbies;
    private BillingAddressDTO billingAddress;

    @Data
    public static class BillingAddressDTO {
        private String city;
        private Integer zip;
        private String country;
    }

    public static UserResponseDTO fromEntity(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        dto.setBalance(user.getBalance());
        dto.setActive(user.getActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setHobbies(user.getHobbies());

        if (user.getBillingAddress() != null) {
            BillingAddressDTO addressDTO = new BillingAddressDTO();
            addressDTO.setCity(user.getBillingAddress().getCity());
            addressDTO.setZip(user.getBillingAddress().getZip());
            addressDTO.setCountry(user.getBillingAddress().getCountry());
            dto.setBillingAddress(addressDTO);
        }

        return dto;
    }
}