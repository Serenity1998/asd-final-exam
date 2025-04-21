package com.satelite.demo.model;

import java.time.Instant;

public class ErrorResponse {

    private Instant timestamp;
    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        // Instant.now() method gets the current UTC timestamp for the 2 points
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
    }

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
}
