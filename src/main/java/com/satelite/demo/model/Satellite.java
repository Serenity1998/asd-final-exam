package com.satelite.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Satellite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @Past
    private LocalDate launchDate;

    @Enumerated(EnumType.STRING)
    private OrbitType orbitType;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "satellite_astronauts", //joining table name
            joinColumns = @JoinColumn(name="satellite_id"),
            inverseJoinColumns = @JoinColumn(name="astronaut_id")
    )
    private Set<Astronaut> astronauts = new HashSet<>();
    public Set<Astronaut> getAstronauts() {
        return astronauts;
    }
    public enum OrbitType {
        LEO,  // Low Earth Orbit (160-2,000 km)
        MEO,  // Medium Earth Orbit (2,000-35,786 km)
        GEO   // Geostationary Earth Orbit
    }
}