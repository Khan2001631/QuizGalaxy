package com.khan.quiz.quiz.dto;

import java.time.Instant;

public class ErrorResponse {
    private int status;
    private String message;
    private boolean success;
    private Instant timestamp;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.success = false;
        this.timestamp = Instant.now();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
