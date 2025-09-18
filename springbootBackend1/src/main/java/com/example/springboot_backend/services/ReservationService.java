package com.example.springboot_backend.services;

import com.example.springboot_backend.models.Reservation;

public interface ReservationService {
    Reservation reserver(Long volId, String nom, String email, Integer nombrePlaces);
}
