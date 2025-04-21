package com.satelite.demo.model;

import java.time.Instant;

public class ErrorResponse {

    private Instant timestamp;
    private int status;
    private String message;
    private String error;
    private String path;

    // Constructor for initializing timestamp, status, and message
    public ErrorResponse(int status, String message, String error, String path) {
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
        this.error = error;
        this.path = path;
    }

    // Getters and Setters
    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
