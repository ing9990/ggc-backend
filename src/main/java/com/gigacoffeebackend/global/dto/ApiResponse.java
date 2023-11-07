package com.gigacoffeebackend.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@NoArgsConstructor
@Getter
public class ApiResponse<T> {

    private HttpStatus status;
    private T data;

    public ApiResponse(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus status, T data) {
        return new ApiResponse<>(status, data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(OK, data);
    }
}