package com.example.springboot_backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_backend.models.Reservation;


	public interface ReservationRepository extends JpaRepository<Reservation, Long> {}


