package com.satelite.demo.controller;
import com.satelite.demo.dto.SatelliteDTO;
import com.satelite.demo.dto.SatelliteResponseDTO;
import com.satelite.demo.service.SatelliteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/satellites")
public class SatelliteController {

    private final SatelliteService satelliteService;

    @Autowired
    public SatelliteController(SatelliteService satelliteService) {
        this.satelliteService = satelliteService;
    }

    @GetMapping
    public ResponseEntity<List<SatelliteResponseDTO>> getAllSatellites() {
        return ResponseEntity.ok(satelliteService.getAllSatellites());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SatelliteResponseDTO> getSatelliteById(@PathVariable Long id) {
        return ResponseEntity.ok(satelliteService.getSatelliteById(id));
    }

    @PostMapping
    public ResponseEntity<SatelliteResponseDTO> createSatellite(@Valid @RequestBody SatelliteDTO satelliteDTO) {
        return new ResponseEntity<>(satelliteService.createSatellite(satelliteDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SatelliteResponseDTO> updateSatellite(@PathVariable Long id,
                                                                @Valid @RequestBody SatelliteDTO satelliteDTO) {
        return ResponseEntity.ok(satelliteService.updateSatellite(id, satelliteDTO));
    }
}