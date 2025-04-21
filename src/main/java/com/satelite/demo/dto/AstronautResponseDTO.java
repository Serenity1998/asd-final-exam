package com.satelite.demo.dto;

import com.satelite.demo.model.Astronaut;

import java.util.List;
import java.util.stream.Collectors;

public class AstronautResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int experienceYears;
    private List<SatelliteSimpleDTO> assignedSatellites;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public List<SatelliteSimpleDTO> getAssignedSatellites() {
        return assignedSatellites;
    }

    public void setAssignedSatellites(List<SatelliteSimpleDTO> assignedSatellites) {
        this.assignedSatellites = assignedSatellites;
    }

    // Constructor to convert from entity
    public static AstronautResponseDTO fromEntity(Astronaut astronaut) {
        AstronautResponseDTO dto = new AstronautResponseDTO();
        dto.setId(astronaut.getId());
        dto.setFirstName(astronaut.getFirstName());
        dto.setLastName(astronaut.getLastName());
        dto.setExperienceYears(astronaut.getExperienceYears());

        if (astronaut.getSatellites() != null) {
            dto.setAssignedSatellites(astronaut.getSatellites().stream()
                    .map(satellite -> {
                        SatelliteSimpleDTO satelliteDTO = new SatelliteSimpleDTO();
                        satelliteDTO.setId(satellite.getId());
                        satelliteDTO.setName(satellite.getName());
                        satelliteDTO.setOrbitType(satellite.getOrbitType());
                        return satelliteDTO;
                    })
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
