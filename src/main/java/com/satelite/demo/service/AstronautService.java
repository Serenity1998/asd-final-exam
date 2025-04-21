package com.satelite.demo.service;

import com.satelite.demo.dto.AstronautDTO;
import com.satelite.demo.model.Astronaut;
import com.satelite.demo.model.Satellite;
import com.satelite.demo.repository.AstronautRepository;
import com.satelite.demo.repository.SatelliteRepository;
import com.satelite.demo.utils.AstronautNotFoundException;
import com.satelite.demo.utils.SatelliteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AstronautService {
    private final AstronautRepository astronautRepository;
    private final SatelliteRepository satelliteRepository;

    @Autowired
    public AstronautService(AstronautRepository astronautRepository, SatelliteRepository satelliteRepository) {
        this.astronautRepository = astronautRepository;
        this.satelliteRepository = satelliteRepository;
    }

    public List<Astronaut> getAllAstronauts(String sort) {
        System.out.println("HERE------------>" + "experienceYears".equalsIgnoreCase(sort));
        if ("experienceYears".equalsIgnoreCase(sort)) {
            return astronautRepository.findAll(Sort.by(Sort.Direction.ASC, "experienceYears"));
        }
        return astronautRepository.findAll();
    }

    public Astronaut getAstronautById(Long id) {
        return astronautRepository.findById(id)
                .orElseThrow(() -> new AstronautNotFoundException("Astronaut not found with id: " + id));
    }

    @Transactional
    public Astronaut createAstronaut(AstronautDTO astronautDTO) {
        Astronaut astronaut = new Astronaut();
        astronaut.setFirstName(astronautDTO.getFirstName());
        astronaut.setLastName(astronautDTO.getLastName());
        astronaut.setExperienceYears(astronautDTO.getExperienceYears());

        // Handle satellite assignments if provided
        if (astronautDTO.getSatelliteIds() != null && !astronautDTO.getSatelliteIds().isEmpty()) {
            Set<Satellite> satellites = astronautDTO.getSatelliteIds().stream()
                    .map(satelliteId -> satelliteRepository.findById(satelliteId)
                            .orElseThrow(() -> new SatelliteNotFoundException("Satellite not found with id: " + satelliteId)))
                    .collect(Collectors.toSet());

            satellites.forEach(astronaut::assignSatellite);
        }

        return astronautRepository.save(astronaut);
    }

    @Transactional
    public Astronaut updateAstronaut(Long id, AstronautDTO astronautDTO) {
        Astronaut astronaut = getAstronautById(id);

        astronaut.setFirstName(astronautDTO.getFirstName());
        astronaut.setLastName(astronautDTO.getLastName());
        astronaut.setExperienceYears(astronautDTO.getExperienceYears());

        // Update satellite assignments if provided
        if (astronautDTO.getSatelliteIds() != null) {
            // Get current satellite IDs
            Set<Long> currentSatelliteIds = astronaut.getSatellites().stream()
                    .map(Satellite::getId)
                    .collect(Collectors.toSet());

            // Find satellites to add
            Set<Long> satellitesToAdd = new HashSet<>(astronautDTO.getSatelliteIds());
            satellitesToAdd.removeAll(currentSatelliteIds);

            // Find satellites to remove
            Set<Long> satellitesToRemove = new HashSet<>(currentSatelliteIds);
            satellitesToRemove.removeAll(astronautDTO.getSatelliteIds());

            // Remove satellites
            satellitesToRemove.forEach(satelliteId -> {
                Satellite satellite = satelliteRepository.findById(satelliteId)
                        .orElseThrow(() -> new SatelliteNotFoundException("Satellite not found with id: " + satelliteId));
                astronaut.removeSatellite(satellite);
            });

            // Add new satellites
            satellitesToAdd.forEach(satelliteId -> {
                Satellite satellite = satelliteRepository.findById(satelliteId)
                        .orElseThrow(() -> new SatelliteNotFoundException("Satellite not found with id: " + satelliteId));
                astronaut.assignSatellite(satellite);
            });
        }

        return astronautRepository.save(astronaut);
    }

    @Transactional
    public void assignSatelliteToAstronaut(Long astronautId, Long satelliteId) {
        Astronaut astronaut = getAstronautById(astronautId);
        Satellite satellite = satelliteRepository.findById(satelliteId)
                .orElseThrow(() -> new SatelliteNotFoundException("Satellite not found with id: " + satelliteId));

        astronaut.assignSatellite(satellite);
        astronautRepository.save(astronaut);
    }

    @Transactional
    public void removeSatelliteFromAstronaut(Long astronautId, Long satelliteId) {
        Astronaut astronaut = getAstronautById(astronautId);
        Satellite satellite = satelliteRepository.findById(satelliteId)
                .orElseThrow(() -> new SatelliteNotFoundException("Satellite not found with id: " + satelliteId));

        astronaut.removeSatellite(satellite);
        astronautRepository.save(astronaut);
    }
}