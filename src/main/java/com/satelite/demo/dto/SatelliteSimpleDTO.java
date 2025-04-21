package com.satelite.demo.dto;

import com.satelite.demo.model.Satellite;

public class SatelliteSimpleDTO {
    private Long id;
    private String name;
    private Satellite.OrbitType orbitType;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Satellite.OrbitType getOrbitType() {
        return orbitType;
    }

    public void setOrbitType(Satellite.OrbitType orbitType) {
        this.orbitType = orbitType;
    }
}
