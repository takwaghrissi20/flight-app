package com.example.springboot_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springboot_backend.models.AuditReservation;

public interface AuditReservationRepository extends JpaRepository<AuditReservation, Long> {}

