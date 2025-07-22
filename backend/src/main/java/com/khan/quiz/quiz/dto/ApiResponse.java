package com.khan.quiz.quiz.dto;

public class ApiResponse<T> {
    private int status;
    private String message;
    private boolean success;
    private T data;

    public ApiResponse(int status, String message, boolean success, T data) {
        this.status = status;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ApiResponse(int status, String message, boolean success) {
        this(status, message, success, null);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    // Static helper methods
    public static <T> ApiResponse<T> success(int status, String message, T data) {
        return new ApiResponse<>(status, message, true, data);
    }

    public static <T> ApiResponse<T> success(int status, String message) {
        return new ApiResponse<>(status, message, true, null);
    }

    public static <T> ApiResponse<T> failure(int status, String message) {
        return new ApiResponse<>(status, message, false, null);
    }

    public static <T> ApiResponse<T> failure(int status, String message, T data) {
        return new ApiResponse<>(status, message, false, data);
    }

}
