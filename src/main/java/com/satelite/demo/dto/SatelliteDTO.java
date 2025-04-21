package com.satelite.demo.dto;

import com.satelite.demo.model.Satellite;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SatelliteDTO {
    private Long id;

    @NotBlank(message = "Satellite name is required")
    private String name;

    @Past(message = "Launch date must be in the past")
    private LocalDate launchDate;

    private Satellite.OrbitType orbitType;

    private Set<Long> astronautIds;


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

    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }

    public Satellite.OrbitType getOrbitType() {
        return orbitType;
    }

    public void setOrbitType(Satellite.OrbitType orbitType) {
        this.orbitType = orbitType;
    }

    public Set<Long> getAstronautIds() {
        return astronautIds;
    }

    public void setAstronautIds(Set<Long> astronautIds) {
        this.astronautIds = astronautIds;
    }
}