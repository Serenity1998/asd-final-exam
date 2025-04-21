package com.satelite.demo.repository;

import com.satelite.demo.model.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SatelliteRepository extends JpaRepository<Satellite, Long> {
    Optional<Satellite> findByName(String name);
    boolean existsByName(String name);
}