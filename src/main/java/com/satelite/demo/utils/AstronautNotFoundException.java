package com.satelite.demo.utils;

public class AstronautNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AstronautNotFoundException(String message) {
        super(message);
    }
}
