package com.satelite.demo.service;

import com.satelite.demo.dto.SatelliteDTO;
import com.satelite.demo.model.Satellite;
import com.satelite.demo.repository.SatelliteRepository;
import com.satelite.demo.utils.SatelliteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SatelliteService {

    private final SatelliteRepository satelliteRepository;

    @Autowired
    public SatelliteService(SatelliteRepository satelliteRepository) {
        this.satelliteRepository = satelliteRepository;
    }

    public List<Satellite> getAllSatellites() {
        return satelliteRepository.findAll();
    }

    public Satellite getSatelliteById(Long id) {
        return satelliteRepository.findById(id)
                .orElseThrow(() -> new SatelliteNotFoundException("Satellite not found with id: " + id));
    }

    @Transactional
    public Satellite createSatellite(SatelliteDTO satelliteDTO) {
        // Check if satellite with the same name already exists
        if (satelliteRepository.existsByName(satelliteDTO.getName())) {
            throw new RuntimeException("Satellite with name '" + satelliteDTO.getName() + "' already exists");
        }

        Satellite satellite = new Satellite();
        satellite.setName(satelliteDTO.getName());
        satellite.setLaunchDate(satelliteDTO.getLaunchDate());
        satellite.setOrbitType(satelliteDTO.getOrbitType());

        return satelliteRepository.save(satellite);
    }

    @Transactional
    public Satellite updateSatellite(Long id, SatelliteDTO satelliteDTO) {
        Satellite satellite = getSatelliteById(id);

        // Check if trying to update name to one that already exists
        if (!satellite.getName().equals(satelliteDTO.getName()) &&
                satelliteRepository.existsByName(satelliteDTO.getName())) {
            throw new RuntimeException("Satellite with name '" + satelliteDTO.getName() + "' already exists");
        }

        satellite.setName(satelliteDTO.getName());
        satellite.setLaunchDate(satelliteDTO.getLaunchDate());
        satellite.setOrbitType(satelliteDTO.getOrbitType());

        return satelliteRepository.save(satellite);
    }
}