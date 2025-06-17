package com.chopervisual.chopervisual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chopervisual.chopervisual.models.Helicoptero;

@Repository
public interface HelicopteroRepository extends JpaRepository<Helicoptero, Long> {
        List<Helicoptero> findByNombreContainingIgnoreCaseOrModeloContainingIgnoreCase(String nombre, String modelo);
}
