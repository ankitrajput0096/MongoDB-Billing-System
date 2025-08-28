package com.mongo.MongoDB.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
public class ApiResponseDTO<T> {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;
    private String path;

    public ApiResponseDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponseDTO(HttpStatus status, String message, T data, String path) {
        this();
        this.status = status.value();
        this.message = message;
        this.data = data;
        this.path = path;
    }

    public static <T> ApiResponseDTO<T> success(T data, String path) {
        return new ApiResponseDTO<>(HttpStatus.OK, "Success", data, path);
    }

    public static <T> ApiResponseDTO<T> created(T data, String path) {
        return new ApiResponseDTO<>(HttpStatus.CREATED, "Resource created", data, path);
    }

    public static ApiResponseDTO<Void> error(HttpStatus status, String message, String path) {
        return new ApiResponseDTO<>(status, message, null, path);
    }
}