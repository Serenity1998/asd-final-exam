package com.satelite.demo.model;

import com.satelite.demo.model.Satellite;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Astronaut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 20)
    private String lastName;

    @Min(0)
    @Max(50)
    private int experienceYears;

    @ManyToMany(mappedBy = "astronauts")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Satellite> satellites = new HashSet<>();

    // Helper methods to manage the relationship
    public void assignSatellite(Satellite satellite) {
        satellites.add(satellite);
        satellite.getAstronauts().add(this);
    }

    public void removeSatellite(Satellite satellite) {
        satellites.remove(satellite);
        satellite.getAstronauts().remove(this);
    }
}