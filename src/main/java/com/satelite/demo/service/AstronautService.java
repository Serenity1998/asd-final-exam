package com.satelite.demo.service;
import com.satelite.demo.dto.AstronautDTO;
import com.satelite.demo.dto.AstronautResponseDTO;
import com.satelite.demo.model.Astronaut;
import com.satelite.demo.model.Satellite;
import com.satelite.demo.repository.AstronautRepository;
import com.satelite.demo.repository.SatelliteRepository;
import com.satelite.demo.utils.AstronautNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AstronautService {

    private final AstronautRepository astronautRepository;
    private final SatelliteRepository satelliteRepository;
    private final SatelliteService satelliteService;

    @Autowired
    public AstronautService(AstronautRepository astronautRepository,
                            SatelliteRepository satelliteRepository,
                            SatelliteService satelliteService) {
        this.astronautRepository = astronautRepository;
        this.satelliteRepository = satelliteRepository;
        this.satelliteService = satelliteService;
    }

    public List<AstronautResponseDTO> getAllAstronauts() {
        return astronautRepository.findAll().stream()
                .map(AstronautResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public AstronautResponseDTO getAstronautById(Long id) {
        Astronaut astronaut = findAstronautEntity(id);
        return AstronautResponseDTO.fromEntity(astronaut);
    }

    @Transactional
    public AstronautResponseDTO createAstronaut(AstronautDTO astronautDTO) {
        Astronaut astronaut = new Astronaut();
        astronaut.setFirstName(astronautDTO.getFirstName());
        astronaut.setLastName(astronautDTO.getLastName());
        astronaut.setExperienceYears(astronautDTO.getExperienceYears());

        // Handle satellite assignments if provided
        if (astronautDTO.getSatelliteIds() != null && !astronautDTO.getSatelliteIds().isEmpty()) {
            Set<Satellite> satellites = astronautDTO.getSatelliteIds().stream()
                    .map(satelliteId -> satelliteService.findSatelliteEntity(satelliteId))
                    .collect(Collectors.toSet());

            satellites.forEach(astronaut::assignSatellite);
        }

        astronaut = astronautRepository.save(astronaut);
        return AstronautResponseDTO.fromEntity(astronaut);
    }

    @Transactional
    public AstronautResponseDTO updateAstronaut(Long id, AstronautDTO astronautDTO) {
        Astronaut astronaut = findAstronautEntity(id);

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
            Astronaut finalAstronaut1 = astronaut;
            satellitesToRemove.forEach(satelliteId -> {
                Satellite satellite = satelliteService.findSatelliteEntity(satelliteId);
                finalAstronaut1.removeSatellite(satellite);
            });

            // Add new satellites
            Astronaut finalAstronaut = astronaut;
            satellitesToAdd.forEach(satelliteId -> {
                Satellite satellite = satelliteService.findSatelliteEntity(satelliteId);
                finalAstronaut.assignSatellite(satellite);
            });
        }

        astronaut = astronautRepository.save(astronaut);
        return AstronautResponseDTO.fromEntity(astronaut);
    }

    @Transactional
    public AstronautResponseDTO assignSatelliteToAstronaut(Long astronautId, Long satelliteId) {
        Astronaut astronaut = findAstronautEntity(astronautId);
        Satellite satellite = satelliteService.findSatelliteEntity(satelliteId);

        astronaut.assignSatellite(satellite);
        astronaut = astronautRepository.save(astronaut);
        return AstronautResponseDTO.fromEntity(astronaut);
    }

    @Transactional
    public AstronautResponseDTO removeSatelliteFromAstronaut(Long astronautId, Long satelliteId) {
        Astronaut astronaut = findAstronautEntity(astronautId);
        Satellite satellite = satelliteService.findSatelliteEntity(satelliteId);

        astronaut.removeSatellite(satellite);
        astronaut = astronautRepository.save(astronaut);
        return AstronautResponseDTO.fromEntity(astronaut);
    }

    // Helper method to find the entity
    public Astronaut findAstronautEntity(Long id) {
        return astronautRepository.findById(id)
                .orElseThrow(() -> new AstronautNotFoundException("Astronaut not found with id: " + id));
    }
}