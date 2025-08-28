package com.mongo.MongoDB.dto.mapper;

import com.mongo.MongoDB.dto.request.UserRequestDTO;
import com.mongo.MongoDB.dto.response.UserResponseDTO;
import com.mongo.MongoDB.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setBalance(dto.getBalance());
        user.setActive(dto.getActive());
        user.setHobbies(dto.getHobbies());

        if (dto.getBillingAddress() != null) {
            User.BillingAddress address = new User.BillingAddress();
            address.setCity(dto.getBillingAddress().getCity());
            address.setZip(dto.getBillingAddress().getZip());
            address.setCountry(dto.getBillingAddress().getCountry());
            user.setBillingAddress(address);
        }

        return user;
    }

    public UserResponseDTO toDTO(User user) {
        return UserResponseDTO.fromEntity(user);
    }

    public void updateEntityFromDTO(UserRequestDTO dto, User user) {
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getAge() != null) user.setAge(dto.getAge());
        if (dto.getBalance() != null) user.setBalance(dto.getBalance());
        if (dto.getActive() != null) user.setActive(dto.getActive());
        if (dto.getHobbies() != null) user.setHobbies(dto.getHobbies());

        if (dto.getBillingAddress() != null) {
            if (user.getBillingAddress() == null) {
                user.setBillingAddress(new User.BillingAddress());
            }
            if (dto.getBillingAddress().getCity() != null) {
                user.getBillingAddress().setCity(dto.getBillingAddress().getCity());
            }
            if (dto.getBillingAddress().getZip() != null) {
                user.getBillingAddress().setZip(dto.getBillingAddress().getZip());
            }
            if (dto.getBillingAddress().getCountry() != null) {
                user.getBillingAddress().setCountry(dto.getBillingAddress().getCountry());
            }
        }
    }
}