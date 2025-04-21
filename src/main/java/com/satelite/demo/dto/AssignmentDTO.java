package com.satelite.demo.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class AssignmentDTO {
    @NotNull(message = "Astronaut ID is required")
    private Long astronautId;

    @NotNull(message = "Satellite ID is required")
    private Long satelliteId;
}