package com.satelite.demo.controller;

import com.satelite.demo.dto.AssignmentDTO;
import com.satelite.demo.dto.AstronautDTO;
import com.satelite.demo.model.Astronaut;
import com.satelite.demo.service.AstronautService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/astronauts")
public class AstronautController {

    private final AstronautService astronautService;

    @Autowired
    public AstronautController(AstronautService astronautService) {
        this.astronautService = astronautService;
    }

    @GetMapping
    public ResponseEntity<List<Astronaut>> getAllAstronauts(@RequestParam(required = false) String sort) {
        return ResponseEntity.ok(astronautService.getAllAstronauts(sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Astronaut> getAstronautById(@PathVariable Long id) {
        return ResponseEntity.ok(astronautService.getAstronautById(id));
    }

    @PostMapping
    public ResponseEntity<Astronaut> createAstronaut(@Valid @RequestBody AstronautDTO astronautDTO) {
        return new ResponseEntity<>(astronautService.createAstronaut(astronautDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Astronaut> updateAstronaut(@PathVariable Long id,
                                                     @Valid @RequestBody AstronautDTO astronautDTO) {
        return ResponseEntity.ok(astronautService.updateAstronaut(id, astronautDTO));
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignSatelliteToAstronaut(@Valid @RequestBody AssignmentDTO assignmentDTO) {
        astronautService.assignSatelliteToAstronaut(
                assignmentDTO.getAstronautId(),
                assignmentDTO.getSatelliteId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unassign")
    public ResponseEntity<Void> removeSatelliteFromAstronaut(@Valid @RequestBody AssignmentDTO assignmentDTO) {
        astronautService.removeSatelliteFromAstronaut(
                assignmentDTO.getAstronautId(),
                assignmentDTO.getSatelliteId());
        return ResponseEntity.ok().build();
    }
}