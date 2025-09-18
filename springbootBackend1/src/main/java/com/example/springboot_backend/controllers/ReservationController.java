package com.example.springboot_backend.controllers;


import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot_backend.dto.ReservationRequest;
import com.example.springboot_backend.exceptions.InsufficientSeatsException;
import com.example.springboot_backend.models.Reservation;
import com.example.springboot_backend.services.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationRequest request) {
        try {
            Reservation reservation = reservationService.reserver(
            		 request.getVolId(),
                     request.getPassager().getNom(),
                     request.getPassager().getEmail(),
                     request.getNombrePlaces()
            		);

           
            return ResponseEntity.ok(Map.of(
                    "reservationId", reservation.getId(),
                    "volId", reservation.getVol().getId(),
                    "nomPassager", reservation.getNomPassager(),
                    "emailPassager", reservation.getEmailPassager(),
                    "nombrePlaces", reservation.getNombrePlaces()
            ));

        } catch (InsufficientSeatsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

