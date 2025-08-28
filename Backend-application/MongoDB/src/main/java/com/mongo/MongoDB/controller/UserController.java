package com.mongo.MongoDB.controller;

import com.mongo.MongoDB.dto.request.UserRequestDTO;
import com.mongo.MongoDB.dto.response.ApiResponseDTO;
import com.mongo.MongoDB.dto.response.UserResponseDTO;
import com.mongo.MongoDB.dto.mapper.UserMapper;
import com.mongo.MongoDB.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> createUser(@Valid @RequestBody UserRequestDTO userRequest) {
        var user = userMapper.toEntity(userRequest);
        var createdUser = userService.createUser(user);
        var response = userMapper.toDTO(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response, "/api/v1/users"));
    }

    /*
    V.V.V. NOTE: var keyword in java
    The var keyword was introduced in Java 10 as part of Local Variable Type Inference (LVTI). It's not a data type itself, but rather a way to let the compiler infer the type of a local variable based on the context.
    Key Points About var:
    - Type Inference: The compiler determines the variable's type from the right-hand side of the assignment
    - Local Variables Only: Can only be used for local variables (inside methods, constructors, or initializer blocks)
    - Must Be Initialized: Requires immediate initialization so the compiler can infer the type
    - Not a Keyword: var is a reserved type name, not a keyword, so you can still use it as an identifier
    When to Use var:
    - When the type is obvious from the context
    - To reduce boilerplate code with complex generic types
    - To improve code readability in certain cases
    When NOT to Use var:
    - When the type isn't clear from the initialization
    - For method parameters or return types
    - For field declarations
    - When it reduces code readability
     */

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getUserById(@PathVariable String id) {
        var user = userService.getUserById(id);

        // Handle the case where user is not found
        if (user.isEmpty()) {
            ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>();
            response.setStatus(404);
            response.setMessage("User not found with id: " + id);
            response.setPath("/api/v1/users/" + id);
            return ResponseEntity.status(404).body(response);
        }

        var response = userMapper.toDTO(user.get());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/users/" + id));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>> getAllUsers() {
        // Get all users without pagination
        var users = userService.getAllUsers();
        // Convert to DTO list
        List<UserResponseDTO> userDTOs = users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
        // Create response
        var response = ApiResponseDTO.success(userDTOs, "/api/v1/users");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserRequestDTO userRequest) {

        var user = userService.getUserById(id);
        // Handle the case where user is not found
        if (user.isEmpty()) {
            ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>();
            response.setStatus(404);
            response.setMessage("User not found with id: " + id);
            response.setPath("/api/v1/users/" + id);
            return ResponseEntity.status(404).body(response);
        }
        userMapper.updateEntityFromDTO(userRequest, user.get());
        var updatedUser = userService.updateUser(user.get().getId(), user.get());
        var response = userMapper.toDTO(updatedUser);

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/users/" + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponseDTO.success(null, "/api/v1/users/" + id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getUserByEmail(@PathVariable String email) {
        var user = userService.getUserByEmail(email);
        // Handle the case where user is not found
        if (user.isEmpty()) {
            ApiResponseDTO<UserResponseDTO> response = new ApiResponseDTO<>();
            response.setStatus(404);
            response.setMessage("User not found with email: " + email);
            response.setPath("/api/v1/users/" + email);
            return ResponseEntity.status(404).body(response);
        }
        var response = userMapper.toDTO(user.get());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/users/email/" + email));
    }

    @GetMapping("/status/{active}")
    public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>> getUsersByActiveStatus(@PathVariable Boolean active) {
        var users = userService.getUsersByActiveStatus(active);
        var response = users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/users/status/" + active));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>> getUsersByCity(@PathVariable String city) {
        var users = userService.getUsersByCity(city);
        var response = users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/users/city/" + city));
    }

    @GetMapping("/balance/greater-than/{minBalance}")
    public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>> getUsersWithBalanceGreaterThan(
            @PathVariable Double minBalance) {

        var users = userService.getUsersWithBalanceGreaterThan(minBalance);
        var response = users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponseDTO.success(response,
                "/api/v1/users/balance/greater-than/" + minBalance));
    }

    @PatchMapping("/{id}/balance")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateUserBalance(
            @PathVariable String id,
            @RequestParam Double amount,
            @RequestParam String transactionType) {

        var user = userService.updateUserBalance(id, amount, transactionType);
        var response = userMapper.toDTO(user);

        return ResponseEntity.ok(ApiResponseDTO.success(response, "/api/v1/users/" + id + "/balance"));
    }

    @GetMapping("/count/active")
    public ResponseEntity<ApiResponseDTO<Long>> countActiveUsers() {
        var count = userService.countActiveUsers();
        return ResponseEntity.ok(ApiResponseDTO.success(count, "/api/v1/users/count/active"));
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<ApiResponseDTO<Boolean>> userExists(@PathVariable String email) {
        var exists = userService.userExists(email);
        return ResponseEntity.ok(ApiResponseDTO.success(exists, "/api/v1/users/exists/" + email));
    }
}