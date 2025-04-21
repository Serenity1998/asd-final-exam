package com.satelite.demo.utils;

public class SatelliteNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SatelliteNotFoundException(String message) {
        super(message);
    }
}
