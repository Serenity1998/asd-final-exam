package com.satelite.demo.dto;

import com.satelite.demo.model.Satellite;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SatelliteResponseDTO {
    private Long id;
    private String name;
    private LocalDate launchDate;
    private Satellite.OrbitType orbitType;
    private List<AstronautSimpleDTO> assignedAstronauts;

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

    public List<AstronautSimpleDTO> getAssignedAstronauts() {
        return assignedAstronauts;
    }

    public void setAssignedAstronauts(List<AstronautSimpleDTO> assignedAstronauts) {
        this.assignedAstronauts = assignedAstronauts;
    }

    // Constructor to convert from entity
    public static SatelliteResponseDTO fromEntity(Satellite satellite) {
        SatelliteResponseDTO dto = new SatelliteResponseDTO();
        dto.setId(satellite.getId());
        dto.setName(satellite.getName());
        dto.setLaunchDate(satellite.getLaunchDate());
        dto.setOrbitType(satellite.getOrbitType());

        if (satellite.getAstronauts() != null) {
            dto.setAssignedAstronauts(satellite.getAstronauts().stream()
                    .map(astronaut -> {
                        AstronautSimpleDTO astronautDTO = new AstronautSimpleDTO();
                        astronautDTO.setId(astronaut.getId());
                        astronautDTO.setFirstName(astronaut.getFirstName());
                        astronautDTO.setLastName(astronaut.getLastName());
                        return astronautDTO;
                    })
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
